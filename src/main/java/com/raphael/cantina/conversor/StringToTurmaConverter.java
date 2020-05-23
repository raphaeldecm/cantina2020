package com.raphael.cantina.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.raphael.cantina.model.Turma;
import com.raphael.cantina.service.TurmaService;

@Component
public class StringToTurmaConverter implements Converter<String, Turma>{

	@Autowired
	private TurmaService service;

	@Override
	public Turma convert(String text) {
		
		if(text.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(text);
		return service.buscarPorId(id);
	}
}
