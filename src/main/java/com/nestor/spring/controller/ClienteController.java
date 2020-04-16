package com.nestor.spring.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

	
	@Secured({"ROLE_USER,ROLE_ADMIN"})
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Model model, RedirectAttributes flash) {
		if(id<=0) {
			flash.addFlashAttribute("danger", "El ID del cliente no puede ser menor o igual a cero");
			return "redirect:/";
		}
		
		Cliente cliente = clienteService.findClienteById(id);
		
		if(cliente==null) {
			flash.addFlashAttribute("danger", "El cliente no existe");
			return "redirect:/";
		}
		
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo","Perfil Cliente");
		return "ver";
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/delete/{id}")
	public String borrar(@PathVariable(value="id")Long id,RedirectAttributes flash) {
		Cliente cliente=null;
		cliente=clienteService.findClienteById(id);
		if(cliente!=null) {
			clienteService.delete(id);
		}
		flash.addFlashAttribute("succes","Cliente eliminado con éxito");
		return "redirect:/";
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/form")
	public String alta(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente",cliente);
		model.addAttribute("titulo", "Formulario cliente");
		return "form";
	}
	
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/form")
	public String guardar(@Valid Cliente cliente,BindingResult result, Model model, SessionStatus session, RedirectAttributes flash){	
		
		if(result.hasErrors()) {
			model.addAttribute("titulo","Formulario Cliente");
			return "form";
		}
		String mensaje= (cliente.getId()!=null)? "Cliente editado con éxito":"Cliente creado con éxito";
		clienteService.guardar(cliente);
		session.setComplete();
		flash.addFlashAttribute("succes",mensaje);
		return "redirect:/";
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(value="id")Long id, Model model,RedirectAttributes flash) {
		Cliente cliente=null;
		if(id==0 || id==null) {
			model.addAttribute("titulo","Formulario Cliente");
			flash.addFlashAttribute("danger","Cliente no puede ser nulo ni ID igual a cero");
			return "redirect:/form";
		}
		
		cliente = clienteService.findClienteById(id);
		
		if(cliente!=null){
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Formulario Cliente");
			return "form";
		}else {
			return "redirect:/";
		}
	}
}
