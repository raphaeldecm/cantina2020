package com.raphael.cantina.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raphael.cantina.dao.PagamentoDao;
import com.raphael.cantina.model.Pagamento;

@Service @Transactional(readOnly = false)
public class PagamentoServiceImpl implements PagamentoService{

	@Autowired
	PagamentoDao dao;

	@Override
	public void salvar(Pagamento pagamento) {
		dao.save(pagamento);
	}

	@Override
	public void editar(Pagamento pagamento) {
		dao.update(pagamento);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override @Transactional(readOnly = true)
	public Pagamento buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override @Transactional(readOnly = true)
	public List<Pagamento> buscarTodos() {
		return dao.findAll();
	}
	
}
