package com.raphael.cantina.controller;

import java.util.List;

import com.raphael.cantina.model.Aluno;
import com.raphael.cantina.model.Turma;
import com.raphael.cantina.model.Turno;
import com.raphael.cantina.service.AlunoService;
import com.raphael.cantina.service.TurmaService;
import com.raphael.cantina.service.TurnoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${application.name}")
	private String applicationName;

    @Autowired
    TurmaService serviceTurma;

    @Autowired
	TurnoService serviceTurno;
	
	@Autowired
	AlunoService serviceAluno;

    @GetMapping(value = "/cadastrar")
    public String cadastrar(Turma turma, ModelMap model){
        model.addAttribute("turmas", serviceTurma.buscarTodos());
        model.addAttribute("applicationName", applicationName);
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
    
    @GetMapping("/editar/{id}/{idTurno}")
	public String preEditar(@PathVariable("id") Long id, @PathVariable("idTurno") Long idTurno, ModelMap model) {

		List<Turno> turnos = serviceTurno.buscarTodos();

		model.addAttribute("turma", serviceTurma.buscarPorId(id));
		model.addAttribute("turnos", turnos);
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
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		
		if (serviceTurma.turmaTemAluno(id)) {
			attr.addFlashAttribute("fail", "Turma não removida. Possui aluno(s) vinculado(s).");
		} else {
			serviceTurma.excluir(id);
			attr.addFlashAttribute("success", "Turma excluído com sucesso.");
		}
		
		return "redirect:/turmas/cadastrar";
	}
	
	@GetMapping("/listarAlunos/{id}")
	public String alunosPorTurma(@PathVariable("id") Long id, Turma turma, RedirectAttributes attr){

		Turma turmaSelecionada = serviceTurma.buscarPorId(id);
		List<Aluno> alunos = serviceAluno.buscarTodosPorTurma(turmaSelecionada);

		attr.addFlashAttribute("turmaSelecionada", turmaSelecionada);
		attr.addFlashAttribute("alunos", alunos);
		attr.addFlashAttribute("tabActived", "active");

		return "redirect:/turmas/cadastrar";
	}
}