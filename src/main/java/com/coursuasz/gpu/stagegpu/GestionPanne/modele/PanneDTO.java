package com.coursuasz.gpu.stagegpu.GestionPanne.modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PanneDTO {
    private Long id;
    private String type;
    private String description;
    private String localisation;
    private PrioritePanne priorite;
    private StatutPanne statut;
    private LocalDateTime dateSignalement;
    private String utilisateurMatricule; // On ne retourne que le matricule de l'utilisateur
}
