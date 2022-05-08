package com.loiola.dantas.api.service;

import java.util.List;

import com.loiola.dantas.api.model.entity.Kitnet;

public interface KitnetService {
	public Kitnet incluir(Kitnet kitnet);
	public List<Kitnet> listar(Kitnet kitnetFiltro);
	public Kitnet alterar(Long id, Kitnet kitnet);
	public void excluir(Long id);
	public Kitnet consultar(Long id);
}
