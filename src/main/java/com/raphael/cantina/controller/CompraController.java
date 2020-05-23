package com.raphael.cantina.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import com.raphael.cantina.model.Aluno;
import com.raphael.cantina.model.Compra;
import com.raphael.cantina.service.AlunoService;
import com.raphael.cantina.service.CompraService;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/compras")
public class CompraController {

    @Autowired
    CompraService serviceCompra;

    @Autowired
    AlunoService serviceAluno;

    @GetMapping(value = "/cadastrar")
    public String compra(Compra compra, ModelMap model) {
        model.addAttribute("compras", serviceCompra.buscarTodos());
        return "/compra/compra";
    }

    @ModelAttribute("alunos")
    public List<Aluno> listaDeAlunos() {
        return serviceAluno.buscarTodos();
    }

    @GetMapping("/buscarAlunoPorId/{id}")
    public String buscarAluno(@PathVariable("id") Long id, Compra compra, ModelMap model) {

        Aluno aluno = serviceAluno.buscarPorId(id);

        model.addAttribute("aluno", aluno.getNome());
        model.addAttribute("saldo", aluno.getSaldo().toString());
        model.addAttribute("turma", aluno.getTurma().getNome());
        model.addAttribute("turno", aluno.getTurno().getNome());

        return "/compra/compra";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Compra compra, BindingResult result, RedirectAttributes attr) throws BindException {

        if (compra.getValor() == null || compra.getData() == null || compra.getDescricao().equals("")) {
            attr.addFlashAttribute("fail", "Erro ao cadastrar compra. Campos obrigatórios.");

            return "redirect:/compras/cadastrar";
            // } else if (result.hasErrors()) {
            // attr.addFlashAttribute("fail", "Erro ao cadastrar
            // compra.1"+result.getErrorCount());
            // return "redirect:/compras/cadastrar";
        } else {
            try {

                Aluno aluno = serviceAluno.buscarPorId(compra.getId());
                compra.setAluno(aluno);
                compra.setId(null);

                if (compra.getAluno().getSaldo().doubleValue() >= compra.getValor().doubleValue())
                    compra.setSituacao(true);

                BigDecimal saldoAtual = aluno.getSaldo();
                saldoAtual = saldoAtual.subtract(compra.getValor());
                aluno.setSaldo(saldoAtual);

                serviceCompra.salvar(compra);
                serviceAluno.editar(aluno);

                attr.addFlashAttribute("success", "Compra cadastrada com sucesso");
                return "redirect:/compras/cadastrar";
            } catch (ConstraintViolationException ex) {
                attr.addFlashAttribute("fail", "Erro ao cadastrar compra.2" + ex.getErrorCode());
                return "redirect:/compras/cadastrar";
            } catch (DataIntegrityViolationException e) {
                attr.addFlashAttribute("fail", "Erro ao cadastrar compra.3" + e.getMessage());
                return "redirect:/compras/cadastrar";
            }
        }
    }

    @GetMapping("/editar/{idCompra}/{idAluno}")
    public String preEditar(@PathVariable("idCompra") Long id, @PathVariable("idAluno") Long idAluno, ModelMap model) {
        Compra compra = serviceCompra.buscarPorId(id);

        model.addAttribute("compra", compra);
        model.addAttribute("aluno", compra.getAluno());
        model.addAttribute("saldo", compra.getAluno().getSaldo());
        model.addAttribute("turma", compra.getAluno().getTurma().getNome());
        model.addAttribute("turno", compra.getAluno().getTurno().getNome());

        return "/compra/compra";
    }

    @PostMapping("/editar")
    public String editar(Compra compra, RedirectAttributes attr) {

        Aluno aluno = compra.getAluno();
        Compra compraTemp = serviceCompra.buscarPorId(compra.getId());

        BigDecimal saldoAtual = aluno.getSaldo();
        saldoAtual = saldoAtual.add(compraTemp.getValor()).subtract(compra.getValor());
        aluno.setSaldo(saldoAtual);

        serviceAluno.editar(aluno);
        serviceCompra.editar(compra);

        attr.addFlashAttribute("success", "Compra editada com sucesso.");
        return "redirect:/compras/cadastrar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {

        Compra compra = serviceCompra.buscarPorId(id);

        Aluno aluno = serviceAluno.buscarPorId(compra.getAluno().getId());

        BigDecimal saldoAtual = compra.getAluno().getSaldo();
        saldoAtual = saldoAtual.add(compra.getValor());

        aluno.setSaldo(saldoAtual);
        serviceAluno.editar(aluno);

        serviceCompra.excluir(id);
        attr.addFlashAttribute("success", "Compra excluída com sucesso.");
        return "redirect:/compras/cadastrar";
    }
}