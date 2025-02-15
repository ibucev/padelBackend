package com.example.padel.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "league")
@EntityListeners(AuditingEntityListener.class)
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    
    @ManyToMany
    @JoinTable(name = "league_pairs",
     joinColumns = @JoinColumn(name = "league_id"),
     inverseJoinColumns = @JoinColumn(name = "pair_id"))
    private List<Pair> pairs = new ArrayList<>();
 
    @OneToMany(mappedBy = "league", cascade = CascadeType.PERSIST)
    private List<Match> matches = new ArrayList<Match>();

    @CreatedDate
    private LocalDateTime leagueCreated;
    private Integer leagueDuration;
}
