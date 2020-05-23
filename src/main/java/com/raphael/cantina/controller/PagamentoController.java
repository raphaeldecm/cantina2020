package com.raphael.cantina.controller;

import java.math.BigDecimal;
import java.util.List;

import com.raphael.cantina.model.Aluno;
import com.raphael.cantina.model.Compra;
import com.raphael.cantina.model.Pagamento;
import com.raphael.cantina.service.AlunoService;
import com.raphael.cantina.service.CompraService;
import com.raphael.cantina.service.PagamentoService;

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
@RequestMapping(value = "/pagamentos")
public class PagamentoController {

    @Autowired
    PagamentoService servicePagamento;

    @Autowired
    AlunoService serviceAluno;

    @Autowired
    CompraService serviceCompra;

    @GetMapping(value = "/cadastrar")
    public String pagamento(Pagamento pagamento, ModelMap model) {
        model.addAttribute("pagamentos", servicePagamento.buscarTodos());
        return "/pagamento/pagamento";
    }

    @ModelAttribute("alunos")
    public List<Aluno> listarTodos() {
        return serviceAluno.buscarTodos();
    }

    @GetMapping("/buscarAlunoPorId/{id}")
    public String buscarAluno(@PathVariable("id") Long id, Pagamento pagamento, ModelMap model) {

        Aluno aluno = serviceAluno.buscarPorId(id);
        model.addAttribute("aluno", aluno.getNome());
        model.addAttribute("saldo", aluno.getSaldo());
        model.addAttribute("turma", aluno.getTurma().getNome());
        model.addAttribute("turno", aluno.getTurno().getNome());

        return "/pagamento/pagamento";
    }

    @PostMapping(value = "/salvar")
    public String salvar(Pagamento pagamento, RedirectAttributes attr) {

        if (pagamento.getValor() == null || pagamento.getData() == null || pagamento.getDescricao() == null) {
            attr.addFlashAttribute("fail", "Erro ao cadastrar pagamento. Campos obrigatórios.");
            return "redirect:/pagamentos/cadastrar";
        } else {
            Aluno aluno = serviceAluno.buscarPorId(pagamento.getId());
            pagamento.setAluno(aluno);
            pagamento.setId(null);

            BigDecimal saldoAtual = aluno.getSaldo();
            saldoAtual = saldoAtual.add(pagamento.getValor());
            aluno.setSaldo(saldoAtual);

            servicePagamento.salvar(pagamento);
            serviceAluno.editar(aluno);

            atualizarCompras(pagamento);

            attr.addFlashAttribute("success", "Pagamento cadastrado com sucesso");
            return "redirect:/pagamentos/cadastrar";
        }
    }

    public void atualizarCompras(Pagamento pagamento){
        
        List<Compra> compras = serviceCompra.buscarTodos();
        Aluno aluno = pagamento.getAluno();

        for(Compra c : compras){
            if(pagamento.getValor().floatValue() >= c.getValor().floatValue() ||
                aluno.getSaldo().floatValue() >= 0){
                c.setSituacao(true);
                serviceCompra.editar(c);
            }
                //Subtraindo o valor do pagamento do valor da compra.
                //pagamento.setValor(pagamento.getValor().subtract(c.getValor()));
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {

        Pagamento pagamento = servicePagamento.buscarPorId(id);

        Aluno aluno = serviceAluno.buscarPorId(pagamento.getAluno().getId());

        BigDecimal saldoAtual = pagamento.getAluno().getSaldo();
        saldoAtual = saldoAtual.subtract(pagamento.getValor());

        aluno.setSaldo(saldoAtual);
        serviceAluno.editar(aluno);

        servicePagamento.excluir(id);
        attr.addFlashAttribute("success", "Pagamento excluído com sucesso.");
        return "redirect:/pagamentos/cadastrar";
    }

}