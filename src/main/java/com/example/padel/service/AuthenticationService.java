package com.example.padel.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.padel.auth.AuthenticationRequest;
import com.example.padel.auth.AuthenticationResponse;
import com.example.padel.auth.EmailTemplateName;
import com.example.padel.auth.RegistrationRequest;
import com.example.padel.model.Player;
import com.example.padel.model.Token;
import com.example.padel.repository.PlayerRepository;
import com.example.padel.repository.RoleRepository;
import com.example.padel.repository.TokenRepository;
import com.example.padel.security.JwtService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlayerRepository playerRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl; 

    public void register(RegistrationRequest request) throws MessagingException {
        logger.info("Registering player with email: {}", request.getEmail());
        var playerRole = roleRepository.findByName("PLAYER")
            .orElseThrow(() -> new IllegalStateException("Role not found"));
        var player = Player.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .accountLocked(false)
            .enabled(false)
            .roles(List.of(playerRole))
            .build();

        playerRepository.save(player);
        sendValidationEmail(player);
    }
   

    private void sendValidationEmail(Player player) throws MessagingException {
        var newToken = generateAndSaveActivationToken(player);

        emailService.sendEmail(
            player.getEmail(), 
            player.getFullName(), 
            EmailTemplateName.ACTIVATE_ACCOUNT, 
            activationUrl,
            newToken, 
            "Account activation");
    }

    private String generateAndSaveActivationToken(Player player){
        String generatedToken = generateActivationCode(6);
        var token =Token.builder()
            .token(generatedToken)
            .createdAt(LocalDateTime.now())
            .expiresAt(LocalDateTime.now().plusMinutes(15))
            .player(player)
            .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder activationCode = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            activationCode.append(characters.charAt(randomIndex));
        }
        return activationCode.toString();
    }               
        
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        logger.info("Authenticating player with email: {}", request.getEmail());
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var claims = new HashMap<String, Object>();
        var user = (Player) auth.getPrincipal();

        claims.put("fullname", user.getFirstname() + " " + user.getLastname());
        var jwtToken = jwtService.generateToken(claims, user);

        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();

    }

    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new RuntimeException("Token not found"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getPlayer());
            throw new RuntimeException("Token expired");
        }

        var player = playerRepository.findByEmail(savedToken.getPlayer().getEmail())
            .orElseThrow(() -> new UsernameNotFoundException(String.format("Player with email %s not found", savedToken.getPlayer().getEmail())));

        player.setEnabled(true);
        playerRepository.save(player);    
        
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

}
