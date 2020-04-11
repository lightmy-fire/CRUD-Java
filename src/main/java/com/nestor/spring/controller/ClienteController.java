package com.nestor.spring.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.nestor.spring.models.entity.Cliente;
import com.nestor.spring.service.IClienteService;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	@Autowired
	private IClienteService clienteService;

	@GetMapping({"/","/home"})
	public String listar(Model model) {
		List<Cliente> clientes = clienteService.listar();
		model.addAttribute("clientes",clientes);
		model.addAttribute("titulo","Lista de Clientes");			
		return "index";
	}
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Model model) {
		Cliente cliente = clienteService.findClienteById(id);
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo","Perfil Cliente");
		return "ver";
	}
	
	@GetMapping("/delete/{id}")
	public String borrar(@PathVariable(value="id")Long id) {
		Cliente cliente=null;
		cliente=clienteService.findClienteById(id);
		if(cliente!=null) {
			clienteService.delete(id);
		}
		return "redirect:/";
	}
	
	@GetMapping("/form")
	public String alta(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente",cliente);
		model.addAttribute("titulo", "Formulario cliente");
		return "form";
	}
	
	@PostMapping("/form")
	public String guardar(@Valid Cliente cliente,BindingResult result, Model model, SessionStatus session){	
		
		if(result.hasErrors()) {
			return "form";
		}
		
		clienteService.guardar(cliente);
		session.setComplete();
		return "redirect:/";
	}
	
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(value="id")Long id, Model model) {
		Cliente cliente=null;
		if(id==0 || id==null) {
			return "redirect:/form";
		}
		
		cliente = clienteService.findClienteById(id);
		
		if(cliente!=null){
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Editar Cliente");
			return "form";
		}else {
			return "/";
		}
	}
}
