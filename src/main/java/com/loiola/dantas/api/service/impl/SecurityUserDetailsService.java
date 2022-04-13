package com.loiola.dantas.api.service.impl;


import java.util.Objects;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.loiola.dantas.api.model.entity.Usuario;
import com.loiola.dantas.api.model.repository.UsuarioRepository;


@Service
public class SecurityUserDetailsService implements UserDetailsService {
	
	private UsuarioRepository usuarioRepository;
	
	public SecurityUserDetailsService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuarioLogado = usuarioRepository.findByCpf(username);
		if(Objects.isNull(usuarioLogado)) {
			throw new UsernameNotFoundException("CPF não cadastrado.");
		}
		
		
		return User.builder()
						.username(usuarioLogado.getCpf())
						.password(usuarioLogado.getSenha())
						.roles("USER")
						.build();
	}

}

