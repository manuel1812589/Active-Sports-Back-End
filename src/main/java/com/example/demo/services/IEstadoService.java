package com.example.demo.services;

import java.util.List;
import com.example.demo.models.Estado;

public interface IEstadoService {
	
	List<Estado> listar();
	
	Estado guardar(Estado estado);
	
	Estado buscarPorIdEstado(Integer id);
	
	void eliminar(Integer id);

}
