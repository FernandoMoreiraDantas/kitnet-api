package com.loiola.dantas.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loiola.dantas.api.model.entity.Aluguel;

public interface AluguelRepository extends JpaRepository<Aluguel, Long> {

}
