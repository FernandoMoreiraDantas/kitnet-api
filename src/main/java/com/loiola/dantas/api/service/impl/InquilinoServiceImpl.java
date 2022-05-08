package com.loiola.dantas.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loiola.dantas.api.exception.RegraNegocioException;
import com.loiola.dantas.api.model.entity.Inquilino;
import com.loiola.dantas.api.model.repository.InquilinoRepository;
import com.loiola.dantas.api.service.InquilinoService;
import com.loiola.dantas.api.util.Util;

@Service
public class InquilinoServiceImpl implements InquilinoService {
	
	@Autowired
	private InquilinoRepository inquilinoRepository;

	@Override
	@Transactional
	public Inquilino incluir(Inquilino inquilino) {
		this.validarUsuario(inquilino, true);
		return inquilinoRepository.save(inquilino);
	}
	
	@Override
	@Transactional
	public Inquilino alterar(Long id, Inquilino inquilino) {
		Inquilino inquilinoConsultado = this.consultar(id);
		if(inquilinoConsultado == null) {
			throw new RegraNegocioException("Não existe cadastro para o ID informado.");
		}

		this.validarUsuario(inquilino, false);
		
		inquilinoConsultado.setCpf(inquilino.getCpf());
		inquilinoConsultado.setNome(inquilino.getNome());
		inquilinoConsultado.setCelular(inquilino.getCelular()); 
		
		return inquilinoRepository.save(inquilinoConsultado);
	}
	
	@Override
	@Transactional
	public void excluir(Long id) {
		Inquilino inquilinoConsultado = this.consultar(id);
		if(inquilinoConsultado == null) {
			throw new RegraNegocioException("Não existe cadastro para o ID informado.");
		}
		
		inquilinoRepository.delete(inquilinoConsultado);
	}
	
	public Inquilino consultar(Long id) {
		Optional<Inquilino> optional = inquilinoRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	@Override
	public List<Inquilino> listar(Inquilino inquilinoFiltro){
		Example<Inquilino> example = Example.of(inquilinoFiltro, ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING).withIgnoreNullValues());
		return inquilinoRepository.findAll(example,Sort.by(Sort.Direction.ASC, "nome"));
	}
	
	private void validarUsuario(Inquilino inquilino, boolean isInclusao) {
		String cpf = Util.removerMascara(inquilino.getCpf());
		inquilino.setCpf(cpf);
		if(Util.isNullOrEmpty(cpf)) {
			throw new RegraNegocioException("Informe o CPF.");
		}
		
		if(!Util.isValidCPF(cpf)) {
			throw new RegraNegocioException("o CPF informado é inválido.");
		}
		
		if(isInclusao && isCpfcadastrado(cpf)) {
			throw new RegraNegocioException("Já existe cadastro para o CPF informado.");
		}
		
		if(Util.isNullOrEmpty(inquilino.getNome())) {
			throw new RegraNegocioException("Informe o Nome.");
		}
		
		// Retirando a mascara do celular
		inquilino.setCelular(Util.removerMascara(inquilino.getCelular()));
		
		if(Util.isNullOrEmpty(inquilino.getCelular())) {
			throw new RegraNegocioException("Informe o Celular.");
		}
		
		if(!Util.isCelularValido(inquilino.getCelular())) {
			throw new RegraNegocioException("Celular inválido.");
		}
		
	}
	
	private boolean isCpfcadastrado(String cpf) {
		Inquilino inquilino = inquilinoRepository.findByCpf(cpf);
		return inquilino != null;
	}

}
