package com.nestor.spring.models.dao;

import org.springframework.data.repository.CrudRepository;
import com.nestor.spring.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario,Long>{
	
	public Usuario findUserByUsername(String username);

}
