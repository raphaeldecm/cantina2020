package com.raphael.cantina.service;

import java.util.List;

import com.raphael.cantina.model.Pagamento;

public interface PagamentoService {

	void salvar(Pagamento pagamento);
	
	void editar(Pagamento pagamento);
	
	void excluir(Long id);
	
	Pagamento buscarPorId(Long id);
	
	List<Pagamento> buscarTodos();
	
}
