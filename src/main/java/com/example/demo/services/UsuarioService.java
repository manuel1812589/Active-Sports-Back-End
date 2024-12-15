package com.example.demo.services;

import java.util.Arrays;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.IUsuarioDao;
import com.example.demo.models.Usuarios;
import com.example.demo.models.UsuarioDto;
import com.example.demo.utilidades.PaginationMod;

@Service
public class UsuarioService implements IUsuarioService {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private IUsuarioDao usuarioDao;

	@Override
	public Optional<Usuarios> buscarPorCorreo(String nombre) {
		return usuarioDao.findByCorreo(nombre);
	}

	@Override
	public Page<Usuarios> listarUsuario(String nombre, Pageable pageable) {
		return usuarioDao.findByCorreoContaining(nombre, pageable);
	}

	@Override
	public UsuarioDto buscarPorId(Integer id) {
		Usuarios usu =  usuarioDao.findById(id).orElse(null);
		
		UsuarioDto dto = mapper.map(usu, UsuarioDto.class);
		
		return dto;
	}

	@Override
	public PaginationMod<UsuarioDto> listarUsuarioDtoPaginado(String nombre, Pageable pageable) {
		Page<Usuarios> paginacion = usuarioDao.findByCorreoContaining(nombre, pageable);
		
		PaginationMod<UsuarioDto> paginationMod = new PaginationMod<UsuarioDto>();
		UsuarioDto[] entityDtos = mapper.map(paginacion.getContent(),UsuarioDto[].class);
		paginationMod.setValue(paginacion, Arrays.asList(entityDtos));
		return paginationMod;
	}

	@Override
	public Usuarios guardar(Usuarios usuario) {
		return usuarioDao.save(usuario);
	}

	@Override
	public Usuarios buscarPorIdUsuario(Integer id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Integer id) {
		usuarioDao.deleteById(id);
	}
	
    @Override
    public boolean correoExiste(String correo) {
        return usuarioDao.existsByCorreo(correo);
    }

    @Override
    public boolean dniExiste(Integer dni) {
        return usuarioDao.existsByDni(dni);
    }
}
