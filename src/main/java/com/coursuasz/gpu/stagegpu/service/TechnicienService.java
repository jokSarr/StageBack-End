package com.coursuasz.gpu.stagegpu.service;

import com.coursuasz.gpu.stagegpu.exception.ResourceAlreadyExistException;
import com.coursuasz.gpu.stagegpu.exception.ResourceNotFoundException;
import com.coursuasz.gpu.stagegpu.modele.Technicien;
import com.coursuasz.gpu.stagegpu.modele.Utilisateur;
import com.coursuasz.gpu.stagegpu.repository.TechnicienRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TechnicienService {
    @Autowired
    private TechnicienRepository technicienRepository;

    public void ajouter(Technicien technicien) {
        technicienRepository.findByMatricule(technicien.getMatricule())
                .ifPresent(existing -> {
                    throw new ResourceAlreadyExistException("Le matricule " + technicien.getMatricule() + " existe déjà");
                });

        try {
            technicienRepository.save(technicien);
        } catch (Exception exception) {
            throw new RuntimeException("Erreur lors de l'ajout de l'utilisateur", exception);
        }
    }

    public Technicien rechercher(Long id){
        try {
            return technicienRepository.findById(id).get();
        }catch (Exception e){
            throw new ResourceNotFoundException("L'utilisateur  avec ID "+id+"n'existe pas ");
        }

    }

    public List<Technicien> Liste (){
        return technicienRepository.findAll();
    }


    public Technicien modifier(Long id, Technicien updatedTechnicien) {
        Technicien existingTechnicien = technicienRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permanent avec ID " + id + " non trouvé"));


        existingTechnicien.setUsername(updatedTechnicien.getUsername());
        existingTechnicien.setNom(updatedTechnicien.getNom());
        existingTechnicien.setPrenom(updatedTechnicien.getPrenom());
        existingTechnicien.setPassword(updatedTechnicien.getPassword());
        existingTechnicien.setRole(updatedTechnicien.getRole());

        existingTechnicien.setMatricule(updatedTechnicien.getMatricule());

        return technicienRepository.save(existingTechnicien);
    }

    public void supprimer(Long id) {
        Technicien technicien = rechercher(id);
        technicienRepository.delete(technicien);
    }
}
