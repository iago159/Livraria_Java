package br.com.livraria.model;

import java.util.Date;

public class Compras {
	
	private int idcompra;
	private Date data;
	private int idlivro;
	private int idcliente;
	private String condicaoCompra;
	private double valor;
	
	public Compras(Date data, int idcliente, int idlivro, double valor) {
		this.setData(data);
		this.setIdcliente(idcliente);
		this.setIdlivro(idlivro);
		this.setValor(valor);
		this.condicaoCompra = "ativa";
	}
	
	public int getIdcompra() {
		return idcompra;
	}
	
	public void setIdcompra(int idcompra) {
		this.idcompra = idcompra;
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public int getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}
	
	public int getIdlivro() {
		return idlivro;
	}

	public void setIdlivro(int idlivro) {
		this.idlivro = idlivro;
	}
	
	public String getCondicaoCompra() {
		return this.condicaoCompra;
	}
	
	public void setCondicaoConta(String condicao) {
		this.condicaoCompra = condicao;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getValor() {
		return this.valor;
	}

}
