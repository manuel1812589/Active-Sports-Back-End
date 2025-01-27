package com.example.demo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.Proveedor;

public interface IProveedorDao extends JpaRepository<Proveedor, Integer>{
	
	public Page<Proveedor> findByNombreContaining(String nombre, Pageable pageable);
	
    boolean existsByNombre(String nombre);

    boolean existsByTelefono(String telefono);

    boolean existsByCorreo(String correo);

    boolean existsByNombreAndIdNot(String nombre, Integer id);

    boolean existsByTelefonoAndIdNot(String telefono, Integer id);

    boolean existsByCorreoAndIdNot(String correo, Integer id);

}
