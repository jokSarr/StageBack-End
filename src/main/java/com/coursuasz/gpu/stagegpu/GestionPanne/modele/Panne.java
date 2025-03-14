//package com.coursuasz.gpu.stagegpu.GestionPanne.modele;
//
//import com.coursuasz.gpu.stagegpu.modele.Technicien;
//import com.coursuasz.gpu.stagegpu.modele.Utilisateur;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Panne {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String type;  // Exemple : Électricité, Plomberie, Informatique...
//    private String description;
//    private String localisation;
//
//    @Enumerated(EnumType.STRING)
//    private PrioritePanne priorite;  // Niveau de priorité (HAUTE, MOYENNE, BASSE)
//
//    @Enumerated(EnumType.STRING)
//    private StatutPanne statut = StatutPanne.EN_ATTENTE; // Par défaut, une panne est en attente
//
//    private LocalDateTime dateSignalement = LocalDateTime.now(); // Date de création
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "utilisateur_id", nullable = false)
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "pannesSignalees"})
//    private Utilisateur utilisateur;  // Celui qui signale la panne
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "technicien_id")
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    private Technicien technicien; // Technicien assigné à la panne (peut être null au début)
//}
package com.coursuasz.gpu.stagegpu.GestionPanne.modele;

import com.coursuasz.gpu.stagegpu.modele.Technicien;
import com.coursuasz.gpu.stagegpu.modele.Utilisateur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Panne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String description;
    private String localisation;

    @Enumerated(EnumType.STRING)
    private PrioritePanne priorite;

    @Enumerated(EnumType.STRING)
    private StatutPanne statut = StatutPanne.EN_ATTENTE;

    private LocalDateTime dateSignalement = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    @JsonIgnore
    private Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technicien_id")
    private Technicien technicien;


    public PanneDTO toDTO() {
        return new PanneDTO(id, type, description, localisation, priorite, statut, dateSignalement, utilisateur != null ? utilisateur.getMatricule() : null);
    }
}
