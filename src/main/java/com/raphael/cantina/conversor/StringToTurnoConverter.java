package com.raphael.cantina.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.raphael.cantina.model.Turno;
import com.raphael.cantina.service.TurnoService;

@Component
public class StringToTurnoConverter implements Converter<String, Turno>{

	@Autowired
	private TurnoService service;
	
	@Override
	public Turno convert(String text) {
		
		if(text.isEmpty()) {
			return null;
		} 
		Long id = Long.valueOf(text);
		return service.buscarPorId(id);
	}

}
