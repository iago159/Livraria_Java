package br.com.livraria.model;
import java.text.DecimalFormat;

public class Livros {
	
	DecimalFormat formatador = new DecimalFormat("###.##");
	
	private int idlivro;
	private String nome;
	private String estilo; 
	private int estoque;
	private String sinopse;
	private double preco;
	private int qtdLivrosVendidos;
	
	public Livros(String nome, String estilo, int estoque, String sinopse, double preco) {
		this.setNome(nome);
		this.setEstilo(estilo);
		this.estoque = estoque;
		this.setSinopse(sinopse);
		this.setPreco(preco);
		this.qtdLivrosVendidos = 0;
	}
	
	
	
	public int getIdlivro() {
		return idlivro;
	}
	public void setIdlivro(int idlivro2) {
		this.idlivro = idlivro2;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		if(nome.length() <= 150)
			this.nome = nome;
		else
			System.out.println("O nome digitado possui mais de 150 caracteres. Tente um nome menor");
	}

	public String getEstilo() {
		return estilo;
	}
	public void setEstilo(String estilo) {
		if(estilo.length() <= 200)
			this.estilo = estilo;
		else
			System.out.println("O estilo digitado possui mais de 200 caracteres. Tente um nome menor");
	}
	
	public String getSinopse() {
		return sinopse;
	}
	public void setSinopse(String sinopse) {
		this.sinopse = sinopse.length() <= 250 ? sinopse : "Sinópse muito grande para ser lida";
	}	
	
	public int getEstoque() {
		return estoque;
	}
	public void setEstoque(int estoque) {
		int qtdFinalEstoque = this.getEstoque() + estoque;
		this.estoque = qtdFinalEstoque;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	public int getQtdLivrosVendidos() {
		return qtdLivrosVendidos;
	}
	
	// ENTRA QUANTOS LIVROS VENDEU A MAIS, A FUNÇÃO JÁ FAZ A SOMA AUTOMATICAMENTE
	public void setQtdLivrosVendidos(int novosLivrosVendidos) {
				
		int novaQtdLivrosVendidos = this.getQtdLivrosVendidos() + novosLivrosVendidos;		
		
		this.qtdLivrosVendidos = novaQtdLivrosVendidos;		
	}
	
	
}
