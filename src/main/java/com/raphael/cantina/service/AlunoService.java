package com.raphael.cantina.service;

import java.util.List;

import com.raphael.cantina.model.Aluno;
import com.raphael.cantina.model.Turma;

public interface AlunoService {

	void salvar(Aluno aluno);
	
	void editar(Aluno aluno);
	
	void excluir(Long id);
	
	Aluno buscarPorId(Long id);
	
	List<Aluno> buscarTodos();

	boolean alunoTemCompra(Long id);
	
	boolean alunoTemPagamento(Long id);
	
	public List<Aluno> buscarTodosPorTurma(Turma turma);
	
}
