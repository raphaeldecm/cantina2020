package com.raphael.cantina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TurnoController {
    
    @GetMapping(value = "/turno")
    public String turno(){
        return "/turno/turno";
    }

}