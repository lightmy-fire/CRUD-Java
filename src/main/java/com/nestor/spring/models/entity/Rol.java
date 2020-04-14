package com.nestor.spring.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Rol implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nombre_rol")
	private String nombreRol;
	
	private static final long serialVersionUID = 1L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre_rol() {
		return nombreRol;
	}
	public void setNombre_rol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	
	
}
