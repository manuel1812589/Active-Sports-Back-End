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
import com.example.demo.models.Rol;
import com.example.demo.models.RolDto;
import com.example.demo.services.IRolService;
import com.example.demo.utilidades.PaginationMod;
import com.example.demo.utilidades.Utilidades;

@RestController
@RequestMapping("/api/roles")
public class ApiRol {

	@Autowired
	private IRolService rolService;

	@GetMapping("/listarv1")
	public List<RolDto> roles() {
		return rolService.listarRoles();
	}
	
	@GetMapping("/listar")
	public Object listar(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "") String nombre) {
	
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		PaginationMod<RolDto> enviar = rolService.listarRolDtoPaginado(nombre,PageRequest.of(page, 20, Sort.by("id").ascending()));
		
		map.put("lista", enviar);
		
	    return map;
	}

	@GetMapping("/listar/{id}")
	public RolDto rolesId(@PathVariable Integer id) {
		return rolService.buscarRolDtoId(id);
	}

	@PostMapping("/agregar")
	public ResponseEntity<Object> rolesGuardar(@RequestBody RolDto dto) {

		Rol rolGuardar = new Rol();
		String url = Utilidades.getRol(dto.getNombre());
		rolGuardar.setNombre(url);

		rolService.guardar(rolGuardar);
		return Utilidades.generateResponseTrue(HttpStatus.CREATED, "ROL CREADO CORRECTAMENTE");
	}

	@PutMapping("/actualizar")
	public ResponseEntity<Object> rolesActualizar(@RequestBody RolDto dto) {

		RolDto rolDto = rolService.buscarRolDtoId(dto.getId());

		if (rolDto == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ROL");
		}

		Rol rolGuardar = new Rol();

		rolGuardar.setId(dto.getId());
		rolGuardar.setNombre(dto.getNombre());
		rolService.guardar(rolGuardar);
		return Utilidades.generateResponseTrue(HttpStatus.CREATED, "ROL ACTUALIZADO CORRECTAMENTE");
	}

	@PostMapping("/eliminar")
	public ResponseEntity<Object> eliminar(@RequestBody RolDto dto) {
		RolDto rolDto = rolService.buscarRolDtoId(dto.getId());

		if (rolDto == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ROL");
		}
		
		rolService.eliminar(dto.getId());
		return Utilidades.generateResponseTrue(HttpStatus.OK, "ROL ELIMINADO CORRECTAMENTE");
	}
}
