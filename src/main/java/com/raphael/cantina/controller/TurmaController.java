package com.raphael.cantina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TurmaController {

    @GetMapping(value = "/turma")
    public String turma(){
        return "/turma/turma";
    }
    
}