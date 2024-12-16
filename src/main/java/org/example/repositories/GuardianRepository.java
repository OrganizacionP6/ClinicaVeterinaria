package org.example.repositories;

import org.example.dtos.GuardianResponse;
import org.example.entities.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GuardianRepository extends JpaRepository<Guardian, Integer> {

    @Query(value = "SELECT g FROM Guardian g WHERE LOWER(g.name) LIKE LOWER(CONCAT ('%',:name, '%'))")
    List<Guardian> findLikeName(String name);
}
