package com.raphael.cantina.controller;

import javax.validation.Valid;

import com.raphael.cantina.model.Usuario;
import com.raphael.cantina.service.UsuarioServiceImpl;

import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Controller
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Value("${application.name}")
    private String applicationName;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @GetMapping(value = "/cadastrar")
    public String cadastrar(Usuario usuario, ModelMap model){
		// model.addAttribute("usuarios", usuarioService.buscarTodos());
		model.addAttribute("applicationName", applicationName);
		// model.addAttribute("sectionName", sectionName);
        return "/usuario/usuario";
    }

    // @Override
    // public void addViewControllers(ViewControllerRegistry registry) {
    //     registry.addViewController("/login").setViewName("login");
    //     registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    // }

    // @PostMapping
    // @ResponseStatus(HttpStatus.CREATED)
    // public Usuario salvar( @RequestBody @Valid Usuario usuario ){
    //     String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
    //     usuario.setSenha(senhaCriptografada);
    //     return usuarioService.salvar(usuario);
    // }

}