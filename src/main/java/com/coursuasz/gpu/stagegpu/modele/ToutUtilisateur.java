package com.coursuasz.gpu.stagegpu.modele;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class ToutUtilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String nom;
    private String prenom;
    private String matricule;
    private String password;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation = new Date();
    private boolean active = true;
    private String role;
}
