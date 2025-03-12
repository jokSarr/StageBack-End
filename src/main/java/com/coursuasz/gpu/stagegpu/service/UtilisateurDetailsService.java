package com.coursuasz.gpu.stagegpu.service;

import com.coursuasz.gpu.stagegpu.modele.Utilisateur;
import com.coursuasz.gpu.stagegpu.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UtilisateurDetailsService implements UserDetailsService {
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username);
        if (utilisateur == null) {
            throw new UsernameNotFoundException("Utilisateur inexistant : " + username);
        }

        String role = utilisateur.getRole().startsWith("ROLE_") ? utilisateur.getRole() : "ROLE_" + utilisateur.getRole().toUpperCase();

        return new org.springframework.security.core.userdetails.User(
                utilisateur.getUsername(),
                utilisateur.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(role))
        );
    }

    public Utilisateur getUtilisateurByUsername(String username) {
        return utilisateurRepository.findByUsername(username);
    }
}