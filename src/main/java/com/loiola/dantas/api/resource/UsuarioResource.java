package com.loiola.dantas.api.resource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loiola.dantas.api.dto.TokenDTO;
import com.loiola.dantas.api.exception.RegraNegocioException;
import com.loiola.dantas.api.model.entity.Usuario;
import com.loiola.dantas.api.model.repository.UsuarioRepository;
import com.loiola.dantas.api.service.JwtService;
import com.loiola.dantas.api.service.UsuarioService;
import com.loiola.dantas.api.util.TextoUtil;

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
		Usuario usuarioAutenticado = usuarioService.autenticar(TextoUtil.removerMascara(usuario.getCpf()), usuario.getSenha());
		String token = jwtService.gerarToken(usuarioAutenticado);
		TokenDTO tokenDTO = new TokenDTO(usuarioAutenticado.getCodigo(),usuarioAutenticado.getNome(), token);
		return ResponseEntity.ok(tokenDTO);
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Usuario usuario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvarUsuario(usuario));
	}
	
	@PutMapping("/{cpf}")
	public ResponseEntity<Usuario> alterar(@PathVariable String cpf, @RequestBody Usuario usuario){
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.alterarUsuario(cpf, usuario));
	}
	
	@DeleteMapping("/{cpf}")
	public ResponseEntity<Usuario> excluir(@PathVariable String cpf){
		Usuario usuario = usuarioRepository.findByCpf(TextoUtil.removerMascara(cpf));
		if(usuario == null) {
			throw new RegraNegocioException("Não existe cadastro para o CPF informado.");
		}
		
		usuarioRepository.delete(usuario);
		
		return  ResponseEntity.noContent().build();
	}
	
	
	@GetMapping("/{cpf}")
	public ResponseEntity<?> consultar(@PathVariable String cpf ){
		Usuario usuario = usuarioRepository.findByCpf(TextoUtil.removerMascara(cpf));
		return usuario != null ? ResponseEntity.ok(usuario):ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario não encontrado");
	}
	
	
	@GetMapping
	public ResponseEntity<?> buscar(
			@RequestParam(value = "codigo", required = false) Long codigo,
			@RequestParam(value = "cpf", required = false) String cpf,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "fone", required = false) String fone) {

			Usuario usuarioFiltro = Usuario.builder().codigo(null).cpf(TextoUtil.removerMascara(cpf)).nome(nome).fone(TextoUtil.removerMascara(fone)).build();
			List<Usuario> listaUsuarios = usuarioService.listar(usuarioFiltro);
			
			return ResponseEntity.ok(listaUsuarios);
			
	}

}
