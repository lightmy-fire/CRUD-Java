package com.nestor.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nestor.spring.service.JpaUserDetailsService;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {	
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void ConfigureGlobal(AuthenticationManagerBuilder build) throws Exception {
		
		System.out.println("Lo que retorna userDetailsService "+userDetailsService);
		
		System.out.println("Qué es: build.userDetailsService(userDetailsService).passwordEncoder(this.passwordEncoder())"+build.userDetailsService(userDetailsService).passwordEncoder(this.passwordEncoder()));
		
		build.userDetailsService(userDetailsService).passwordEncoder(this.passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/","/home","/ver/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login").permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
	}

	
	
}
