package com.coursuasz.gpu.stagegpu.repository;

import com.coursuasz.gpu.stagegpu.modele.Technicien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TechnicienRepository extends JpaRepository<Technicien, Long> {

    Technicien findByUsername(String username);

    @Query("SELECT t FROM Technicien t WHERE t.matricule = ?1")
    Optional<Technicien> findByMatricule(String matricule);

}
