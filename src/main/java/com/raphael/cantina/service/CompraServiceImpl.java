package com.raphael.cantina.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raphael.cantina.dao.CompraDao;
import com.raphael.cantina.model.Compra;

@Service @Transactional(readOnly = false)
public class CompraServiceImpl implements CompraService{

	@Autowired
	private CompraDao dao;
	
	@Override
	public void salvar(Compra compra) {
		dao.save(compra);
	}

	@Override
	public void editar(Compra compra) {
		dao.update(compra);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override @Transactional(readOnly = true)
	public Compra buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	public List<Compra> buscarTodos() {
		return dao.findAll();
	}

}
