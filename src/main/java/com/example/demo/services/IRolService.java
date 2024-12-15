package com.example.demo.services;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.example.demo.models.Rol;
import com.example.demo.models.RolDto;
import com.example.demo.utilidades.PaginationMod;

public interface IRolService {
	
	PaginationMod<RolDto> listarRolDtoPaginado(String nombre, Pageable pageable);

	List<RolDto> listarRoles();
	
	RolDto buscarRolDtoId(Integer id);
	
	Rol guardar(Rol rol);
	
	void eliminar(Integer id);
	
}
