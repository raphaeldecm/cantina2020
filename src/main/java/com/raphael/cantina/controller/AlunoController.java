package com.raphael.cantina.controller;

import java.math.BigDecimal;
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
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/alunos")
public class AlunoController {

	@Value("${application.name}")
	private String applicationName;

    @Autowired
    AlunoService serviceAluno;

    @Autowired
    TurnoService serviceTurno;

    @Autowired
    TurmaService serviceTurma;

	private final String sectionName = "Alunos";
    
    @GetMapping(value="/cadastrar")
    public String cadastrar(Aluno aluno, ModelMap model) {
    	model.addAttribute("applicationName", applicationName);
		model.addAttribute("sectionName", sectionName);
        model.addAttribute("alunos", serviceAluno.buscarTodos());
        return "/aluno/aluno";
    }
    
    @ModelAttribute("turnos")
	public List<Turno> listaDeTurnos(){
		//return departamentoService.buscarTodos();
		return serviceTurno.buscarTodos();
	}

    @PostMapping("/salvar")
	public String salvar(Aluno aluno, BindingResult result, RedirectAttributes attr) throws BindException {
		if(aluno.getSaldo() == null) {
			aluno.setSaldo(BigDecimal.ZERO);
		}
		if(result.hasErrors() || aluno.getNome().equals("") || aluno.getTurma() == null || aluno.getTurno() == null) {
			attr.addFlashAttribute("fail", "Aluno não cadastrado. Todos os campos deste formulário são obrigatórios.");
			return "redirect:/alunos/cadastrar";
		}
		
		serviceAluno.salvar(aluno);
		attr.addFlashAttribute("success", "Aluno cadastrado com sucesso");
		return "redirect:/alunos/cadastrar";
    }
 
    @GetMapping(value="/cadastrar/{id}/{nome}")
	public @ResponseBody ModelAndView getTurmasCadastrar(@PathVariable("id") Long id, @PathVariable("nome") String nome) {
		
		Turno turno = serviceTurno.buscarPorId(id);
		List<Turma> turmas = serviceTurma.buscarTodosPorTurno(turno);
		
		ModelAndView mav = new ModelAndView("aluno/aluno");
		
		Aluno aluno = new Aluno();
		aluno.setNome(nome);
		mav.addObject("turmas", turmas);
		mav.addObject("aluno", aluno);
		mav.addObject("saldo", aluno.getSaldo());

		return mav;
    }

	@GetMapping(value="/cadastrar/{id}")
	public @ResponseBody ModelAndView getTurmasCadastrarNoName(@PathVariable("id") Long id) {
		
		Turno turno = serviceTurno.buscarPorId(id);
		List<Turma> turmas = serviceTurma.buscarTodosPorTurno(turno);
		
		ModelAndView mav = new ModelAndView("aluno/aluno");
		
		Aluno aluno = new Aluno();
		mav.addObject("turmas", turmas);
		mav.addObject("aluno", aluno);
		mav.addObject("saldo", aluno.getSaldo());

		return mav;
    }
    
    @GetMapping("/editar/{id}/{turnoId}/{nomeAluno}")
	public String preEditar(@PathVariable("id") Long id, @PathVariable("turnoId") Long turnoId, 
			@PathVariable("nomeAluno") String nomeAluno, ModelMap model) {
        
        Aluno aluno = serviceAluno.buscarPorId(id);
        List<Turno> turnos = serviceTurno.buscarTodos();
        
        Turno turno = serviceTurno.buscarPorId(turnoId);
        List<Turma> turmas = serviceTurma.buscarTodosPorTurno(turno);
        
		model.addAttribute("aluno", aluno);
		model.addAttribute("turnos", turnos);
		model.addAttribute("turno", aluno.getTurno().getId());
        model.addAttribute("turmas", turmas);
        
		return "/aluno/aluno";
    }
    
    //Listar turmas após seleção de turno durante edição
	@GetMapping(value="/editar/{alunoId}/{id}")
	public @ResponseBody ModelAndView getTurmasEditar(@PathVariable("id") Long id, 
			@PathVariable("alunoId") Long alunoId) {
		
		Turno turno = serviceTurno.buscarPorId(id);
		List<Turma> turmas = serviceTurma.buscarTodosPorTurno(turno);
		
		ModelAndView mav = new ModelAndView("aluno/aluno");
		
		Aluno aluno = serviceAluno.buscarPorId(alunoId);
		
		mav.addObject("turmas", turmas);
		mav.addObject("aluno", aluno);

		return mav;
    }

    @PostMapping("/editar")
	public String editar(Aluno aluno, BindingResult result, RedirectAttributes attr) {
		if(aluno.getSaldo() == null) {
			aluno.setSaldo(BigDecimal.ZERO);
		}
		if(result.hasErrors()) {// || aluno.getNome().equals("") || aluno.getTurma() == null || aluno.getTurno() == null) {
			attr.addFlashAttribute("fail", "Aluno não editado. Todos os campos deste formulário são obrigatórios.");
			return "redirect:/alunos/cadastrar";
		} else {
		
			serviceAluno.editar(aluno);
			if(aluno.getSaldo() == null) {
				aluno.setSaldo(BigDecimal.ZERO);
			}
			attr.addFlashAttribute("success", "Aluno editado com sucesso.");
			return "redirect:/alunos/cadastrar";
		}
    }
    
    @GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		
		if (serviceAluno.alunoTemCompra(id)) {
			attr.addFlashAttribute("fail", "Aluno não removido. Possui compra(s) vinculada(s).");
		} else if(serviceAluno.alunoTemPagamento(id)) {
			attr.addFlashAttribute("fail", "Aluno não removido. Possui pagamentos(s) vinculado(s).");
		} else {
			serviceAluno.excluir(id);
			attr.addFlashAttribute("success", "Aluno excluído com sucesso.");
		}
		
		return "redirect:/alunos/cadastrar";
    }
    
}