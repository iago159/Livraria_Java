package br.com.livraria.main;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import br.com.livraria.dao.ClienteDAO;
import br.com.livraria.dao.CompraDAO;
import br.com.livraria.dao.LivroDAO;
import br.com.livraria.model.Clientes;
import br.com.livraria.model.Compras;
import br.com.livraria.model.Livros;

public class Main {
	
	//--------------------------------------------- CLIENTES ---------------------------------------------//	
	
	public static void cadastrarClientes(String nome, String sexo) {
		Clientes novoCliente = new Clientes(nome, sexo);
		ClienteDAO cliente = new ClienteDAO();
		cliente.save(novoCliente);
		System.out.println("Parabéns "+ nome + ". Você é o nosso mais novo cliente!.");
	}
	
	//mostra todos os clientes
	public static void verClientes() {
		
		for(Clientes cliente : ClienteDAO.getClientes()) {
			System.out.println("\nId Cliente: " + cliente.getIdcliente() + "\nCondicao da Conta: " + cliente.getCondicaoConta() + "\nCliente: " + cliente.getNome() + "\nSexo: " 
						+ cliente.getSexo() + "\nQuantidade de livros comprados: " + cliente.getQtdLivrosComprados());
		}	
	}	
	
	public static void verClientes(int idcliente) {
		int idCliente = ClienteDAO.getClientes(idcliente).getIdcliente();
		if(idCliente != 0) {			
			String nome = ClienteDAO.getClientes(idcliente).getNome();
			String condicaoConta = ClienteDAO.getClientes(idcliente).getCondicaoConta();
			int qtdLivrosComprados = ClienteDAO.getClientes(idcliente).getQtdLivrosComprados();		
			System.out.println("Id do Cliente: " + idCliente + "\nCondicao da conta: " + condicaoConta + "\nNome: " + nome + "\nQuantidade de livros comprados: "+ qtdLivrosComprados);
		}else
			System.out.println("Cliente não cadastrado.");
	}
	
	//Seleciona o cliente passando um id como parâmetro
	public static Clientes getCliente(int idcliente) {
		return ClienteDAO.getClientes(idcliente);
	}
	
	//Atualiza o cliente a partir de seu id
	public static void atualizarCliente(int idcliente, String novoNome, String novoSexo) {
		ClienteDAO cliente = new ClienteDAO();
		cliente.update(idcliente, novoNome, novoSexo);
		System.out.println("Cliente atualizado com sucesso!\nNome: " + novoNome + "\nSexo: " + novoSexo);
	}

	//Deleta um cliente específico a partir de seu id (RECOMENDA-SE CANCELAR O CLIENTE)
	public static void deletarCliente(int idcliente) {
		
		try(Scanner scanner = new Scanner(System.in)){
			System.out.println("Tem certeza que deseja deletar o registro deste cliente? Aperta 1 para continuar.");
			int certeza = scanner.nextInt();
			
			if(certeza == 1) {
				ClienteDAO cliente = new ClienteDAO();
				cliente.delete(idcliente);
				System.out.println("Cliente deletado com sucesso!");
			}else
				System.out.println("Nenhum cliente foi deletado.");			
		}
	}

	// Deleta todos os clientes
	public static void deletarTodosClientes() {
		
		try (Scanner scanner = new Scanner (System.in)) {
			System.out.println("Tem certeza que deseja apagar todos os registros da tabela Clientes? Aperta 1 para continuar!");
			int certeza = scanner.nextInt();
			if(certeza == 1) {
				ClienteDAO cliente = new ClienteDAO();
				cliente.deleteAll();
				System.out.println("Todos os registros de clientes foram deletados");
			}else
				System.out.println("Nenhum cliente foi deletado");
		}
	}
	
	public static void cancelarClientes(int idcliente) {
		ClienteDAO clienteDAO = new ClienteDAO();
		clienteDAO.cancelarCliente(idcliente);
		System.out.println("Cliente cancelado com sucesso. Situação da conta: " + ClienteDAO.getClientes(idcliente).getCondicaoConta());

		
	}
	
	public static void ativarCliente(int idcliente) {
		ClienteDAO clienteDAO = new ClienteDAO();
		clienteDAO.ativarCliente(idcliente);
		System.out.println("Cliente ativado com sucesso. Situação da conta: " + ClienteDAO.getClientes(idcliente).getCondicaoConta());

		
	}

	
	//--------------------------------------------- LIVROS ---------------------------------------------//
	
