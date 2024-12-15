package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.models.RolDto;
import com.example.demo.models.Usuarios;
import com.example.demo.models.UsuarioDto;
import com.example.demo.services.IRolService;
import com.example.demo.services.IUsuarioService;
import com.example.demo.utilidades.PaginationMod;
import com.example.demo.utilidades.Utilidades;

@RestController
@RequestMapping("/api/usuarios")
public class ApiUsuarios {
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IRolService rolService;
	
	@GetMapping("/listar")
	public Object listar(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "") String nombre) {
				
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		PaginationMod<UsuarioDto> enviar = usuarioService.listarUsuarioDtoPaginado(nombre,PageRequest.of(page, 5, Sort.by("id").ascending()));
		
		List<RolDto> roles = rolService.listarRoles();
		
		map.put("lista", enviar);
		map.put("roles", roles);

	    return map;
	}	
	
	@GetMapping("/listar/{id}")
	public UsuarioDto usuariosId(@PathVariable Integer id) {
		return usuarioService.buscarPorId(id);
	}
	
	@PostMapping("/agregar")
	public ResponseEntity<Object> guardarUsuario(@RequestBody Usuarios usuario) {
	    if (usuarioService.correoExiste(usuario.getCorreo())) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "EL CORREO QUE SE INTENTA REGISTRAR YA EXISTE");
	    }

	    if (usuarioService.dniExiste(usuario.getDni())) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "El DNI QUE SE INTENTA REGISTRAR YA EXISTE");
	    }

	    Usuarios guardarUsuario = new Usuarios();
	    guardarUsuario.setCorreo(usuario.getCorreo());
	    guardarUsuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
	    guardarUsuario.setRoles(usuario.getRoles());
	    guardarUsuario.setNombre(usuario.getNombre());
	    guardarUsuario.setDni(usuario.getDni());
	    usuarioService.guardar(guardarUsuario);

	    return Utilidades.generateResponse(HttpStatus.CREATED, "USUARIO CREADO");
	}

	@PutMapping("/actualizar")
	public ResponseEntity<Object> actualizarUsuario(@RequestBody Usuarios usuario) {
	    Usuarios userActual = usuarioService.buscarPorIdUsuario(usuario.getId());

	    if (!userActual.getCorreo().equals(usuario.getCorreo()) && usuarioService.correoExiste(usuario.getCorreo())) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "EL CORREO QUE SE INTENTA REGISTRAR YA EXISTE");
	    }

	    if (!userActual.getDni().equals(usuario.getDni()) && usuarioService.dniExiste(usuario.getDni())) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "El DNI QUE SE INTENTA REGISTRAR YA EXISTE");
	    }

	    Usuarios guardarUsuario = new Usuarios();
	    guardarUsuario.setId(usuario.getId());
	    guardarUsuario.setCorreo(usuario.getCorreo());
	    guardarUsuario.setPassword(usuario.getPassword().isEmpty() ? 
	        userActual.getPassword() : passwordEncoder.encode(usuario.getPassword()));
	    guardarUsuario.setRoles(usuario.getRoles());
	    guardarUsuario.setNombre(usuario.getNombre());
	    guardarUsuario.setDni(usuario.getDni());
	    usuarioService.guardar(guardarUsuario);

	    return Utilidades.generateResponse(HttpStatus.CREATED, "USUARIO ACTUALIZADO");
	}

	
	@PostMapping("/eliminar")
	public ResponseEntity<Object> eliminarUsuaroi(@RequestBody Usuarios usuario){
			
			usuarioService.eliminar(usuario.getId());
			return Utilidades.generateResponse(HttpStatus.CREATED, "USUARIO ELIMINADO");
		
	}
}
