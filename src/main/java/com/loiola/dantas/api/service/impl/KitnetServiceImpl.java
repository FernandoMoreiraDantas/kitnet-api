package com.loiola.dantas.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loiola.dantas.api.exception.RegraNegocioException;
import com.loiola.dantas.api.model.entity.Kitnet;
import com.loiola.dantas.api.model.repository.KitnetRepository;
import com.loiola.dantas.api.service.KitnetService;
import com.loiola.dantas.api.util.Util;

@Service
public class KitnetServiceImpl implements KitnetService {
	
	@Autowired
	private KitnetRepository kitnetRepository;

	@Override
	@Transactional
	public Kitnet incluir(Kitnet kitnet) {
		this.validarKitnet(kitnet, true);
		return kitnetRepository.save(kitnet);
	}
	
	@Override
	@Transactional
	public Kitnet alterar(Long id, Kitnet kitnet) {
		Kitnet kitnetConsultado = this.consultar(id);
		if(kitnetConsultado == null) {
			throw new RegraNegocioException("Não existe cadastro para o ID informado.");
		}

		this.validarKitnet(kitnetConsultado, false);
		
		kitnetConsultado.setNome(kitnet.getNome());
		kitnetConsultado.setDescricao(kitnet.getDescricao());
		kitnetConsultado.setSituacao(kitnet.getSituacao());
		
		return kitnetRepository.save(kitnetConsultado);
	}
	
	@Override
	@Transactional
	public void excluir(Long id) {
		Kitnet kitnetConsultado = this.consultar(id);
		if(kitnetConsultado == null) {
			throw new RegraNegocioException("Não existe cadastro para o ID informado.");
		}
		
		kitnetRepository.delete(kitnetConsultado);
	}
	
	public Kitnet consultar(Long id) {
		Optional<Kitnet> optional = kitnetRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	@Override
	public List<Kitnet> listar(Kitnet kitnetFiltro){
		Example<Kitnet> example = Example.of(kitnetFiltro, ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING).withIgnoreNullValues());
		return kitnetRepository.findAll(example,Sort.by(Sort.Direction.ASC, "nome"));
	}
	
	private void validarKitnet(Kitnet kitnet, boolean isInclusao) {
		if(Util.isNullOrEmpty(kitnet.getNome())) {
			throw new RegraNegocioException("Informe o Nome.");
		}
		
		if(isInclusao && isNomeKitnetCadastrado(kitnet.getNome())) {
			throw new RegraNegocioException("Já existe Kitnet com esse Nome.");
		}
		
		if(Util.isNullOrEmpty(kitnet.getDescricao())) {
			throw new RegraNegocioException("Informe a Descrição.");
		}
		
		if(kitnet.getSituacao() == null) {
			throw new RegraNegocioException("Informe o Situação.");
		}
		
	}
	
	private boolean isNomeKitnetCadastrado(String nome) {
		Kitnet kitnet = kitnetRepository.findBynome(nome);
		return kitnet != null;
	}

}
