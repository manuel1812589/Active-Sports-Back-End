package com.example.demo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.Rol;

public interface IRolDao extends JpaRepository<Rol, Integer>{

	public Page<Rol> findByNombreContaining(String nombre, Pageable pageable);
	
}
