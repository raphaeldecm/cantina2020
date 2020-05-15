package com.raphael.cantina.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.raphael.cantina.model.Turma;
import com.raphael.cantina.model.Turno;

@Repository
public class TurmaDaoImpl extends AbstractDao<Turma, Long> implements TurmaDao{
	
	@SuppressWarnings("unchecked")
	protected
	final Class<Turma> entityClass = 
			(Class<Turma>) ( (ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	public List<Turma> findByTurno(Turno turno) {
		
		return entityManager
				.createQuery("from " + entityClass.getSimpleName(), entityClass)
				.getResultList();
	}	

}
