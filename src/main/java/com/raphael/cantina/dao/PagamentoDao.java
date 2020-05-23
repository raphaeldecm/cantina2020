package com.raphael.cantina.dao;

import java.util.List;

import com.raphael.cantina.model.Pagamento;

public interface PagamentoDao {
	
	void save(Pagamento pagamento);

    void update(Pagamento pagamento);

    void delete(Long id);

    Pagamento findById(Long id);

    List<Pagamento> findAll();

}
