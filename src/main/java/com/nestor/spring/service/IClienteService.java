package com.nestor.spring.service;

import java.util.List;

import com.nestor.spring.models.entity.Cliente;

public interface IClienteService {

	public List<Cliente> listar();
	public Cliente findClienteById(Long id);
	public void delete(Long id);
	public void guardar(Cliente cliente);
}
