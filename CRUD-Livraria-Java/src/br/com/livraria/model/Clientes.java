package br.com.livraria.model;

import java.util.ArrayList;

public class Clientes {
	
	private int idcliente;
	private String nome;
	private String sexo;
	private int qtdLivrosComprados;
	private String condicaoConta;
	private ArrayList<Integer> livrosComprados = new ArrayList<>();
	
	public Clientes(String nome, String sexo) {
		this.setNome(nome);
		this.setSexo(sexo);
		this.qtdLivrosComprados = 0;
		this.setCondicaoConta("ativa");
	}
	
	public int getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		if(nome.length() <= 50)
			this.nome = nome;
		else
			System.out.println("O nome digitado possui mais de 50 caracteres. Tente um nome menor");	}
	
	public String getSexo() {
		return sexo;
	}	
	public void setSexo(String sexo) {
		this.sexo = sexo;			
	}
	
	public ArrayList<Integer> getLivrosComprados() {
		return livrosComprados;
	}
	public void setLivrosComprados(Integer livrosComprados) {
		this.livrosComprados.add(livrosComprados);
	}
	
	public int getQtdLivrosComprados() {
		return qtdLivrosComprados;
	}
	public void setQtdLivrosComprados(int qtdLivrosComprados) {
		int qtdFinalLivrosComprados = this.getQtdLivrosComprados() + qtdLivrosComprados;
		this.qtdLivrosComprados = qtdFinalLivrosComprados;
	}
	
	public String getCondicaoConta() {
		return this.condicaoConta;
	}
	
	public void setCondicaoConta(String condicao) {
		this.condicaoConta = condicao;
	}

}
