package com.example.padel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.padel.model.League;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

}
