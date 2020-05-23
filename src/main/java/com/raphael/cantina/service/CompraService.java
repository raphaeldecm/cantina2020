package com.raphael.cantina.service;

import java.util.List;

import com.raphael.cantina.model.Compra;

public interface CompraService {

	void salvar(Compra compra);
	
	void editar(Compra compra);
	
	void excluir(Long id);
	
	Compra buscarPorId(Long id);
	
	List<Compra> buscarTodos();
	
}
