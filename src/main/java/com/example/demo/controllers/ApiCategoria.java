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
import com.example.demo.models.Categoria;
import com.example.demo.models.MarcasDto;
import com.example.demo.models.Categoria;
import com.example.demo.models.CategoriaDto;
import com.example.demo.services.IEstadoService;
import com.example.demo.services.ICategoriaService;
import com.example.demo.utilidades.PaginationMod;
import com.example.demo.utilidades.Utilidades;

@RestController
@RequestMapping("/api/categoria")
public class ApiCategoria {

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private IEstadoService estadoService;

	@GetMapping("/listar")
	public Object listar(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "") String nombre) {
				
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		PaginationMod<CategoriaDto> enviar = categoriaService.listarCategoriaDtoPaginado(nombre,PageRequest.of(page, 5, Sort.by("id").ascending()));
		
		List<Estado> estado = estadoService.listar();
		
		map.put("lista", enviar);
		map.put("estado", estado);

	    return map;
	}
	
	@PostMapping("/agregar")
	public ResponseEntity<Object> guardarCategoria(@RequestBody Categoria marca) {

	    Categoria marcaExistente = categoriaService.nombreExiste(marca.getNombre());
	    if (marcaExistente != null) {
	        if (marcaExistente.getEstado().getId().equals(3)) {
	            Estado estadoActivo = estadoService.buscarPorIdEstado(1);
	            if (estadoActivo == null) {
	                return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ESTADO ACTIVO");
	            }
	            marcaExistente.setEstado(estadoActivo);
	            categoriaService.guardar(marcaExistente);
	            return Utilidades.generateResponse(HttpStatus.ACCEPTED, "SE ENCONTRÓ UNA CATEGORÍA ARCHIVADA CON ESTE NOMBRE, SE RESTAURARÁ A ACTIVO");
	        } else {
	            return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "EL NOMBRE DE LA CATEGORÍA QUE SE INTENTA REGISTRAR YA EXISTE");
	        }
	    }

	    Estado estado = estadoService.buscarPorIdEstado(marca.getEstado().getId());
	    if (estado == null) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ESTADO");
	    }

	    Categoria nuevaMarca = new Categoria();
	    nuevaMarca.setNombre(marca.getNombre());
	    nuevaMarca.setEstado(estado);
	    categoriaService.guardar(nuevaMarca);

	    return Utilidades.generateResponse(HttpStatus.CREATED, "CATEGORÍA CREADA CORRECTAMENTE");
	}
	
	@PutMapping("/actualizar")
	public ResponseEntity<Object> actualizarCategoria(@RequestBody Categoria marca) {

	    Categoria marcaExistente = categoriaService.buscarPorIdCategoria(marca.getId());
	    if (marcaExistente == null) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ LA CATEGORÍA");
	    }

	    Categoria categoriaConNombre = categoriaService.nombreExiste(marca.getNombre());
	    if (categoriaConNombre != null && !categoriaConNombre.getId().equals(marca.getId())) {
	        if (categoriaConNombre.getEstado().getId().equals(3)) {
	            Estado estadoActivo = estadoService.buscarPorIdEstado(1);
	            if (estadoActivo == null) {
	                return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ESTADO ACTIVO");
	            }
	            categoriaConNombre.setEstado(estadoActivo);
	            categoriaService.guardar(categoriaConNombre);
	            return Utilidades.generateResponse(HttpStatus.ACCEPTED, "SE ENCONTRÓ UNA CATEGORÍA ARCHIVADA CON ESTE NOMBRE, SE RESTAURARÁ A ACTIVO");
	        } else {
	            return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "EL NOMBRE DE LA CATEGORÍA YA EXISTE");
	        }
	    }

	    Estado estadoMarca = estadoService.buscarPorIdEstado(marca.getEstado().getId());
	    if (estadoMarca == null) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ESTADO");
	    }

	    categoriaService.guardar(marca);
	    if (marca.getEstado().getId() == 3) {
	    	return Utilidades.generateResponse(HttpStatus.CREATED, "CATEGORÍA ARCHIVADA CORRECTAMENTE");
	    } else {
		    return Utilidades.generateResponse(HttpStatus.CREATED, "CATEGORÍA ACTUALIZADA CORRECTAMENTE");
	    }
	}

}
