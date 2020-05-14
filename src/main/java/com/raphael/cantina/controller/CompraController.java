package com.raphael.cantina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompraController {
    
    @GetMapping(value = "/compra")
    public String compra(){
        return "/compra/compra";
    }

}