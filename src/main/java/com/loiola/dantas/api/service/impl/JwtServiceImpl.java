package com.loiola.dantas.api.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.loiola.dantas.api.model.entity.Usuario;
import com.loiola.dantas.api.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService {
	
	@Value("${jwt.expiracao}")
	private String expiracao;
	
	@Value("${jwt.chave-assinatura}")
	private String chaveAssinatura;
	
	
	@Override
	public String gerarToken(Usuario usuario) {
		Date dataHoraExpiracao = Date.from( LocalDateTime.now().plusMinutes(Long.valueOf(expiracao)).atZone(ZoneId.systemDefault()).toInstant());
		
		String token = Jwts
						.builder()
						.setExpiration(dataHoraExpiracao)
						.setSubject(usuario.getCpf())
						.claim("nome", usuario.getNome())
						.signWith(SignatureAlgorithm.HS512, chaveAssinatura)
						.compact();
		
		
		return token;
	}

	@Override
	public Claims obterClains(String token) throws ExpiredJwtException {
		return Jwts.parser()
				   .setSigningKey(chaveAssinatura)
				   .parseClaimsJws(token)
				   .getBody();
		
	}

	@Override
	public boolean isTokenValido(String token) {
		try {
			Claims claims = obterClains(token);
			Date dataEx = claims.getExpiration();
			LocalDateTime dataEspiracao = dataEx.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			boolean dataHoraAtualIsAfterDataExpiracao = LocalDateTime.now().isAfter(dataEspiracao);
			return !dataHoraAtualIsAfterDataExpiracao;
		} catch (ExpiredJwtException e) {
			return false;
		}

	}
	
	@Override
	public Date obterDataHoraExpiracao(String token) {
		Claims claims = obterClains(token);
		Date dataEx = claims.getExpiration();
		return dataEx;
	}

	@Override
	public String obterLoginUsuario(String token) {
		return obterClains(token).getSubject();
	}

}
