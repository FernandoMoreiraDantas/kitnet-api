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

import com.loiola.dantas.api.model.entity.Inquilino;
import com.loiola.dantas.api.service.InquilinoService;
import com.loiola.dantas.api.util.Util;

@RestController
@RequestMapping("api/inquilinos")
public class InquilinoResource {
	
	@Autowired
	private InquilinoService inquilinoService;
	
	@PostMapping
	public ResponseEntity<?> incluir(@RequestBody Inquilino inquilino){
		return ResponseEntity.status(HttpStatus.CREATED).body(inquilinoService.incluir(inquilino)); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Inquilino> alterar(@PathVariable Long id, @RequestBody Inquilino inquilino){
		return ResponseEntity.status(HttpStatus.OK).body(inquilinoService.alterar(id,inquilino));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Inquilino> excluir(@PathVariable Long id){
		inquilinoService.excluir(id);
		return  ResponseEntity.noContent().build();
	}
	
	
	@GetMapping
	public ResponseEntity<?> buscar(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "cpf", required = false) String cpf,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "celular", required = false) String celular) {

			Inquilino inquilinoFiltro = Inquilino.builder().id(null).cpf(Util.removerMascara(cpf)).nome(nome).celular(Util.removerMascara(celular)).build();
			List<Inquilino> listaInquilinos = inquilinoService.listar(inquilinoFiltro);
			
			return ResponseEntity.ok(listaInquilinos);
			
	}
	
	
}
