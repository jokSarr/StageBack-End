package com.coursuasz.gpu.stagegpu.controller;

import com.coursuasz.gpu.stagegpu.dto.LoginDTO;
import com.coursuasz.gpu.stagegpu.dto.UtilisateurDTO;
import com.coursuasz.gpu.stagegpu.jwt.JwtUtils;
import com.coursuasz.gpu.stagegpu.mapper.UtilisateurMapper;
import com.coursuasz.gpu.stagegpu.modele.Utilisateur;
import com.coursuasz.gpu.stagegpu.service.UtilisateurDetailsService;
import com.coursuasz.gpu.stagegpu.service.UtilisateurService;
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
public class UtilisateurController {
    private final UtilisateurService utilisateurService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private UtilisateurMapper utilisateurMapper = Mappers.getMapper(UtilisateurMapper.class);
    private final UtilisateurDetailsService userDetails;

    @PostMapping(path = "/inscrire")
    public ResponseEntity<?> ajouter (@RequestBody UtilisateurDTO utilisateurDTO) {
        Utilisateur utilisateur = utilisateurMapper.dTOToUtilisateur(utilisateurDTO);
        String password = passwordEncoder.encode("Passer123");
        utilisateur.setPassword(password);
        utilisateurService.ajouter(utilisateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(utilisateurDTO);
    }

    @PostMapping(path = "/connecter")
    public ResponseEntity<?> authentifier(@RequestBody LoginDTO loginDTO) {
        Utilisateur utilisateur = utilisateurMapper.loginToUtilisateur(loginDTO);
        System.out.println("L'utilisateur mappe est: " + utilisateur.getUsername() + " " + utilisateur.getRole());
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(utilisateur.getUsername(), utilisateur.getPassword()));
            if (authentication.isAuthenticated()) {
                String username = ((UserDetails) authentication.getPrincipal()).getUsername();
                System.out.println("username " + username);
                Utilisateur user = userDetails.getUtilisateurByUsername(username);
                Map<String, Object> authData = new HashMap<>();
                String role = user.getRole();
                Long id = user.getId();

                System.out.println(role);
                authData.put("token",jwtUtils.generateToken(username, role,id));
                authData.put("type", "Bearer");
                authData.put("role", role);
                return ResponseEntity.ok(authData);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("erreur sur username ou password");
        }catch (AuthenticationException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("erreur sur username ou password");
        }
    }

    //liste
    @GetMapping("/liste")
    public ResponseEntity<List<Utilisateur>> lister() {
        return ResponseEntity.ok(utilisateurService.lister());
    }
}