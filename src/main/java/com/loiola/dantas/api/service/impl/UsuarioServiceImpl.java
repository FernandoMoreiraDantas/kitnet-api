package com.loiola.dantas.api.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.loiola.dantas.api.exception.ErroAutenticacaoException;
import com.loiola.dantas.api.model.entity.Usuario;
import com.loiola.dantas.api.model.repository.UsuarioRepository;
import com.loiola.dantas.api.service.UsuarioService;


@Service
public class UsuarioServiceImpl  implements UsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	
	@Override
	public Usuario autenticar(String cpf, String senha) {
		Usuario usuario = usuarioRepository.findByCpf(cpf);
		if(usuario == null) {
			throw new ErroAutenticacaoException("Usuário não encontrado para o CPF informado.");
		}
		if(!encoder.matches(senha, usuario.getSenha())) {
			throw new ErroAutenticacaoException("Senha Inválida.");
		}
		return usuario;
	}
	
	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		usuario.setSenha(encoder.encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}
	
}
