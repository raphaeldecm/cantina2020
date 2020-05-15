package com.raphael.cantina.service;

import java.util.List;

import com.raphael.cantina.model.Turno;

public interface TurnoService {

	void salvar(Turno turno);
	
	void editar(Turno turno);
	
	void excluir(Long id);
	
	Turno buscarPorId(Long id);
	
	List<Turno> buscarTodos();
	
	boolean turnoTemTurma(Long id);
	
}
