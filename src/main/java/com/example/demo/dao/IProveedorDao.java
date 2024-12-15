package com.example.demo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.Proveedor;

public interface IProveedorDao extends JpaRepository<Proveedor, Integer>{
	
	public Page<Proveedor> findByNombreContaining(String nombre, Pageable pageable);

}
