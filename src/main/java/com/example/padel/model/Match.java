package com.example.padel.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "match")
@EntityListeners(AuditingEntityListener.class)
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "firstpair_id", referencedColumnName = "id")
    private Pair firstPair;
    @ManyToOne
    @JoinColumn(name = "secondpair_id", referencedColumnName = "id")
    private Pair secondPair;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<SetResult> setResults = new ArrayList<SetResult>();

    private LocalDateTime matchDate;
}
