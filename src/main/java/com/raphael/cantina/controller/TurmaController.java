package com.raphael.cantina.controller;

import java.util.List;

import com.raphael.cantina.model.Turma;
import com.raphael.cantina.model.Turno;
import com.raphael.cantina.service.TurmaService;
import com.raphael.cantina.service.TurnoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/turmas")
public class TurmaController {

    @Autowired
    TurmaService serviceTurma;

    @Autowired
    TurnoService serviceTurno;

    @GetMapping(value = "/cadastrar")
    public String cadastrar(Turma turma, ModelMap model){
        model.addAttribute("turmas", serviceTurma.buscarTodos());
        return "/turma/turma";
    }

    @ModelAttribute("turnos")
	public List<Turno> listaDeTurnos(){
		//return departamentoService.buscarTodos();
		return serviceTurno.buscarTodos();
	}
    
    @PostMapping("/salvar")
	public String salvar(Turma turma, RedirectAttributes attr) {
		serviceTurma.salvar(turma);
		attr.addFlashAttribute("success", "Turma cadastrada com sucesso!");
		return "redirect:/turmas/cadastrar";
    }
    
    @GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("turma", serviceTurma.buscarPorId(id));
		return "/turma/turma";
	}
	
	@PostMapping("/editar")
	public String editar(Turma turma, RedirectAttributes attr) {
		serviceTurma.editar(turma);
		//Enviando alerta para página com attr
		attr.addFlashAttribute("success", "Turma editada com sucesso.");
		return "redirect:/turmas/cadastrar";
    }
    
    @GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		
		if (serviceTurma.turmaTemAluno(id)) {
			model.addAttribute("fail", "Turma não removida. Possui aluno(s) vinculado(s).");
		} else {
			serviceTurma.excluir(id);
			model.addAttribute("success", "Turma excluído com sucesso.");
		}
		
		return "redirect:/turmas/cadastrar";
    }
    
}