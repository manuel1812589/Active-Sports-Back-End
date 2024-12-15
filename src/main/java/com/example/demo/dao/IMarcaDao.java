package com.example.demo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.Marca;
import com.example.demo.models.Usuarios;

public interface IMarcaDao extends JpaRepository<Marca, Integer>{

	Page<Marca> findByNombreContainingAndEstadoIdNot(String nombre, Integer estadoId, Pageable pageable);
	
	Marca findByNombre(String nombre);

}
