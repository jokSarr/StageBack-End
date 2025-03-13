package com.coursuasz.gpu.stagegpu.repository;

import com.coursuasz.gpu.stagegpu.modele.ToutUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToutUtilisateurRepository extends JpaRepository<ToutUtilisateur, Long> {
    ToutUtilisateur findByUsername(String username);
}
