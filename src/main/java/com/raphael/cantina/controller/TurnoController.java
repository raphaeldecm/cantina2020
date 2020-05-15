package com.raphael.cantina.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raphael.cantina.model.Turno;
import com.raphael.cantina.service.TurnoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

@Controller
@RequestMapping(value = "/turnos")
public class TurnoController {
    
    @Autowired
    private TurnoService serviceTurno;

    @GetMapping(value = "/cadastrar")
    public String cadastrar(Turno turno, ModelMap model){
		model.addAttribute("turnos", serviceTurno.buscarTodos());
        return "/turno/turno";
    }

    @PostMapping("/salvar")
	public String salvar(Turno turno, RedirectAttributes attr) {
		serviceTurno.salvar(turno);
		attr.addFlashAttribute("success", "Turno cadastrado com sucesso");
		return "redirect:/turnos/cadastrar";
	}

    @GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("turno", serviceTurno.buscarPorId(id));
		return "/turno/turno";
	}

	@PostMapping("/editar")
	public String editar(Turno turno, RedirectAttributes attr) {
		serviceTurno.editar(turno);
		//Enviando alerta para página com attr
		attr.addFlashAttribute("success", "Turno editado com sucesso.");
		return "redirect:/turnos/cadastrar";
	}

    @GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {

		if (serviceTurno.turnoTemTurma(id)) {
			model.addAttribute("fail", "Turno não removido. Possui turma(s) vinculada(s).");
		} else {
			serviceTurno.excluir(id);
			model.addAttribute("success", "Turno excluído com sucesso.");
		}
		
		return "redirect:/turnos/cadastrar";
	}

}