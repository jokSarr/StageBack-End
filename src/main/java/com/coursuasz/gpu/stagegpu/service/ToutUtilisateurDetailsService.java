package com.coursuasz.gpu.stagegpu.service;

import com.coursuasz.gpu.stagegpu.modele.ToutUtilisateur;
import com.coursuasz.gpu.stagegpu.modele.Utilisateur;
import com.coursuasz.gpu.stagegpu.repository.ToutUtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ToutUtilisateurDetailsService implements UserDetailsService {
    private final ToutUtilisateurRepository toutUtilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ToutUtilisateur utilisateur = toutUtilisateurRepository.findByUsername(username);
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

    public ToutUtilisateur getUtilisateurByUsername(String username) {
        return toutUtilisateurRepository.findByUsername(username);
    }
}