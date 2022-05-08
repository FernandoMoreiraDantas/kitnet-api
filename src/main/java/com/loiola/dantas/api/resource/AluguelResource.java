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

import com.loiola.dantas.api.model.entity.Aluguel;
import com.loiola.dantas.api.model.entity.Inquilino;
import com.loiola.dantas.api.model.entity.Kitnet;
import com.loiola.dantas.api.service.AluguelService;

@RestController
@RequestMapping("api/alugueis")
public class AluguelResource {
	
	@Autowired
	private AluguelService aluguelService;
	
	@PostMapping
	public ResponseEntity<?> incluir(@RequestBody Aluguel aluguel){
		return ResponseEntity.status(HttpStatus.CREATED).body(aluguelService.incluir(aluguel));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody Aluguel aluguel){
		return ResponseEntity.status(HttpStatus.OK).body(aluguelService.alterar(id,aluguel));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id){
		aluguelService.excluir(id);
		return  ResponseEntity.noContent().build();
	}
	
	
	@GetMapping
	public ResponseEntity<?> buscar(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "inquilino", required = false) Inquilino inquilino,
			@RequestParam(value = "kitnet", required = false) Kitnet kitnet) {

			Aluguel aluguelFiltro = Aluguel.builder().id(null).inquilino(inquilino).kitnet(kitnet).build();
			List<Aluguel> listaAlugueis = aluguelService.listar(aluguelFiltro);
			
			return ResponseEntity.ok(listaAlugueis);
			
	}
	
	
}
