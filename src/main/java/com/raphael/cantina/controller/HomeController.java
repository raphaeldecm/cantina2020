package com.raphael.cantina.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Value("${application.name}")
    private String applicationName;

    private final String sectionName = "Principal";
    
    @GetMapping(value="/")
    public String home(Model model) {
        model.addAttribute("applicationName", applicationName);
        model.addAttribute("sectionName", sectionName);
        return "/home";
    }
    

}