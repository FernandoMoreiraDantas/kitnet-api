package com.loiola.dantas.api.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inquilino")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inquilino {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "cpf")
	private String cpf; 
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "celular")
	private String celular;
}