	public static void cadastrarLivros(String nome, String estilo, int estoque, String sinopse, double preco) {
		Livros novoLivro = new Livros(nome, estilo, estoque, sinopse, preco);
		try{
			LivroDAO.save(novoLivro);
			System.out.println("Livro cadastrado com sucesso!");
		}catch(Exception e) {
			System.out.println("Não foi possível salvar o livro");
		}
	}
	
	public static void verLivros() {
		
		DecimalFormat precoFmt = new DecimalFormat("###.00");
		
		for(Livros livro : LivroDAO.getLivros()) {
			int idlivro = livro.getIdlivro();
			int estoque = livro.getEstoque();
			String titulo = livro.getNome();
			double preco = livro.getPreco();
			int vendidos = livro.getQtdLivrosVendidos();
			System.out.println("\nTítulo: " + titulo + "\nId do livro: " + idlivro + "\nPreço: " + precoFmt.format(preco) + "\nEstoque: " + estoque + "\nQuantidade vendidos: " + vendidos);
		}	
	}	
	
	public static void verLivros(int idlivro) {
		
		DecimalFormat precoFmt = new DecimalFormat("###.00");
		
		Livros livro = getLivro(idlivro);
		int estoque = livro.getEstoque();
		String titulo = livro.getNome();
		double preco = livro.getPreco();
		int vendidos = livro.getQtdLivrosVendidos();
		
		System.out.println("\nTítulo: " + titulo + "\nId do livro: " + livro.getIdlivro() + "\nPreço: " + precoFmt.format(preco) + "\nEstoque: " + estoque + "\nQuantidade vendidos: " + vendidos);
	}
	
	public static Livros getLivro(int idlivro) {
		return LivroDAO.getLivros(idlivro);
	}
	
	public static void atualizarLivro(int idlivro, String nome, String estilo, String sinopse, double preco) {
		LivroDAO.update(idlivro, nome, estilo, sinopse, preco);
		System.out.println("Livro atualizado com sucesso!");
	}
	
	public static void adicionarEstoque(int idlivro, int quantidadeAdquirida) {
		LivroDAO livrodao = new LivroDAO();
		livrodao.updateEstoque(idlivro, quantidadeAdquirida);
		int novoEstoque = LivroDAO.getLivros(idlivro).getEstoque();
		System.out.println("Estoque adicionado com sucesso. Novo estoque: " + novoEstoque);
	}
	
	public static void deletarLivro(int idlivro) {
		try (Scanner scanner = new Scanner (System.in)) {
			System.out.println("Tem certeza que deseja apagar este livro? Aperta 1 para continuar.");
			int certeza = scanner.nextInt();
			if(certeza == 1) {
				String nomeDoLivro = LivroDAO.getLivros(idlivro).getNome();
				LivroDAO.deletarLivro(idlivro);
				System.out.println("Livro " + nomeDoLivro + " deletado com sucesso!");
			}else
				System.out.println("Nenhum livro foi deletado.");
		}
	}
	
	public static void deletarTodosLivros() {
		try(Scanner scanner = new Scanner (System.in)){
			System.out.println("Tem certeza que deseja apagar todos os livros? Aperta 1 para continuar.");
			int certeza = scanner.nextInt();
			if(certeza == 1) {
				LivroDAO.deletarTodosLivros();
				System.out.println("Todos os livros deletados com sucesso!");
			}else
				System.out.println("Nenhum livro foi deletado.");
		}
	}
	
	
	//--------------------------------------------- COMPRAS ---------------------------------------------//
	
	public static void comprarLivros(int idcliente, int idlivro, double valor) {
		Calendar hoje = Calendar.getInstance();
		Compras novaCompra = new Compras(hoje.getTime(), idcliente, idlivro, valor);
		CompraDAO compra = new CompraDAO();
		compra.save(novaCompra);	
		System.out.println("Compra efetuada com sucesso.");
	}
	
	public static void cancelarCompra(int idcompra) {

		try(Scanner scanner = new Scanner(System.in)){
			System.out.println("Tem certeza que deseja cancelada esta compra? Não será possível reativá-la. Caso sim, digite 1");
			int certeza = scanner.nextInt();
			if(certeza==1) {
				CompraDAO compra = new CompraDAO();
				compra.cancelarCompra(idcompra);
				System.out.println("Compra cancelada com sucesso!");
			}else
				System.out.println("Nenhuma compra foi cancelada.");
		}
	}
	
