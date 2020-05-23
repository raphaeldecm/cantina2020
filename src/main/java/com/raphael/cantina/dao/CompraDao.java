package com.raphael.cantina.dao;

import java.util.List;

import com.raphael.cantina.model.Compra;

public interface CompraDao {

	void save(Compra compra);

    void update(Compra compra);

    void delete(Long id);

    Compra findById(Long id);

    List<Compra> findAll();
    
    //List<Compra> findCompras(Aluno aluno);
	
}
