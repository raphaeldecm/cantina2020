package com.raphael.cantina.dao;

import java.util.Optional;

import com.raphael.cantina.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDao extends JpaRepository<Usuario, Integer>{

    Optional<Usuario> findByLogin(String login);
    
}
