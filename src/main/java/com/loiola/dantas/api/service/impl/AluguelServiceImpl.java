package com.loiola.dantas.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loiola.dantas.api.exception.RegraNegocioException;
import com.loiola.dantas.api.model.entity.Aluguel;
import com.loiola.dantas.api.model.entity.Kitnet;
import com.loiola.dantas.api.model.entity.SituacaoEnum;
import com.loiola.dantas.api.model.repository.AluguelRepository;
import com.loiola.dantas.api.model.repository.KitnetRepository;
import com.loiola.dantas.api.service.AluguelService;

@Service
public class AluguelServiceImpl implements AluguelService {

	@Autowired
	private AluguelRepository aluguelRepository;
	
	@Autowired
	KitnetRepository kitnetRepository;
	

	@Override
	@Transactional
	public Aluguel incluir(Aluguel aluguel) {
		this.validarAluguel(aluguel);
		Kitnet kitnet  = kitnetRepository.findById(aluguel.getKitnet().getId()).get();
		if(kitnet == null) {
			throw new RegraNegocioException("Não existe Kitnet para o ID informado.");
		}
		
		kitnet.setSituacao(SituacaoEnum.OCUPADO);
		kitnetRepository.save(kitnet);
		
		return aluguelRepository.save(aluguel);
	}

	@Override
	@Transactional
	public Aluguel alterar(Long id, Aluguel aluguel) {
		Aluguel aluguelConsultado = this.consultar(id);
		if (aluguelConsultado == null) {
			throw new RegraNegocioException("Não existe Aluguel para o ID informado.");
		}
		this.validarAluguel(aluguel);
		
		if(!aluguelConsultado.getKitnet().getId().equals(aluguel.getKitnet().getId())){
			// mudou a kit net
			Kitnet kitnetAnterior  = kitnetRepository.findById(aluguelConsultado.getKitnet().getId()).get();
			kitnetAnterior.setSituacao(SituacaoEnum.DESOCUPADO);
			kitnetRepository.save(kitnetAnterior);
			
			Kitnet kitnetAtual  = kitnetRepository.findById(aluguel.getKitnet().getId()).get();
			kitnetAtual.setSituacao(SituacaoEnum.OCUPADO);
			kitnetRepository.save(kitnetAtual);
			
		}

		aluguelConsultado.setInquilino(aluguel.getInquilino());
		aluguelConsultado.setKitnet(aluguel.getKitnet());
		aluguelConsultado.setDataIni(aluguel.getDataIni());
		aluguelConsultado.setDataFim(aluguel.getDataFim());
		aluguelConsultado.setValor(aluguel.getValor());

		return aluguelRepository.save(aluguelConsultado);
	}

	@Override
	@Transactional
	public void excluir(Long id) {
		Aluguel aluguelConsultado = this.consultar(id);
		if (aluguelConsultado == null) {
			throw new RegraNegocioException("Não existe Aluguel para o ID informado.");
		}
		
		Kitnet kitnet  = kitnetRepository.findById(aluguelConsultado.getKitnet().getId()).get();
		if(kitnet == null) {
			throw new RegraNegocioException("Não existe Kitnet para o ID informado.");
		}
		
		kitnet.setSituacao(SituacaoEnum.DESOCUPADO);
		kitnetRepository.save(kitnet);

		aluguelRepository.delete(aluguelConsultado);
	}

	public Aluguel consultar(Long id) {
		Optional<Aluguel> optional = aluguelRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	@Override
	public List<Aluguel> listar(Aluguel aluguelFiltro) {
		Example<Aluguel> example = Example.of(aluguelFiltro, ExampleMatcher.matching().withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING).withIgnoreNullValues());
		return aluguelRepository.findAll(example);
	}

	private void validarAluguel(Aluguel aluguel) {
		if (Objects.isNull(aluguel.getInquilino()) || Objects.isNull(aluguel.getInquilino().getId())) {
			throw new RegraNegocioException("Informe o Inquilino.");
		}

		if (Objects.isNull(aluguel.getKitnet()) || Objects.isNull(aluguel.getKitnet().getId())) {
			throw new RegraNegocioException("Informe a Kitnet.");
		}

		if (Objects.isNull(aluguel.getDataIni())) {
			throw new RegraNegocioException("Informe a data de inicio.");

		}

		if (Objects.isNull(aluguel.getValor())) {
			throw new RegraNegocioException("Informe o valor.");

		}

	}

}
