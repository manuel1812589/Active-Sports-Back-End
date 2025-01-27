package com.example.demo.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import com.example.demo.models.Categoria;
import com.example.demo.models.CategoriaDto;
import com.example.demo.utilidades.PaginationMod;

public interface ICategoriaService {
    
    List<Categoria> listar();
    
    PaginationMod<CategoriaDto> listarCategoriaDtoPaginado(String nombre, Pageable pageable);
    
    Categoria guardar(Categoria categoria);
    
    Categoria buscarPorIdCategoria(Integer id);
    
    void eliminar(Integer id);
    
    Categoria nombreExiste(String nombre);
}
