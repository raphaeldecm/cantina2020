package com.raphael.cantina.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    @Value("${application.name}")
    private String applicationName;

    @GetMapping(value = "/usuario")
    public String usuario(ModelMap model){

        model.addAttribute("applicationName", applicationName);
        return "/usuario/usuario";
    }

}