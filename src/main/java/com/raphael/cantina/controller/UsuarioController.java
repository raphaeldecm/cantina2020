package com.raphael.cantina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {
    
    @GetMapping(value = "/usuario")
    public String usuario(){
        return "/usuario/usuario";
    }

}