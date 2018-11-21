package br.ufc.quixada.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Transactional
@Controller
public class PrincipalController {

	@Autowired
	private ServletContext servletContext;

	@RequestMapping("/mostrarMensagem")
	public String mostrarMensagem(Model model, HttpSession session){
		System.out.println("Passou aqui!");
		return "teste";
	}
}
