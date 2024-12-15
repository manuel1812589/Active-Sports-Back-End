package com.example.demo.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import com.example.demo.models.Marca;
import com.example.demo.models.MarcasDto;
import com.example.demo.models.Usuarios;
import com.example.demo.utilidades.PaginationMod;

public interface IMarcaService {
	
	List<Marca> listar();
	
	PaginationMod<MarcasDto> listarMarcaDtoPaginado(String nombre, Pageable pageable);
	
	Marca guardar(Marca marca);
	
	Marca buscarPorIdMarca(Integer id);
	
	void eliminar(Integer id);
	
	Marca nombreExiste(String nombre);
	
}
