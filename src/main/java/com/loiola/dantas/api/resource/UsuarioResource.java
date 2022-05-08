package com.loiola.dantas.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loiola.dantas.api.dto.TokenDTO;
import com.loiola.dantas.api.model.entity.Usuario;
import com.loiola.dantas.api.service.JwtService;
import com.loiola.dantas.api.service.UsuarioService;
import com.loiola.dantas.api.util.Util;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/autenticar")
	public ResponseEntity<?> autenticar(@RequestBody Usuario usuario) {
		Usuario usuarioAutenticado = usuarioService.autenticar(Util.removerMascara(usuario.getCpf()), usuario.getSenha());
		String token = jwtService.gerarToken(usuarioAutenticado);
		TokenDTO tokenDTO = new TokenDTO(usuarioAutenticado.getCodigo(),usuarioAutenticado.getNome(), token, jwtService.obterDataHoraExpiracao(token));
		return ResponseEntity.ok(tokenDTO);
	}
}
