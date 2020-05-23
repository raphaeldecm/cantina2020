package com.raphael.cantina.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.raphael.cantina.model.Aluno;
import com.raphael.cantina.service.AlunoService;

@Component
public class StringToAlunoConverter implements Converter<String, Aluno>{

	@Autowired
	private AlunoService serviceAluno;
	
	@Override
	public Aluno convert(String text) {
		// TODO Auto-generated method stub
		if(text.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(text);
		return serviceAluno.buscarPorId(id);
	}

}
