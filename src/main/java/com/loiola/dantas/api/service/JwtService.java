package com.loiola.dantas.api.service;



import java.util.Date;

import com.loiola.dantas.api.model.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

public interface JwtService {
	
	String gerarToken(Usuario usuario);
	
	Claims obterClains(String token) throws ExpiredJwtException;
	
	boolean isTokenValido(String token);
	
	String obterLoginUsuario(String token);
	
	Date obterDataHoraExpiracao(String token);
	
}
