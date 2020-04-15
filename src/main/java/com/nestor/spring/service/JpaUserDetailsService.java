package com.nestor.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nestor.spring.models.dao.IUsuarioDao;
import com.nestor.spring.models.entity.Rol;
import com.nestor.spring.models.entity.Usuario;

@Service("userDetailsService")
public class JpaUserDetailsService implements UserDetailsService{
	
	@Autowired
	private IUsuarioDao usuarioDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioDao.findUserByUsername(username);
		
		if(usuario==null) {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		
		for(Rol rol: usuario.getRoles()) {
			
			roles.add(new SimpleGrantedAuthority(rol.getNombre_rol()));
			
			System.out.println(" usuario.getRoles() "+usuario.getRoles());
			System.out.println(" rol.getNombreRol() "+rol);
		}
		
		if(roles.isEmpty()) {
			throw new UsernameNotFoundException("El usuario no tiene roles asignados");
		}
		
		User user=new User(usuario.getUsername(), usuario.getPassword(), usuario.getHabilitado(),true,true,true, roles);
		
		System.out.println("USER: "+user.toString());
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getHabilitado(),true,true,true, roles);
	}

}
