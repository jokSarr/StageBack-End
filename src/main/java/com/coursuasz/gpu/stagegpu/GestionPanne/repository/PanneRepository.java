package com.coursuasz.gpu.stagegpu.GestionPanne.repository;



import com.coursuasz.gpu.stagegpu.GestionPanne.modele.Panne;
import com.coursuasz.gpu.stagegpu.GestionPanne.modele.StatutPanne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PanneRepository extends JpaRepository<Panne, Long> {
    List<Panne> findAll();

}
