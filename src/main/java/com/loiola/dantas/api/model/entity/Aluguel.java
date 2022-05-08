package com.loiola.dantas.api.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "aluguel")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Aluguel {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "data_ini")
	@JsonFormat(pattern =  "dd/MM/yyyy")
	private LocalDate dataIni;
	
	@Column(name = "data_fim")
	@JsonFormat(pattern =  "dd/MM/yyyy")
	private LocalDate dataFim;
	
	@Column(name = "valor")
	private BigDecimal valor;
	
	@OneToOne
	@JoinColumn(name = "id_inquilino")
	private Inquilino inquilino;
	
	@OneToOne
	@JoinColumn(name = "id_Kitnet")
	private Kitnet kitnet;
}