package com.example.demo.services;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.dao.IMarcaDao;
import com.example.demo.models.Marca;
import com.example.demo.models.MarcasDto;
import com.example.demo.utilidades.PaginationMod;

@Service
public class MarcaService implements IMarcaService{
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private IMarcaDao marcaDao;

	@Override
	public PaginationMod<MarcasDto> listarMarcaDtoPaginado(String nombre, Pageable pageable) {
	    // Filtrar marcas donde el estado_id no es 3
	    Integer estadoExcluido = 3;
	    Page<Marca> paginacion = marcaDao.findByNombreContainingAndEstadoIdNot(nombre, estadoExcluido, pageable);
	    
	    PaginationMod<MarcasDto> paginationMod = new PaginationMod<MarcasDto>();
	    MarcasDto[] entityDtos = mapper.map(paginacion.getContent(), MarcasDto[].class);
	    paginationMod.setValue(paginacion, Arrays.asList(entityDtos));
	    return paginationMod;
	}


	@Override
	public Marca guardar(Marca marca) {
		return marcaDao.save(marca);
	}

	@Override
	public Marca buscarPorIdMarca(Integer id) {
		return marcaDao.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Integer id) {
		marcaDao.deleteById(id);
	}

	@Override
	public Marca nombreExiste(String nombre) {
	    return marcaDao.findByNombre(nombre);
	}


	@Override
	public List<Marca> listar() {
		return marcaDao.findAll();
	}

}
