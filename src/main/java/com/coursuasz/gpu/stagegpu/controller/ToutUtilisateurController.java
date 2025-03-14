package com.coursuasz.gpu.stagegpu.controller;

import com.coursuasz.gpu.stagegpu.dto.LoginDTO;
import com.coursuasz.gpu.stagegpu.dto.UtilisateurDTO;
import com.coursuasz.gpu.stagegpu.jwt.JwtUtils;
import com.coursuasz.gpu.stagegpu.mapper.UtilisateurMapper;
import com.coursuasz.gpu.stagegpu.modele.ToutUtilisateur;
import com.coursuasz.gpu.stagegpu.modele.Utilisateur;
import com.coursuasz.gpu.stagegpu.service.ToutUtilisateurDetailsService;
import com.coursuasz.gpu.stagegpu.service.ToutUtilisateurService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Data
public class ToutUtilisateurController {
    private final ToutUtilisateurService toutUtilisateurService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private UtilisateurMapper utilisateurMapper = Mappers.getMapper(UtilisateurMapper.class);
    private final ToutUtilisateurDetailsService userDetails;

    @PostMapping(path = "/inscrire")
    public ResponseEntity<?> ajouter (@RequestBody UtilisateurDTO utilisateurDTO) {
        Utilisateur utilisateur = utilisateurMapper.dTOToUtilisateur(utilisateurDTO);
        String password = passwordEncoder.encode("Passer123");
        utilisateur.setPassword(password);
        toutUtilisateurService.ajouter(utilisateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(utilisateurDTO);
    }

    // @PostMapping(path = "/connecter")
    // public ResponseEntity<?> authentifier(@RequestBody LoginDTO loginDTO) {
    //     Utilisateur utilisateur = utilisateurMapper.loginToUtilisateur(loginDTO);
    //     System.out.println("L'utilisateur mappe est: " + utilisateur.getUsername() + " " + utilisateur.getRole());
    //     try {
    //         Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(utilisateur.getUsername(), utilisateur.getPassword()));
    //         if (authentication.isAuthenticated()) {
    //             String username = ((UserDetails) authentication.getPrincipal()).getUsername();
    //             System.out.println("username " + username);
    //             ToutUtilisateur user = userDetails.getUtilisateurByUsername(username);
    //             Map<String, Object> authData = new HashMap<>();
    //             String role = user.getRole();
    //             Long id = user.getId();

    //             System.out.println(role);
    //             authData.put("token",jwtUtils.generateToken(username, role,id));
    //             authData.put("type", "Bearer");
    //             authData.put("role", role);
    //             return ResponseEntity.ok(authData);
    //         }
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("erreur sur username ou password");
    //     }catch (AuthenticationException e) {
    //         log.error(e.getMessage());
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("erreur sur username ou password");
    //     }
    // }

    @PostMapping(path = "/connecter")
    public ResponseEntity<?> authentifier(@RequestBody LoginDTO loginDTO) {
        Utilisateur utilisateur = utilisateurMapper.loginToUtilisateur(loginDTO);
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(utilisateur.getUsername(), utilisateur.getPassword())
            );

            if (authentication.isAuthenticated()) {
                String username = ((UserDetails) authentication.getPrincipal()).getUsername();
                ToutUtilisateur user = userDetails.getUtilisateurByUsername(username);
                String role = user.getRole();
                Long id = user.getId();

                // Génération du token JWT
                String jwtToken = jwtUtils.generateToken(username, role, id);

                // Création des headers
                return ResponseEntity.ok()
                        .header("Authorization", "Bearer " + jwtToken) // Ajout du token dans le header
                        .body(Map.of("role", role, "id", id)); // Renvoyer d'autres infos dans le corps si nécessaire
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erreur sur username ou password");
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erreur sur username ou password");
        }
    }


    @GetMapping("/liste")
    public ResponseEntity<List<ToutUtilisateur>> lister() {
        return ResponseEntity.ok(toutUtilisateurService.lister());
    }
}