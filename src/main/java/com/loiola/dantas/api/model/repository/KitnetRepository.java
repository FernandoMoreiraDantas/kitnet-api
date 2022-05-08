package com.loiola.dantas.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loiola.dantas.api.model.entity.Kitnet;

public interface KitnetRepository extends JpaRepository<Kitnet, Long> {
	Kitnet findBynome(String nome);
}
