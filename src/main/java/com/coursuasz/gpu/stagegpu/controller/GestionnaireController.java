package com.coursuasz.gpu.stagegpu.controller;

import com.coursuasz.gpu.stagegpu.modele.Utilisateur;
import com.coursuasz.gpu.stagegpu.service.TechnicienService;
import com.coursuasz.gpu.stagegpu.service.UtilisateurService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/listeUtilisateur")
    public ResponseEntity<List<Utilisateur>> listerLesUtilisateur() {
        return ResponseEntity.ok(utilisateurService.Liste());
    }




}
