package com.raphael.cantina.dao;

import com.raphael.cantina.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioDao extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);
}