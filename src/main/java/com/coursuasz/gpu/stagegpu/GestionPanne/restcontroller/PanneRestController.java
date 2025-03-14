package com.coursuasz.gpu.stagegpu.GestionPanne.restcontroller;

import com.coursuasz.gpu.stagegpu.GestionPanne.modele.Panne;
import com.coursuasz.gpu.stagegpu.GestionPanne.modele.PanneDTO;
import com.coursuasz.gpu.stagegpu.GestionPanne.modele.PanneRequest;
import com.coursuasz.gpu.stagegpu.GestionPanne.service.PanneService;
import com.coursuasz.gpu.stagegpu.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("gestionnaire")
public class PanneRestController {

    @Autowired
    private PanneService panneService;


    @GetMapping("/pannes")
    public List<PanneDTO> getAllPannes() {
        return panneService.getAllPannes();
    }


    @PostMapping("/pannes/add")
    public ResponseEntity<?> addPanne(@RequestBody PanneRequest panneRequest) {
        if (panneRequest.getUtilisateurId() == null) {
            return ResponseEntity.badRequest().body("L'ID de l'utilisateur est requis !");
        }

        try {
            Panne panne = new Panne();
            panne.setType(panneRequest.getType());
            panne.setDescription(panneRequest.getDescription());
            panne.setLocalisation(panneRequest.getLocalisation());
            panne.setPriorite(panneRequest.getPriorite());

            Panne nouvellePanne = panneService.addPanne(panne, panneRequest.getUtilisateurId());
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvellePanne);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    @PutMapping("/{idPanne}/assign/{idTech}")
    public ResponseEntity<String> assignPanne(@PathVariable Long idPanne, @PathVariable Long idTech) {
        boolean result = panneService.sendPanne(idPanne, idTech);
        if (result) {
            return ResponseEntity.ok("Panne assignée avec succès !");
        } else {
            return ResponseEntity.badRequest().body("Erreur : Panne ou technicien introuvable.");
        }
    }


}
