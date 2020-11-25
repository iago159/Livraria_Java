package br.com.livraria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import br.com.livraria.factory.ConnectionFactory;
import br.com.livraria.model.Livros;

public class LivroDAO {
	
	// Cria um novo registro de livro
	public static void save (Livros livro) {
		
		String sql = "INSERT INTO livros (nome, estilo, estoque, sinopse, preco) VALUES (?, ?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
			pstm.setString(1, livro.getNome());
			pstm.setString(2, livro.getEstilo());
			pstm.setInt(3, livro.getEstoque());
			pstm.setString(4, livro.getSinopse());
			pstm.setDouble(5, livro.getPreco());

			
			pstm.execute();	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				
				if(pstm!=null) 
					pstm.close();			
				if(conn!=null) {
					conn.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	
	
	
	
}

	//Seleciona todos os livros do banco de dados
	public static List<Livros> getLivros(){
		
		String sql = "SELECT * FROM livros ORDER BY nome";
		
		List<Livros> livros = new ArrayList<Livros>();
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
			rset = pstm.executeQuery();
			
			while(rset.next()) {

				Livros livro = new Livros(rset.getString("nome"), rset.getString("estilo"),	rset.getInt("estoque"), rset.getString("sinopse"), rset.getDouble("preco"));
				livro.setIdlivro(rset.getInt("idlivro"));
				livros.add(livro);
				}
		}catch(Exception e) {
			e.printStackTrace();					
		}finally {
			try {
				if(conn != null)
					conn.close();
				if(pstm != null)
					pstm.close();
			}catch(Exception e) {				
			e.printStackTrace();
			}
		}
		
		return livros;
		
	}

	// Selecionar um livro espeífico a partir de seu id
	public static Livros getLivros(int idlivro) {
		
		String sql = "SELECT * FROM livros WHERE idlivro = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		Livros livro = new Livros("Todos os valores aqui vao receber o valor do livro selecionado", "a", 0,"a",0);
		
		try {
			
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			pstm.setInt(1, idlivro);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				
				if(rset.getInt("idlivro") == idlivro) {
					livro.setIdlivro(idlivro);
					livro.setNome(rset.getString("nome"));
					livro.setEstilo(rset.getString("estilo"));
					livro.setEstoque(rset.getInt("estoque"));
					livro.setSinopse(rset.getString("sinopse"));
					livro.setPreco(rset.getDouble("preco"));
					break;
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)
					conn.close();
				if(pstm != null)
					pstm.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		return livro;
		
	}

	// Altera um livro a partir de seu id
	public static void update (int idlivro, String nome, String estilo, String sinopse, double preco) {
	
		String sql = "UPDATE livros SET NOME = ?, estilo = ?, sinopse = ?, preco = ? WHERE idlivro= ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
			pstm.setString(1, nome);
			pstm.setString(2, estilo);
			pstm.setString(3, sinopse);
			pstm.setDouble(4, preco);
			pstm.setInt(5, idlivro);
			
			pstm.execute();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)
					conn.close();
				if(pstm != null)
					pstm.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	// Adicionar Estoque
	public void updateEstoque(int idlivro, int quantidadeAdquirida) {
		
		String sql = "UPDATE livros SET estoque = ? WHERE idlivro = ?";
		
		Connection conn= null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
			//FALTA FAZER O SELECT PARA SELECIONAR UM LIVRO EM ESPECÍFICO E USAR DAQUI PARA FRENTE
			Livros livro = new Livros("Todos os valores aqui vao receber o valor do livro selecionado", "", 0,"",0);
			
			livro = getLivros(idlivro);
			
			livro.setEstoque(quantidadeAdquirida);
			
			pstm.setInt(1, livro.getEstoque());
			pstm.setInt(2, idlivro);
			
			pstm.execute();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)
					conn.close();
				if(pstm != null)
					pstm.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	//Deletar um livro
	public static void deletarLivro(int idlivro) {
		
		String sql = "DELETE FROM livros WHERE idlivro = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
			pstm.setInt(1, idlivro);
			
			pstm.execute();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null)
					conn.close();
				if(pstm != null)
					pstm.close();
			}catch(Exception e) {				
				e.printStackTrace();
			}
		}
	}
	
	//Deleta todos os livros
	public static void deletarTodosLivros() {
		
		String sql = "TRUNCATE livros";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
			pstm.execute();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
		
			try {
				if(conn != null)
					conn.close();
				if(pstm != null)
					pstm.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static List<Livros> pesquisarLivros(String nomeLivro){
		
		String sql = "SELECT * FROM livros WHERE nome LIKE ?";
		List<Livros> livros = new ArrayList<Livros>();
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			pstm.setString(1, "%" +nomeLivro + "%");
			
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				Livros livro = new Livros(rset.getString("nome"), rset.getString("estilo"),	rset.getInt("estoque"), rset.getString("sinopse"), rset.getDouble("preco"));
				livro.setIdlivro(rset.getInt("idlivro"));
				livros.add(livro);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
		
			try {
				if(conn != null)
					conn.close();
				if(pstm != null)
					pstm.close();
			}catch(Exception e) {
				e.printStackTrace();
			}			
		}
		
		
		return livros;
	}

	public static int qtdVendidos(int idlivro) {
		
		String sql = "SELECT COUNT(*) FROM compras WHERE idlivro = ?";
		int qtdVendidos = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			pstm.setInt(1, idlivro);
			
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				qtdVendidos = rset.getInt("COUNT(*)");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn!=null) {
					conn.close();
				}
				if(pstm != null) {
					pstm.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return qtdVendidos;
		
	}
	
}
