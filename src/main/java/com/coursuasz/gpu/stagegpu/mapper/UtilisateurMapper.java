package com.coursuasz.gpu.stagegpu.mapper;

import com.coursuasz.gpu.stagegpu.dto.LoginDTO;
import com.coursuasz.gpu.stagegpu.dto.UtilisateurDTO;
import com.coursuasz.gpu.stagegpu.modele.Utilisateur;
import org.mapstruct.Mapper;

@Mapper
public interface UtilisateurMapper {
    LoginDTO UtilisateurToDTO(Utilisateur utilisateur);

    Utilisateur dTOToUtilisateur(UtilisateurDTO utilisateurDTO);

    LoginDTO UtilisateurToLogin(Utilisateur utilisateur);

    Utilisateur loginToUtilisateur(LoginDTO loginDTO);
}
