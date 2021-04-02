package com.raphael.cantina.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Value("${application.name}")
    private String applicationName;

    @GetMapping(value="/")
    public String home(Model model) {
        model.addAttribute("applicationName", applicationName);
        return "/home";
    }
    

}