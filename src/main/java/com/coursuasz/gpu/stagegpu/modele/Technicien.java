package com.coursuasz.gpu.stagegpu.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance( strategy = InheritanceType.JOINED)
public class Technicien extends ToutUtilisateur {
    private String specialite;
}
