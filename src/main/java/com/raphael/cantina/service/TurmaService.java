package com.raphael.cantina.service;

import java.util.List;

import com.raphael.cantina.model.Turma;
import com.raphael.cantina.model.Turno;

public interface TurmaService {

	void salvar(Turma turma);
	
	void editar(Turma turma);
	
	void excluir(Long id);
	
	Turma buscarPorId(Long id);
	
	List<Turma> buscarTodos();

	boolean turmaTemAluno(Long id);

	List<Turma> buscarPorTurno(Turno turno);

	List<Turma> buscarTodosPorTurno(Turno turno);
}
