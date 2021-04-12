package com.raphael.cantina.service;

import java.util.List;

import com.raphael.cantina.dao.UsuarioDao;
import com.raphael.cantina.exceptions.SenhaInvalidaException;
import com.raphael.cantina.model.Usuario;
import com.raphael.cantina.model.Usuario_Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioDao repository;

    // @Override @Transactional(readOnly = true)
	// public List<Usuario> buscarTodos() {
	// 	return repository.findAll();
	// }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = repository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        // String roles = usuario.getRoles();

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .authorities(usuario.getAuthorities())
                .build();
    }
    
}
