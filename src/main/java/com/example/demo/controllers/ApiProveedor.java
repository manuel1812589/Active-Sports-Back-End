package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.models.Proveedor;
import com.example.demo.models.ProveedorDto;
import com.example.demo.services.IProveedorService;
import com.example.demo.utilidades.PaginationMod;
import com.example.demo.utilidades.Utilidades;

@RestController
@RequestMapping("/api/proveedor")
public class ApiProveedor {
	
	@Autowired
	private IProveedorService proveedorService;
	
	@GetMapping("/listarv1")	
	public List<Proveedor> proveedor() {
		return proveedorService.listarProveedor();
	}

	@GetMapping("/listar/{id}")
	public Proveedor proveedorId(@PathVariable Integer id) {
		return proveedorService.buscarIdProveedor(id);
	}
	
	@GetMapping("/listar")
	public Object listar(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "") String nombre) {
	
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		PaginationMod<ProveedorDto> enviar = proveedorService.listarProveedorDtoPaginado(nombre,PageRequest.of(page, 10, Sort.by("id").ascending()));
		
		map.put("lista", enviar);
		
	    return map;
	}

	@PostMapping("/agregar")
	public ResponseEntity<Object> proveedorGuardar(@RequestBody Proveedor proveedor) {
	    if (proveedorService.existePorNombre(proveedor.getNombre())) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "YA EXISTE UN PROVEEDOR CON ESE NOMBRE");
	    }
	    if (proveedorService.existePorTelefono(proveedor.getTelefono())) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "YA EXISTE UN PROVEEDOR CON ESE TELÉFONO");
	    }
	    if (proveedorService.existePorCorreo(proveedor.getCorreo())) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "YA EXISTE UN PROVEEDOR CON ESE CORREO");
	    }

	    proveedorService.guardarProveedor(proveedor);
	    return Utilidades.generateResponse(HttpStatus.CREATED, "PROVEEDOR CREADO CORRECTAMENTE");
	}

	@PutMapping("/actualizar")
	public ResponseEntity<Object> proveedorActualizar(@RequestBody Proveedor proveedor) {

	    Proveedor proveedorBuscar = proveedorService.buscarIdProveedor(proveedor.getId());
	    if (proveedorBuscar == null) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL PROVEEDOR");
	    }

	    if (proveedorService.existePorNombreYDistintoId(proveedor.getNombre(), proveedor.getId())) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "YA EXISTE OTRO PROVEEDOR CON ESE NOMBRE");
	    }
	    if (proveedorService.existePorTelefonoYDistintoId(proveedor.getTelefono(), proveedor.getId())) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "YA EXISTE OTRO PROVEEDOR CON ESE TELÉFONO");
	    }
	    if (proveedorService.existePorCorreoYDistintoId(proveedor.getCorreo(), proveedor.getId())) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "YA EXISTE OTRO PROVEEDOR CON ESE CORREO");
	    }

	    proveedorService.guardarProveedor(proveedor);
	    return Utilidades.generateResponse(HttpStatus.CREATED, "PROVEEDOR ACTUALIZADO CORRECTAMENTE");
	}

	@PostMapping("/eliminar")
	public ResponseEntity<Object> eliminar(@RequestBody Proveedor proveedor) {
		Proveedor proveedorBuscar = proveedorService.buscarIdProveedor(proveedor.getId());

		if (proveedorBuscar == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL PROVEEDOR");
		}
		
		proveedorService.eliminar(proveedor.getId());
		return Utilidades.generateResponse(HttpStatus.CREATED, "PROVEEDOR ELIMINADO CORRECTAMENTE");
	}

}
