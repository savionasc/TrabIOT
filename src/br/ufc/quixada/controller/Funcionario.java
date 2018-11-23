package br.ufc.quixada.controller;

public class Funcionario {
	public String nome;
	public boolean ocupado;
	public String quarto;
	public Funcionario(String nome) {
		super();
		this.nome = nome;
		this.ocupado = false;
	}
	
	public void addOcupacao(String quarto){
		this.quarto = quarto;
		this.ocupado = true;
	}
	
	public String removeOcupacao(){
		String retorno = this.quarto;
		this.quarto = "";
		this.ocupado = false;
		return retorno;
	}
}
