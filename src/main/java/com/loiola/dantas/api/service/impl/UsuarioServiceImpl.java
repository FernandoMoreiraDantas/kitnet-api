package com.loiola.dantas.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loiola.dantas.api.exception.ErroAutenticacaoException;
import com.loiola.dantas.api.model.entity.Usuario;
import com.loiola.dantas.api.model.repository.UsuarioRepository;
import com.loiola.dantas.api.service.UsuarioService;


@Service
public class UsuarioServiceImpl  implements UsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public Usuario autenticar(String cpf, String senha) {
		Usuario usuario = usuarioRepository.findByCpf(cpf);
		if(usuario == null) {
			throw new ErroAutenticacaoException("Usuário não encontrado para o CPF informado.");
		}
		if(!usuario.getSenha().equals(senha)) {
			throw new ErroAutenticacaoException("Senha Inválida.");
		}
		return usuario;
	}	
}
