package com.example.demo.models;

import java.io.Serializable;

public class ProveedorDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String nombre;
	
	private String contacto;
	
	private String direccion;

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

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
}
