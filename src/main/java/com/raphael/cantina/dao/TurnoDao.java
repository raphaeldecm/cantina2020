package com.raphael.cantina.dao;

import java.util.List;

import com.raphael.cantina.model.Turno;

public interface TurnoDao {

	void save(Turno turno);

    void update(Turno turno);

    void delete(Long id);

    Turno findById(Long id);

    List<Turno> findAll();
	
}