	public static Compras getCompra(int idcompra) {
		CompraDAO compras = new CompraDAO();
		Compras compra = compras.getCompras(idcompra);
		return compra;
	}

	public static void verCompras(int idcompra) {
		String clienteNome = getCliente(getCompra(idcompra).getIdcliente()).getNome();
		String livroNome = getLivro(getCompra(idcompra).getIdlivro()).getNome();
		String condicaoCompra = getCompra(idcompra).getCondicaoCompra();
		int idCliente = getCompra(idcompra).getIdcliente();
		java.util.Date dataCompra = getCompra(idcompra).getData();
		
		System.out.println("\nCliente: " + clienteNome + "\nId do cliente: " + idCliente + "\nLivro comprado: " + livroNome + "\nCondição da compra: " + condicaoCompra + "\nData da compra: " + dataCompra);
		
	}
	
	public static void verComprasCliente(int idcliente) {
		
		
		for(Compras compra : CompraDAO.getComprasCliente(idcliente)) {
			
			Livros livroComprado = LivroDAO.getLivros(compra.getIdlivro());
			Clientes cliente = ClienteDAO.getClientes(compra.getIdcliente());
			String nomeCliente = cliente.getNome();
			String nomeLivro = livroComprado.getNome();
			int idCliente = cliente.getIdcliente();
			String condicaoCompra = compra.getCondicaoCompra();
			java.util.Date dataCompra = compra.getData();
			
			System.out.println("\nCliente: " + nomeCliente + "\nId do Cliente: " + idCliente + "\nLivro comprado: " + nomeLivro + "\nCondição da compra: " + condicaoCompra + "\nData da compra: " + dataCompra);
		}

	}
  
	public static void verCompras(int diaInicial, int mesInicial, int anoInicial, int diaFinal, int mesFinal, int anoFinal) {
		
		Calendar dataIni = new GregorianCalendar(anoInicial, mesInicial, diaInicial);
		Calendar dataFin = new GregorianCalendar(anoFinal, mesFinal, diaFinal);
		java.sql.Date dataInicial = new java.sql.Date(dataIni.getTimeInMillis());
		java.sql.Date dataFinal = new java.sql.Date(dataFin.getTimeInMillis());

		
		for(Compras compra : CompraDAO.getCompras(dataInicial, dataFinal)) {			
			
			
			Livros livroComprado = LivroDAO.getLivros(compra.getIdlivro());
			Clientes clienteQueComprou = ClienteDAO.getClientes(compra.getIdcliente());
			String nomeCliente = clienteQueComprou.getNome();
			String nomeLivro = livroComprado.getNome();
			int idCliente = clienteQueComprou.getIdcliente();
			String condicaoCompra = compra.getCondicaoCompra();
			java.util.Date dataQueComprou = compra.getData();
			
			
			
			System.out.println("\nCliente: " + nomeCliente + "\nId do cliente: " + idCliente +  "\nLivro comprado: " + nomeLivro + "\nCondição da compra: " + condicaoCompra + "\nData da compra: " + dataQueComprou);
			
		}
		
	}
	
	//---------------------------------------------------------------------------------------------//
	
	public static void main(String[] args) {

//		cadastrarClientes("Marlon Ferreira Cruz", "F");
//		verClientes();
//		verClientes(2);
//		getCliente(1);
//		atualizarCliente(4, "Marlon Ferreira Cruz", "M");
//		deletarCliente(1);
//		deletarTodosClientes();
//		cancelarClientes(2);
//		ativarCliente(2);
	
		
//		cadastrarLivros("Clean Code", "programação", 10, "Clean Code, 'Código limpo' numa tradução literal, é um clássico que ensina boas práticas de programação.", 150.00);
//		verLivros()
//		verLivros(1);
//		System.out.println(getLivro(2).getNome());
//		atualizarLivro(3, "clean Code", "non", "nonnn", 1);
//		adicionarEstoque(4,20);
//		deletarLivro(4);
//		deletarTodosLivros();
		
		
//		comprarLivros(1,1,50);
//		cancelarCompra(1);
//		System.out.println(getCompra().getIdcliente());
//		verCompras(1);
//		verComprasCliente(2);
//		verCompras(1,1,1,1,1,3000);
		
	}

}
