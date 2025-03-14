package com.coursuasz.gpu.stagegpu.controller;


import com.coursuasz.gpu.stagegpu.GestionPanne.service.PanneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/techniciens/pannes")
@RequiredArgsConstructor
public class TechnicienController {
    private final PanneService panneService;


    @PutMapping("/{idPanne}/confirmer")
    public ResponseEntity<String> confirmerPanne(@PathVariable Long idPanne, @RequestHeader("technicienId") Long technicienId) {
        boolean result = panneService.confirmerPanne(idPanne, technicienId);
        if (result) {
            return ResponseEntity.ok("Panne confirmée en cours de traitement par le technicien !");
        } else {
            return ResponseEntity.badRequest().body("Erreur : Panne introuvable ou non assignée au technicien.");
        }
    }


    @PutMapping("/{idPanne}/cloturer")
    public ResponseEntity<String> closePanne(@PathVariable Long idPanne, @RequestHeader("technicienId") Long technicienId) {
        boolean result = panneService.closePanne(idPanne, technicienId);
        if (result) {
            return ResponseEntity.ok("Panne clôturée avec succès par le technicien !");
        } else {
            return ResponseEntity.badRequest().body("Erreur : Panne introuvable ou non assignée au technicien.");
        }
    }
}

