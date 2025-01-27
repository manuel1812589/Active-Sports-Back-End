package com.example.demo.services;

import java.util.Arrays;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.dao.IProveedorDao;
import com.example.demo.models.Proveedor;
import com.example.demo.models.ProveedorDto;
import com.example.demo.utilidades.PaginationMod;

@Service
public class ProveedorService implements IProveedorService{
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private IProveedorDao proveedorDao;

	@Override
	public PaginationMod<ProveedorDto> listarProveedorDtoPaginado(String nombre, Pageable pageable) {
		Page<Proveedor> paginacion = proveedorDao.findByNombreContaining(nombre, pageable);
		
		PaginationMod<ProveedorDto> paginationMod = new PaginationMod<ProveedorDto>();
		ProveedorDto[] entityDtos = mapper.map(paginacion.getContent(),ProveedorDto[].class);
		paginationMod.setValue(paginacion, Arrays.asList(entityDtos));
		return paginationMod;
	}

	@Override
	public List<Proveedor> listarProveedor() {
		return proveedorDao.findAll();
	}

	@Override
	public Proveedor buscarIdProveedor(Integer id) {
		return proveedorDao.findById(id).orElse(null);
	}

	@Override
	public Proveedor guardarProveedor(Proveedor proveedor) {
		return proveedorDao.save(proveedor);
	}

	@Override
	public void eliminar(Integer id) {
		proveedorDao.deleteById(id);
		
	}
	
    @Override
    public boolean existePorNombre(String nombre) {
        return proveedorDao.existsByNombre(nombre);
    }

    @Override
    public boolean existePorTelefono(String telefono) {
        return proveedorDao.existsByTelefono(telefono);
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return proveedorDao.existsByCorreo(correo);
    }

    @Override
    public boolean existePorNombreYDistintoId(String nombre, Integer id) {
        return proveedorDao.existsByNombreAndIdNot(nombre, id);
    }

    @Override
    public boolean existePorTelefonoYDistintoId(String telefono, Integer id) {
        return proveedorDao.existsByTelefonoAndIdNot(telefono, id);
    }

    @Override
    public boolean existePorCorreoYDistintoId(String correo, Integer id) {
        return proveedorDao.existsByCorreoAndIdNot(correo, id);
    }

}
