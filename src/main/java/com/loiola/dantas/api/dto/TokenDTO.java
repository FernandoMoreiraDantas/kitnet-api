package com.loiola.dantas.api.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenDTO {
	private Long id;
	private String nome;
	private String token;
	private Date dataHoraExpiracaoToken;
}