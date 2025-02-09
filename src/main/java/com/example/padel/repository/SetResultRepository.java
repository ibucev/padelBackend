package com.example.padel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.padel.model.SetResult;

@Repository
public interface SetResultRepository extends JpaRepository <SetResult, Integer> {

}
