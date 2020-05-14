package com.raphael.cantina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagamentosController {
    
    @GetMapping(value = "/pagamento")
    public String pagamento(){
        return "/pagamento/pagamento";
    }

}