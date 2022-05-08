package com.loiola.dantas.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loiola.dantas.api.model.entity.Inquilino;

public interface InquilinoRepository extends JpaRepository<Inquilino, Long> {
	
	Inquilino findByCpf(String cpf);
}
