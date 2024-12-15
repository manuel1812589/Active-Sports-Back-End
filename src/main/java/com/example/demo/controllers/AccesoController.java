package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Rol;
import com.example.demo.models.Usuarios;
import com.example.demo.services.IUsuarioService;
import com.example.demo.jwt.AuthRequest;
import com.example.demo.jwt.AuthResponse;

import com.example.demo.jwt.JwtTokenUtil;

@RestController
@RequestMapping("/api/v1")
public class AccesoController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtTokenUtil jwtUtil;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
	    try {
	        Authentication authentication = this.authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword()));
	        Optional<Usuarios> user = usuarioService.buscarPorCorreo(request.getCorreo());
	        String accessToken = jwtUtil.generarToken(user.get());

	        // Obtener los roles del usuario y devolverlos junto con el token
	        List<String> roles = user.get().getRoles().stream().map(Rol::getNombre).collect(Collectors.toList());

	        AuthResponse response = new AuthResponse(request.getCorreo(), accessToken, roles);
	        
	        return ResponseEntity.ok(response);
	    } catch (BadCredentialsException e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	}
}
