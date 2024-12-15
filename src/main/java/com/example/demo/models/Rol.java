package com.example.demo.models;

import java.io.Serializable;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="roles")
public class Rol implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nombre;
	
	@ManyToMany(mappedBy="roles",cascade = {
	          CascadeType.PERSIST,
	          CascadeType.MERGE
	      })
	@JsonIgnore
	private Set<Usuarios> usuario;

	public Rol() {
	}

	public Rol(String nombre) {
		this.nombre = nombre;
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

	public Set<Usuarios> getUsuario() {
		return usuario;
	}

	public void setUsuario(Set<Usuarios> usuario) {
		this.usuario = usuario;
	}
	
}
