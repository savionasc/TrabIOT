package br.ufc.quixada.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Transactional
@Controller
public class PrincipalController {

	@Autowired
	private ServletContext servletContext;
	
	List<String> cinemaria = new ArrayList<String>();
	List<Integer[]> distancias = new ArrayList<Integer[]>();
		
	List funcOcupados = new ArrayList<Funcionario>();
	List funcDesocupados = new ArrayList<Funcionario>();

	//Construtor da aplicação
	int construtor(){
		cinemaria.add("beatriz");
		distancias.add(new Integer[]{0, 1, 2, 3, 4});
		cinemaria.add("jesca");
		distancias.add(new Integer[]{1, 0, 1, 2, 3});
		cinemaria.add("segundo");
		distancias.add(new Integer[]{2, 1, 0, 1, 2});
		cinemaria.add("vinicius");
		distancias.add(new Integer[]{3, 2, 1, 0, 1});
		cinemaria.add("yuri");
		distancias.add(new Integer[]{4, 3, 2, 1, 0});
		addFuncionario("Carlos");
		addFuncionario("Savio");
		listarFuncionarios();
		addOcupacao("segundo");
		addOcupacao("vinicius");
		listarFuncionarios();
		addOcupacao("jesca");
		addOcupacao("beatriz");
		addOcupacao("yuri");
		desocupar("beatriz");
		desocupar("beatriz");
		listarFuncionarios();
		desocupar("jesca");
		desocupar("yuri");
		listarFuncionarios();
		return 0;
	}
	
	//Quando o func fizer login no app
	void addFuncionario(String nome){
		Funcionario f = new Funcionario(nome);		
		funcDesocupados.add(f);
	}
	
	//Quando solicitarem um func para limpar um quarto
	void addOcupacao(String quarto){
		//Se houver funcionario desocupado
		if(funcDesocupados.size() > 0){
			Funcionario f = ((Funcionario) funcDesocupados.remove(0));
			f.addOcupacao(quarto);
			funcOcupados.add(f);
			System.out.println("Func adicionado; Funcionario chamado: "+f.nome);
			//System.out.println("Func adicionado");
		}
		//Se houver funcionarios ocupados
		else if(funcOcupados.size() > 0){
			int idMelhor = -1;
			int distancia = 100;
			for (int i = 0; i < funcOcupados.size(); i++) {
				if(i != idMelhor){
					String quartoAtual = ((Funcionario)funcOcupados.get(i)).quarto;
					int idQuartoAtual = cinemaria.indexOf(quartoAtual);
					int idQuartoDesejado = cinemaria.indexOf(quarto);
					int dist = distancias.get(idQuartoAtual)[idQuartoDesejado];
					if(dist < distancia){
						distancia = dist;
						idMelhor = i;
					}
				}
			}
			
			Funcionario f = ((Funcionario)funcOcupados.remove(idMelhor));
			f.addOcupacao(quarto);
			funcOcupados.add(f);
			
			System.out.println("Func adicionado; Funcionario chamado: "+f.nome+" id: "+idMelhor+" Dist: "+distancia);
		}
	}
	
	void desocupar(String quarto){
		for (int i = 0; i < funcOcupados.size(); i++) {
			Funcionario f = ((Funcionario)funcOcupados.get(i));
			if(f.quarto.equals(quarto)){
				f = ((Funcionario)funcOcupados.remove(i));
				f.removeOcupacao();
				funcDesocupados.add(f);
				System.out.println("o F: "+f.nome+" desocupou o q: "+quarto);
				return;
			}
		}
	}

	void listarFuncionarios(){
		System.out.println("Funcionários:");
		for (int i = 0; i < funcDesocupados.size(); i++) {
			Funcionario f = ((Funcionario)funcDesocupados.get(i));
			System.out.println(f.nome+" está desocupado");
		}
		
		for (int i = 0; i < funcOcupados.size(); i++) {
			Funcionario f = ((Funcionario)funcOcupados.get(i));
			System.out.println(f.nome+" está no quarto: "+f.quarto);
		}
	}

	@RequestMapping("/mostrarMensagem")
	public String mostrarMensagem(Model model, HttpSession session){
		construtor();
		System.out.println("Passou aqui!");
		return "teste";
	}
}
