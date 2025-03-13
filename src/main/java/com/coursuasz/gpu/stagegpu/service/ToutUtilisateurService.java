package com.coursuasz.gpu.stagegpu.service;

import com.coursuasz.gpu.stagegpu.exception.ResourceAlreadyExistException;
import com.coursuasz.gpu.stagegpu.exception.ResourceNotFoundException;
import com.coursuasz.gpu.stagegpu.modele.ToutUtilisateur;
import com.coursuasz.gpu.stagegpu.modele.Utilisateur;
import com.coursuasz.gpu.stagegpu.repository.ToutUtilisateurRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ToutUtilisateurService {
    @Autowired
    private ToutUtilisateurRepository toutUtilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public void ajouter(ToutUtilisateur toutUtilisateur) {
        ToutUtilisateur existe = toutUtilisateurRepository.findByUsername(toutUtilisateur.getUsername());
        if(existe != null)
            throw new ResourceAlreadyExistException("Le username "+toutUtilisateur.getUsername()+" existe déja");
        try {
            toutUtilisateurRepository.save(toutUtilisateur);
        } catch(Exception exception){
            throw new ResourceNotFoundException("Erreur lors de l'ajout");
        }
    }

    public ToutUtilisateur rechercher(String username){
        try{
            return toutUtilisateurRepository.findByUsername(username);
        }catch(Exception exception){
            throw new ResourceNotFoundException("Utilisateur "+username+" n'existe pas");
        }
    }

    public List<ToutUtilisateur> lister() {
        return toutUtilisateurRepository.findAll();
    }
}
