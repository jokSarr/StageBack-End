package com.coursuasz.gpu.stagegpu.GestionPanne.service;

import com.coursuasz.gpu.stagegpu.GestionPanne.modele.Panne;
import com.coursuasz.gpu.stagegpu.GestionPanne.modele.PanneDTO;
import com.coursuasz.gpu.stagegpu.GestionPanne.modele.StatutPanne;
import com.coursuasz.gpu.stagegpu.GestionPanne.repository.PanneRepository;
import com.coursuasz.gpu.stagegpu.modele.Technicien;
import com.coursuasz.gpu.stagegpu.modele.ToutUtilisateur;
import com.coursuasz.gpu.stagegpu.modele.Utilisateur;
import com.coursuasz.gpu.stagegpu.repository.TechnicienRepository;
import com.coursuasz.gpu.stagegpu.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class PanneService {

    @Autowired
    private PanneRepository panneRepository;


    @Autowired
    private TechnicienRepository technicienRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;


    public List<PanneDTO> getAllPannes() {
        return panneRepository.findAll()
                .stream()
                .map(Panne::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Panne addPanne(Panne panne, Long utilisateurId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur avec ID " + utilisateurId + " non trouvé"));

        panne.setUtilisateur(utilisateur);
        return panneRepository.save(panne);
    }



    public boolean sendPanne(Long idPanne, Long idTech) {
        Optional<Panne> panneOpt = panneRepository.findById(idPanne);
        Optional<Technicien> techOpt = technicienRepository.findById(idTech);

        if (panneOpt.isPresent() && techOpt.isPresent()) {
            Panne panne = panneOpt.get();
            panne.setStatut(StatutPanne.EN_ATTENTE);
            panne.setTechnicien(techOpt.get());
            panneRepository.save(panne);
            return true;
        }
        return false;
    }


    public boolean confirmerPanne(Long idPanne, Long technicienId) {
        Optional<Panne> panneOpt = panneRepository.findById(idPanne);
        if (panneOpt.isPresent()) {
            Panne panne = panneOpt.get();
            if (panne.getTechnicien() != null && panne.getTechnicien().getId().equals(technicienId)) {
                panne.setStatut(StatutPanne.EN_COURS);
                panneRepository.save(panne);
                return true;
            }
        }
        return false;
    }


    public boolean closePanne(Long idPanne, Long technicienId) {
        Optional<Panne> panneOpt = panneRepository.findById(idPanne);
        if (panneOpt.isPresent()) {
            Panne panne = panneOpt.get();
            if (panne.getTechnicien() != null && panne.getTechnicien().getId().equals(technicienId)) {
                panne.setStatut(StatutPanne.RESOLU);
                panneRepository.save(panne);
                return true;
            }
        }
        return false;
    }
}
