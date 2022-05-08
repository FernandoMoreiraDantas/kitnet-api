package com.loiola.dantas.api.service;

import java.util.List;

import com.loiola.dantas.api.model.entity.Aluguel;

public interface AluguelService {
	public Aluguel incluir(Aluguel aluguel);
	public List<Aluguel> listar(Aluguel aluguelFiltro);
	public Aluguel alterar(Long id, Aluguel aluguel);
	public void excluir(Long id);
	public Aluguel consultar(Long id);
}
