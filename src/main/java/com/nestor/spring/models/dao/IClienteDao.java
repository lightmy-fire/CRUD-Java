package com.nestor.spring.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.nestor.spring.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{

}
