package com.loiola.dantas.api.service;

import java.util.List;

import com.loiola.dantas.api.model.entity.Inquilino;

public interface InquilinoService {
	public Inquilino incluir(Inquilino inquilino);
	public List<Inquilino> listar(Inquilino inquilinoFiltro);
	public Inquilino alterar(Long id, Inquilino inquilino);
	public void excluir(Long id);
	public Inquilino consultar(Long id);
}
