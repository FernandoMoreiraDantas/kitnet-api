package com.loiola.dantas.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loiola.dantas.api.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByCpf(String cpf);
}
