package com.coursuasz.gpu.stagegpu.repository;

import com.coursuasz.gpu.stagegpu.modele.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByUsername(String username);

    @Query("SELECT u FROM Utilisateur u WHERE u.matricule = ?1")
    Optional<Utilisateur> findByMatricule(String matricule);

}
