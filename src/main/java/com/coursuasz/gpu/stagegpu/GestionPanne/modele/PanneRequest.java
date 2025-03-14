package com.coursuasz.gpu.stagegpu.GestionPanne.modele;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PanneRequest {
    private String type;
    private String description;
    private String localisation;
    private PrioritePanne priorite;
    private Long utilisateurId; // ID de l'utilisateur signalant la panne
}
