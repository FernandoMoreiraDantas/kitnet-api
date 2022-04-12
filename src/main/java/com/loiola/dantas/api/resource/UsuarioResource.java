package com.loiola.dantas.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loiola.dantas.api.dto.TokenDTO;
import com.loiola.dantas.api.model.entity.Usuario;
import com.loiola.dantas.api.model.repository.UsuarioRepository;
import com.loiola.dantas.api.service.JwtService;
import com.loiola.dantas.api.service.UsuarioService;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/autenticar")
	public ResponseEntity<?> autenticar(@RequestBody Usuario usuario) {
		Usuario usuarioAutenticado = usuarioService.autenticar(usuario.getCpf(), usuario.getSenha());
		String token = jwtService.gerarToken(usuarioAutenticado);
		TokenDTO tokenDTO = new TokenDTO(usuarioAutenticado.getNome(), token);
		return ResponseEntity.ok(tokenDTO);
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Usuario usuario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvarUsuario(usuario));
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> consultar(@PathVariable Long codigo ){
		Optional<Usuario> optionalUsuario = usuarioRepository.findById(codigo);
		return optionalUsuario.isPresent() ? ResponseEntity.ok(optionalUsuario.get()):ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario não encontrado");
	}
	
	@GetMapping
	public List<Usuario> listar(){
		return usuarioRepository.findAll();
	} 

}
