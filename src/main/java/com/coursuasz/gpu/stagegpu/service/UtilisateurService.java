package com.coursuasz.gpu.stagegpu.service;

import com.coursuasz.gpu.stagegpu.exception.ResourceAlreadyExistException;
import com.coursuasz.gpu.stagegpu.exception.ResourceNotFoundException;
import com.coursuasz.gpu.stagegpu.modele.Utilisateur;
import com.coursuasz.gpu.stagegpu.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public void ajouter(Utilisateur utilisateur) {
        utilisateurRepository.findByMatricule(utilisateur.getMatricule())
                .ifPresent(existing -> {
                    throw new ResourceAlreadyExistException("Le matricule " + utilisateur.getMatricule() + " existe déjà");
                });

        try {
            utilisateurRepository.save(utilisateur);
        } catch (Exception exception) {
            throw new RuntimeException("Erreur lors de l'ajout de l'utilisateur", exception);
        }
    }

    public Utilisateur rechercher(Long id){
        try {
            return utilisateurRepository.findById(id).get();
        }catch (Exception e){
            throw new ResourceNotFoundException("L'utilisateur  avec ID "+id+"n'existe pas ");
        }

    }

    public List<Utilisateur> Liste (){
        return utilisateurRepository.findAll();
    }


    public Utilisateur modifier(Long id, Utilisateur updatedUtilisateur) {
        Utilisateur existingUtilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permanent avec ID " + id + " non trouvé"));


        existingUtilisateur.setUsername(updatedUtilisateur.getUsername());
        existingUtilisateur.setNom(updatedUtilisateur.getNom());
        existingUtilisateur.setPrenom(updatedUtilisateur.getPrenom());
        existingUtilisateur.setPassword(updatedUtilisateur.getPassword());
        existingUtilisateur.setRole(updatedUtilisateur.getRole());

        existingUtilisateur.setMatricule(updatedUtilisateur.getMatricule());

        return utilisateurRepository.save(existingUtilisateur);
    }

    public void supprimer(Long id) {
        Utilisateur utilisateur = rechercher(id);
        utilisateurRepository.delete(utilisateur);
    }

}
