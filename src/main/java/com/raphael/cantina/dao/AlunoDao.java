package com.raphael.cantina.dao;

import java.util.List;

import com.raphael.cantina.model.Aluno;

public interface AlunoDao {

	void save(Aluno aluno);

    void update(Aluno aluno);

    void delete(Long id);

    Aluno findById(Long id);

    List<Aluno> findAll();
	
}
