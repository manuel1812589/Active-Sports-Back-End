package com.example.demo.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.models.Usuarios;
import com.example.demo.models.UsuarioDto;
import com.example.demo.utilidades.PaginationMod;

public interface IUsuarioService {

	Optional<Usuarios> buscarPorCorreo(String nombre);
	
	UsuarioDto buscarPorId(Integer id);
	
	Usuarios buscarPorIdUsuario(Integer id);
	
	Page<Usuarios> listarUsuario(String nombre, Pageable pageable);
	
	PaginationMod<UsuarioDto> listarUsuarioDtoPaginado(String nombre, Pageable pageable);
	
	Usuarios guardar(Usuarios usuario);
	
	void eliminar(Integer id);
	
	boolean correoExiste(String correo);
	
	boolean dniExiste(Integer dni);
	
}
