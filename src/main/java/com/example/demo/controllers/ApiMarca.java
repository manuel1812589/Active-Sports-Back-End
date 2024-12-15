package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.models.Estado;
import com.example.demo.models.Marca;
import com.example.demo.models.MarcasDto;
import com.example.demo.services.IEstadoService;
import com.example.demo.services.IMarcaService;
import com.example.demo.utilidades.PaginationMod;
import com.example.demo.utilidades.Utilidades;

@RestController
@RequestMapping("/api/marca")
public class ApiMarca {
	
	@Autowired
	private IMarcaService marcaService;
	
	@Autowired
	private IEstadoService estadoService;
	
	@GetMapping("/listar")
	public Object listar(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "") String nombre) {
				
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		PaginationMod<MarcasDto> enviar = marcaService.listarMarcaDtoPaginado(nombre,PageRequest.of(page, 5, Sort.by("id").ascending()));
		
		List<Estado> estado = estadoService.listar();
		
		map.put("lista", enviar);
		map.put("estado", estado);

	    return map;
	}
	
	@PostMapping("/agregar")
	public ResponseEntity<Object> guardarMarca(@RequestBody Marca marca) {

	    Marca marcaExistente = marcaService.nombreExiste(marca.getNombre());
	    if (marcaExistente != null) {
	        if (marcaExistente.getEstado().getId().equals(3)) {
	            Estado estadoActivo = estadoService.buscarPorIdEstado(1);
	            if (estadoActivo == null) {
	                return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ESTADO ACTIVO");
	            }
	            marcaExistente.setEstado(estadoActivo);
	            marcaService.guardar(marcaExistente);
	            return Utilidades.generateResponse(HttpStatus.ACCEPTED, "SE ENCONTRÓ UNA MARCA ARCHIVADA CON ESTE NOMBRE, SE RESTAURARÁ A ACTIVO");
	        } else {
	            return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "EL NOMBRE DE LA MARCA QUE SE INTENTA REGISTRAR YA EXISTE");
	        }
	    }

	    Estado estado = estadoService.buscarPorIdEstado(marca.getEstado().getId());
	    if (estado == null) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ESTADO");
	    }

	    Marca nuevaMarca = new Marca();
	    nuevaMarca.setNombre(marca.getNombre());
	    nuevaMarca.setEstado(estado);
	    marcaService.guardar(nuevaMarca);

	    return Utilidades.generateResponse(HttpStatus.CREATED, "MARCA CREADA");
	}
	
	@PutMapping("/actualizar")
	public ResponseEntity<Object> actualizarMarca(@RequestBody Marca marca) {

	    Marca marcaExistente = marcaService.buscarPorIdMarca(marca.getId());
	    if (marcaExistente == null) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ LA MARCA");
	    }

	    Marca marcaConNombre = marcaService.nombreExiste(marca.getNombre());
	    if (marcaConNombre != null && !marcaConNombre.getId().equals(marca.getId())) {
	        if (marcaConNombre.getEstado().getId().equals(3)) {
	            Estado estadoActivo = estadoService.buscarPorIdEstado(1);
	            if (estadoActivo == null) {
	                return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ESTADO ACTIVO");
	            }
	            marcaConNombre.setEstado(estadoActivo);
	            marcaService.guardar(marcaConNombre);
	            return Utilidades.generateResponse(HttpStatus.ACCEPTED, "SE ENCONTRÓ UNA MARCA ARCHIVADA CON ESTE NOMBRE, SE RESTAURARÁ A ACTIVO");
	        } else {
	            return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "EL NOMBRE DE LA MARCA YA EXISTE");
	        }
	    }

	    Estado estadoMarca = estadoService.buscarPorIdEstado(marca.getEstado().getId());
	    if (estadoMarca == null) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ESTADO");
	    }

	    marcaService.guardar(marca);
	    if (marca.getEstado().getId() == 3) {
	    	return Utilidades.generateResponse(HttpStatus.CREATED, "MARCA ARCHIVADA CORRECTAMENTE");
	    } else {
		    return Utilidades.generateResponse(HttpStatus.CREATED, "MARCA ACTUALIZADA CORRECTAMENTE");
	    }
	}


}
