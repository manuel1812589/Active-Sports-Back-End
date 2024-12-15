package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Usuarios;

public interface IUsuarioDao extends JpaRepository<Usuarios, Integer>{
	
	Optional<Usuarios> findByCorreo(String username);
	
	Page<Usuarios> findByCorreoContaining(String username, Pageable pageable);
	
    boolean existsByCorreo(String correo);
    boolean existsByDni(Integer dni);
	
}
