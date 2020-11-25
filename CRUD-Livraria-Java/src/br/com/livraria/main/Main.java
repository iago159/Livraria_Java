package br.com.livraria.main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
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
		System.out.println("Parabéns "+ nome + ", você é o nosso mais novo cliente!. Seu número de ID é: "+ ClienteDAO.getIdClientes() + ". Lembre-se dele");
	}
	
	//mostra todos os clientes
	public static void verClientes() {
		
		boolean existeClientes = (ClienteDAO.getClientes().isEmpty() == false) ? true : false;
		
		if(existeClientes) {
			for(Clientes cliente : ClienteDAO.getClientes()) {
				System.out.println("\nId Cliente: " + cliente.getIdcliente() + "\nCondicao da Conta: " + cliente.getCondicaoConta() + "\nCliente: " + cliente.getNome() + "\nSexo: " 
						+ cliente.getSexo() + "\nQuantidade de livros comprados: " + ClienteDAO.qtdLivrosComprados(cliente.getIdcliente()));
			}				
		}else
			System.out.println("Não existem clientes cadastrados até o momento.");
	}	
	
	//mostra as informações do cliente a partir de seu ID
	public static void verClientes(int idcliente) {
		
		boolean clienteExiste = ClienteDAO.getClientes(idcliente).getIdcliente() != 0 ? true : false;
		if(clienteExiste) {			
			String nome = ClienteDAO.getClientes(idcliente).getNome();
			String condicaoConta = ClienteDAO.getClientes(idcliente).getCondicaoConta();
			int qtdLivrosComprados = ClienteDAO.qtdLivrosComprados(idcliente);		
			System.out.println("Id do Cliente: " + idcliente + "\nCondicao da conta: " + condicaoConta + "\nNome: " + nome + "\nQuantidade de livros comprados: "+ qtdLivrosComprados);
		}else
			System.out.println("Cliente não cadastrado.");
	}
	
	//Seleciona um cliente a partir de seu ID
	public static Clientes getCliente(int idcliente) {
		return ClienteDAO.getClientes(idcliente);
	}

	public static void atualizarCliente(int idcliente, String novoNome, String novoSexo) {
		ClienteDAO cliente = new ClienteDAO();
		cliente.update(idcliente, novoNome, novoSexo);
		System.out.println("Cliente atualizado com sucesso!\nNome: " + novoNome + "\nSexo: " + novoSexo);
	}

	public static void deletarCliente(int idcliente) {
		
		try(Scanner scanner = new Scanner(System.in)){
			System.out.println("Tem certeza que deseja deletar o registro deste cliente? Suas compras também serão deletadas."
					+ "\nAperte 1 para continuar.");
			int numeroDigitado = scanner.nextInt();
			
			boolean certeza = numeroDigitado == 1 ? true : false;
			
			if(certeza) {
				ClienteDAO cliente = new ClienteDAO();
				cliente.delete(idcliente);
				CompraDAO.deletarComprasCliente(idcliente);
				System.out.println("Cliente deletado com sucesso!");
			}else
				System.out.println("Nenhum cliente foi deletado.");			
		}
	}

	public static void deletarTodosClientes() {
		
		try (Scanner scanner = new Scanner (System.in)) {
			System.out.println("Tem certeza que deseja apagar todos os registros de Clientes? Isso apagará também todas as compras realizadas."
					+ "\nAperte 1 para continuar!");
			int numeroDigitado = scanner.nextInt();
			boolean certeza = numeroDigitado == 1 ? true : false;
			if(certeza) {
				ClienteDAO cliente = new ClienteDAO();
				CompraDAO.deletarTodasCompras();
				cliente.deleteAll();
				System.out.println("Todos os registros de clientes e compras foram deletados");
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
	
	//Mostra a quantidade de livros que esse cliente comprou
	public static void qtdLivrosComprados(int idcliente) {
		int qtdComprada = ClienteDAO.qtdLivrosComprados(idcliente);
		System.out.println("O cliente com ID '" + idcliente + "' ja comprou " + qtdComprada + " livros.");
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
		
		boolean existeLivros = (LivroDAO.getLivros().isEmpty() == false) ? true : false;
		
		if(existeLivros) {
			for(Livros livro : LivroDAO.getLivros()) {
				int idlivro = livro.getIdlivro();
				int estoque = livro.getEstoque();
				String titulo = livro.getNome();
				double preco = livro.getPreco();
				int vendidos = LivroDAO.qtdVendidos(livro.getIdlivro());
				System.out.println("\nTítulo: " + titulo + "\nId do livro: " + idlivro + "\nPreço: " + precoFmt.format(preco) + "\nEstoque: " + estoque + "\nQuantidade vendidos: " + vendidos);
			}				
		}else
			System.out.println("Não exixtem livros cadastrados a´te o momento.");
		
	}	
	
	public static void verLivros(int idlivro) {
		
		DecimalFormat precoFmt = new DecimalFormat("###.00");
		
		Livros livro = getLivro(idlivro);
		int estoque = livro.getEstoque();
		String titulo = livro.getNome();
		double preco = livro.getPreco();
		int vendidos = LivroDAO.qtdVendidos(livro.getIdlivro());
		
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
			int numeroDigitado = scanner.nextInt();
			boolean certeza = numeroDigitado == 1 ? true : false;
			if(certeza) {
				String nomeDoLivro = LivroDAO.getLivros(idlivro).getNome();
				LivroDAO.deletarLivro(idlivro);
				System.out.println("Livro " + nomeDoLivro + " deletado com sucesso!");
			}else
				System.out.println("Nenhum livro foi deletado.");
		}
	}
	
	public static void deletarTodosLivros() {
		try(Scanner scanner = new Scanner (System.in)){
			System.out.println("Tem certeza que deseja apagar todos os livros? Isso apagará também todos os registros de compras."
					+ "\nAperte 1 para continuar.");
			int numeroDigitado = scanner.nextInt();
			boolean certeza = numeroDigitado == 1 ? true : false;
			if(certeza) {
				LivroDAO.deletarTodosLivros();
				CompraDAO.deletarTodasCompras();
				System.out.println("Todos os livros e compras foram deletadas com sucesso!");
			}else
				System.out.println("Nenhum livro foi deletado.");
		}
	}
	
	//Pesquisa livros a partir de trexos do nome do livro (por exemplo, se pesquisar "jo" aparecerá todos os livros que contem as letras JO nessa sequência)
	public static void pesquisarLivros(String nomeLivro) {
		System.out.println("Resultados de busca para '" + nomeLivro + "':\n" );
		List<Livros> livros = new ArrayList<Livros>();
		for(Livros livro : LivroDAO.pesquisarLivros(nomeLivro)) {
			livros.add(livro);
		}
		
		boolean arrayNaoVazio = !(livros.isEmpty());
		
		
		if(arrayNaoVazio) {
			for(Livros livro : livros) {
			System.out.println("Nome: " + livro.getNome() + "\nCodigo: " + livro.getIdlivro() + "\n");
			}
		}else
			System.out.println("Nenhum livro encontrado com essa busca.");		
	}
	
	public static void qtdLivrosVendidos(int idlivro) {
		int qtdVendida = LivroDAO.qtdVendidos(idlivro);
		
		System.out.println("O livro com id '" + idlivro + "' vendeu " + qtdVendida + " unidades.");
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
			boolean certeza = scanner.nextInt() == 1 ? true : false;
			if(certeza) {
				CompraDAO compra = new CompraDAO();
				compra.cancelarCompra(idcompra);
				System.out.println("Compra cancelada com sucesso!");
			}else
				System.out.println("Nenhuma compra foi cancelada.");
		}
	}
	
	public static Compras getCompra(int idcompra) {
		Compras compra = CompraDAO.getCompras(idcompra);
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
	  
	public static void verCompras(int diaInicial, int mesInicial, int anoInicial, int diaFinal, int mesFinal, int anoFinal) {
		
				
		Calendar dataIni = new GregorianCalendar(anoInicial, mesInicial, diaInicial);
		Calendar dataFin = new GregorianCalendar(anoFinal, mesFinal, diaFinal);
		java.sql.Date dataInicial = new java.sql.Date(dataIni.getTimeInMillis());
		java.sql.Date dataFinal = new java.sql.Date(dataFin.getTimeInMillis());

		
		boolean existeCompras = (CompraDAO.getCompras(dataInicial, dataFinal).isEmpty() == false) ? true : false;
		
		if(existeCompras) {
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
		}else
			System.out.println("Não existem compras realizadas entre essas datas.");
		
	}
	
	public static void verComprasCliente(int idcliente) {
		
		String nomeCliente = getCliente(idcliente).getNome();
		
		System.out.println("\nCompras do cliente " + nomeCliente + ", possuidor do ID '" + idcliente + "':");
		
		for(Compras compra : CompraDAO.verComprasCliente(idcliente)) {
			
			Livros livro = getLivro(compra.getIdlivro());
			
			String nomeLivro = livro.getNome();
			String condicaoCompra = compra.getCondicaoCompra();
			java.util.Date dataCompra = compra.getData();
			
			System.out.println("\nLivro Comprado: " + nomeLivro + "\nCondicao da compra: " + condicaoCompra + "\nData da compra: " + dataCompra );
			
			
		}
	}
	
	public static void verComprasLivro(int idlivro) {
		
		String nomeLivro = getLivro(idlivro).getNome();
		
		System.out.println("\nCompras do livro " + nomeLivro + ", possuidor do ID '" + idlivro + "':");
		
		for(Compras compra : CompraDAO.verComprasLivro(idlivro)) {
			
			Clientes cliente = getCliente(compra.getIdcliente());
			
			String nomeCliente = cliente.getNome();
			int idCliente = cliente.getIdcliente();
			String condicaoCompra = compra.getCondicaoCompra();
			java.util.Date dataCompra = compra.getData();
			
			System.out.println("\nCliente: " + nomeCliente + "\nID do cliente: "+ idCliente + "\nCondicao da compra: " + condicaoCompra + "\nData da compra: " + dataCompra );
			
			
		}
	}

	
	//---------------------------------------------------------------------------------------------//
	
	public static void main(String[] args) {

//		cadastrarClientes("João Batista", "M");
//		verClientes();
//		verClientes(2);
//		getCliente(1);
//		atualizarCliente(1, "Iago Alves", "M");
		deletarCliente(1);
//		deletarTodosClientes();
//		cancelarClientes(2);
//		ativarCliente(2);
//		qtdLivrosComprados(1);
//		
//		
//		cadastrarLivros("O misterioro caso da mansão de João", "Fantasia", 40, "Durante as férias, a mansão de João, aparentemente abandonada, vive uma situação inusitada.", 99.90);
//		verLivros();
//		verLivros(1);
//		System.out.println(getLivro(2).getNome());
//		atualizarLivro(3, "clean Code", "novo estilo", "nova sinopse", 1);
//		adicionarEstoque(4,20);
//		deletarLivro(4);
//		deletarTodosLivros();
//		pesquisarLivros("a");
//		qtdLivrosVendidos(1);		

//		
//		comprarLivros(1,1,99.90);
//		cancelarCompra(1);
//		System.out.println(getCompra(1).getIdcliente());
//		verCompras(1);
//		verComprasCliente(1);
//		verCompras(1,1,1,1,1,3000);
//		verComprasCliente(1);
//		verComprasLivro(1);
		
	}

}
