package com.raphael.cantina.controller;

import com.raphael.cantina.model.Turno;
import com.raphael.cantina.service.TurnoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/turnos")
public class TurnoController {

	@Value("${application.name}")
	private String applicationName;

	private final String sectionName = "Turnos";

    @Autowired
    private TurnoService serviceTurno;

    @GetMapping(value = "/cadastrar")
    public String cadastrar(Turno turno, ModelMap model){
		model.addAttribute("turnos", serviceTurno.buscarTodos());
		model.addAttribute("applicationName", applicationName);
		model.addAttribute("sectionName", sectionName);
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
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {

		if (serviceTurno.turnoTemTurma(id)) {
			attr.addFlashAttribute("fail", "Turno não removido. Possui turma(s) vinculada(s).");
		} else {
			serviceTurno.excluir(id);
			attr.addFlashAttribute("success", "Turno excluído com sucesso");
		}
		
		return "redirect:/turnos/cadastrar";
	}

}