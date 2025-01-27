package com.example.demo.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dao.IEstadoDao;
import com.example.demo.models.Estado;

@Service
public class EstadoService implements IEstadoService{
	
	@Autowired
	private IEstadoDao estadoDao;

	@Override
	public List<Estado> listar() {
		return estadoDao.findAll();
	}

	@Override
	public Estado guardar(Estado estado) {
		return estadoDao.save(estado);
	}

	@Override
	public Estado buscarPorIdEstado(Integer id) {
		return estadoDao.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Integer id) {
		estadoDao.deleteById(id);
	}
	
	@Override
	public Estado buscarPorNombre(String nombre) {
	    return estadoDao.findByNombreIgnoreCase(nombre);  
	}


}
