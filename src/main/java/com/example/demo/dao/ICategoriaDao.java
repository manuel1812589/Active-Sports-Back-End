package com.example.demo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.Categoria;

public interface ICategoriaDao extends JpaRepository<Categoria, Integer> {

    Page<Categoria> findByNombreContainingAndEstadoIdNot(String nombre, Integer estadoId, Pageable pageable);
    
    Categoria findByNombre(String nombre);

}
