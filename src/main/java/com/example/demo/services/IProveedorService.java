package com.example.demo.services;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.example.demo.models.Proveedor;
import com.example.demo.models.ProveedorDto;
import com.example.demo.utilidades.PaginationMod;

public interface IProveedorService {
	
	PaginationMod<ProveedorDto> listarProveedorDtoPaginado(String nombre, Pageable pageable);
	
	List<Proveedor> listarProveedor();

	Proveedor buscarIdProveedor(Integer id);
	
	Proveedor guardarProveedor(Proveedor proveedor);
	
	void eliminar(Integer id);
	
    boolean existePorNombre(String nombre);
    boolean existePorTelefono(String telefono);
    boolean existePorCorreo(String correo);

    boolean existePorNombreYDistintoId(String nombre, Integer id);
    boolean existePorTelefonoYDistintoId(String telefono, Integer id);
    boolean existePorCorreoYDistintoId(String correo, Integer id);

}
