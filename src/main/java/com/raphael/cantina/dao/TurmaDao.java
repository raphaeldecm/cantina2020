package com.raphael.cantina.dao;

import java.util.List;

import com.raphael.cantina.model.Turma;
import com.raphael.cantina.model.Turno;

public interface TurmaDao {

	void save(Turma turma);

    void update(Turma turma);

    void delete(Long id);

    Turma findById(Long id);

    List<Turma> findAll();

	List<Turma> findByTurno(Turno turno);
	
}
