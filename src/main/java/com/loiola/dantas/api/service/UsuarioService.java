package com.loiola.dantas.api.service;

import java.util.List;

import com.loiola.dantas.api.model.entity.Usuario;

public interface UsuarioService {
	
	public Usuario autenticar(String cpf, String senha);
	
	public Usuario salvarUsuario(Usuario usuario);
	
	public Usuario alterarUsuario(String cpf, Usuario usuario);
	
	List<Usuario> listar(Usuario usuarioFiltro);
	
}
