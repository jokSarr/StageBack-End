package com.coursuasz.gpu.stagegpu.controller;

import com.coursuasz.gpu.stagegpu.exception.ResourceNotFoundException;
import com.coursuasz.gpu.stagegpu.exception.ResourceAlreadyExistException;
import com.coursuasz.gpu.stagegpu.modele.Technicien;
import com.coursuasz.gpu.stagegpu.modele.Utilisateur;
import com.coursuasz.gpu.stagegpu.service.TechnicienService;
import com.coursuasz.gpu.stagegpu.service.UtilisateurService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
@Data
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("gestionnaire")
public class GestionnaireController {

    private final UtilisateurService utilisateurService;
    private final TechnicienService technicienService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/listeUtilisateur")
    public ResponseEntity<List<Utilisateur>> listerLesUtilisateur() {
        return ResponseEntity.ok(utilisateurService.Liste());
    }

    @PostMapping("/ajoutTechnicien")
    public ResponseEntity<?> ajouter(@RequestBody Technicien technicien) {
        try {
            technicien.setPassword(passwordEncoder.encode(technicien.getPassword()));
            technicienService.ajouter(technicien);
            return ResponseEntity.status(HttpStatus.CREATED).body("Technicien ajouté avec succès !");
        } catch (ResourceAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout.");
        }
    }

    @GetMapping("/technicien/{id}")
    public ResponseEntity<?> rechercher(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(technicienService.rechercherTech(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/listeTechnicien")
    public ResponseEntity<List<Technicien>> lister() {
        return ResponseEntity.ok(technicienService.Liste());
    }

    @PutMapping("/modifierTechnicien/{id}")
    public ResponseEntity<?> modifier(@PathVariable Long id, @RequestBody Technicien updatedTechnicien) {
        try {
            if (updatedTechnicien.getPassword() != null && !updatedTechnicien.getPassword().isEmpty()) {
                updatedTechnicien.setPassword(passwordEncoder.encode(updatedTechnicien.getPassword()));
            }

            Technicien technicienModifie = technicienService.modifier(id, updatedTechnicien);
            return ResponseEntity.ok(technicienModifie);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la modification");
        }
    }
    @DeleteMapping("/supprimerTechnicien/{id}")
    public ResponseEntity<?> supprimer(@PathVariable Long id) {
        try {
            technicienService.supprimer(id);
            return ResponseEntity.ok("Technicien supprimé !");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



}
