package com.example.demo.services;

import java.util.Arrays;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.dao.IRolDao;
import com.example.demo.models.Rol;
import com.example.demo.models.RolDto;
import com.example.demo.utilidades.PaginationMod;



@Service
public class RolService implements IRolService{
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private IRolDao rolDao;
	
	@Override
	public List<RolDto> listarRoles() {
		
		List<Rol> rol = rolDao.findAll();
		
		List<RolDto> dto = Arrays.asList(mapper.map(rol, RolDto[].class));
		
		return dto;
	}

	@Override
	public RolDto buscarRolDtoId(Integer id) {
		
		Rol rol = rolDao.findById(id).orElse(null);
		
		RolDto dto = mapper.map(rol, RolDto.class);
		
		return dto;
	}

	@Override
	public Rol guardar(Rol rol) {
		return rolDao.save(rol);
	}

	@Override
	public void eliminar(Integer id) {
		rolDao.deleteById(id);
	}

	@Override
	public PaginationMod<RolDto> listarRolDtoPaginado(String nombre, Pageable pageable) {
		Page<Rol> paginacion = rolDao.findByNombreContaining(nombre, pageable);
		
		PaginationMod<RolDto> paginationMod = new PaginationMod<RolDto>();
		RolDto[] entityDtos = mapper.map(paginacion.getContent(),RolDto[].class);
		paginationMod.setValue(paginacion, Arrays.asList(entityDtos));
		return paginationMod;
	}

}
