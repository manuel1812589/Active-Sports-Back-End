package com.example.demo.models;

import java.io.Serializable;

public class ProveedorDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String nombre;
	
	private String telefono;
	
	private String correo;

	public ProveedorDto() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
}
