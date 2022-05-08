package com.loiola.dantas.api.resource;

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

import com.loiola.dantas.api.model.entity.Kitnet;
import com.loiola.dantas.api.model.entity.SituacaoEnum;
import com.loiola.dantas.api.service.KitnetService;

@RestController
@RequestMapping("api/kitnets")
public class KitnetResource {
	
	@Autowired
	private KitnetService kitnetService;
	
	@PostMapping
	public ResponseEntity<?> incluir(@RequestBody Kitnet kitnet){
		return ResponseEntity.status(HttpStatus.CREATED).body(kitnetService.incluir(kitnet));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody Kitnet kitnet){
		return ResponseEntity.status(HttpStatus.OK).body(kitnetService.alterar(id,kitnet));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id){
		kitnetService.excluir(id);
		return  ResponseEntity.noContent().build();
	}
	
	
	@GetMapping
	public ResponseEntity<?> buscar(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "situacao", required = false) SituacaoEnum situacao) {

			Kitnet kitnetFiltro = Kitnet.builder().id(null).nome(nome).descricao(descricao).situacao(situacao).build();
			List<Kitnet> listaKitnets = kitnetService.listar(kitnetFiltro);
			
			return ResponseEntity.ok(listaKitnets);
			
	}
	
	
}
