package com.loiola.dantas.api.service;

import com.loiola.dantas.api.model.entity.Usuario;

public interface UsuarioService {
	
	public Usuario autenticar(String cpf, String senha);
	
	public Usuario salvarUsuario(Usuario usuario);
	
}
