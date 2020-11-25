package br.com.livraria.model;

public class Clientes {
	
	private int idcliente;
	private String nome;
	private String sexo;
	private String condicaoConta;
	
	public Clientes(String nome, String sexo) {
		this.setNome(nome);
		this.setSexo(sexo);
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

	
	public String getCondicaoConta() {
		return this.condicaoConta;
	}
	
	public void setCondicaoConta(String condicao) {
		this.condicaoConta = condicao;
	}

}
