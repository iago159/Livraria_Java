package br.com.livraria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.livraria.factory.ConnectionFactory;
import br.com.livraria.model.Clientes;

public class ClienteDAO {
	
	/*
	 * Toda regra de neg�cio vir� dentro dessa classe. Nela ter� o CRUD
	 * 
	 * C: Create
	 * R: Read
	 * U: Update
	 * D: Delete
	 */
	
	
	//CREATE (cria um novo registro de cliente)
	public void save(Clientes cliente) {
		
		//C�DIGO NA LINGUAGEM SQL
		String sql = "INSERT INTO clientes(sexo, nome) VALUES (?,?)";
		
		//CRIA UMA VARI�VEL QUE VAI RECEBER CONEX�O
		Connection conn = null;
		
		//CRIA A PREPARA��O PARA RECEBER O C�DIGO SQL
		PreparedStatement pstm = null;
		
		try {
			//Estabelece uma conex�o com o banco de dados
			conn = ConnectionFactory.createConnectionToMySQL();
			
			//Cria uma PreparedStatement para executar uma query (um comando SQL)
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
			//Adiciona os valores que s�o esperados pela query (pelo c�digo SQL)
			pstm.setString(1, cliente.getSexo());
			pstm.setString(2, cliente.getNome());
			
			
			//Executa a query
			pstm.execute();
			
		}catch (Exception e){
			//Caso d� algum erro
			e.printStackTrace();
		}finally {
			//Fechar as conex�es
			try {
				if(pstm!=null) {
					pstm.close();
				}
				
				if(conn!=null) {
					conn.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	//READ (seleciona todos os clientes cadastrados)
	public static List<Clientes> getClientes(){
		
		String sql = "SELECT * FROM clientes ORDER BY nome";

		//CRIA O ARRAY QUE VAI RECEBER TODOS OS CLIENTES E PASSAR COMO RETORNO DA CLASSE
		List<Clientes> clientes = new ArrayList<Clientes>();
		 
		Connection conn = null;
		PreparedStatement pstm = null;
		
		//O RESULTSET VAI PERCORRER OS DADOS DA TABELA SQL
		ResultSet rset = null;
		
		try {
			
			conn = ConnectionFactory.createConnectionToMySQL();
			
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
			
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				
				//recuperando os dados de cada cliente
				Clientes cliente = new Clientes(rset.getString("nome"), rset.getString("sexo"));
				cliente.setIdcliente(rset.getInt("idcliente"));
				cliente.setCondicaoConta(rset.getString("condicao"));
				//Adiciona o cliente no array de clientes
				clientes.add(cliente);
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rset!=null) {
					rset.close();
				}
				if(pstm != null) {
					pstm.close();
				}
				if(conn != null) {
					conn.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return clientes;
		 
	}
	
	//READ 2 (seleciona apenas um cliente atrav�s de seu id)
	public static Clientes getClientes(int idcliente) {
		
		String sql = "SELECT * FROM clientes WHERE idcliente = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		Clientes cliente = new Clientes("Cliente n�o existente", "t");

		try {
			
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			pstm.setInt(1, idcliente); 
			rset = pstm.executeQuery();
						
			while(rset.next()) {
				
				if(rset.getInt("idcliente") == idcliente) {
					cliente.setIdcliente(idcliente);
					cliente.setNome(rset.getString("nome"));
					cliente.setSexo(rset.getString("sexo"));
					cliente.setCondicaoConta(rset.getString("condicao"));
					break;
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstm != null) 
					pstm.close();
				if(conn != null)
					conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return cliente;
		
	}

	//RETORNA O ID DO ULTIMO CLIENTE REGISTRADO
	public static int getIdClientes() {
		
		String sql = "SELECT * FROM clientes ORDER BY idcliente DESC LIMIT 1;";
		
		int idCliente = 0;
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			rset = pstm.executeQuery();

			while(rset.next()) {
				idCliente = rset.getInt("idcliente");
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
		return idCliente;
	}
	
	//UPDATE (muda-se o nome e o sexo do cliente)
	public void update(int idcliente, String novoNome, String novoSexo) {
		String sql = "UPDATE clientes SET NOME = ?, sexo = ? WHERE idcliente = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
			pstm.setString(1, novoNome);
			pstm.setString(2, novoSexo);
			pstm.setInt(3, idcliente);
			
			pstm.execute();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstm!=null) {
					pstm.close();
				}if(conn != null) {
					conn.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	public static int qtdLivrosComprados(int idcliente) {
		String sql = "SELECT COUNT(*) FROM compras WHERE idcliente = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		int qtdComprados = 0;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			pstm.setInt(1, idcliente);
			
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				qtdComprados = rset.getInt("COUNT(*)");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstm != null)
					pstm.close();
				if(conn != null)
					conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return qtdComprados;
		
	}
	
	//DELETE (deleta um registro de cliente)
	public void delete(int idcliente) {
		
		String sql = "DELETE FROM clientes WHERE idcliente = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
			pstm.setInt(1, idcliente);
			
			pstm.execute();
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstm != null) {
					pstm.close();
				}if(conn != null) {
					conn.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	//DELETA TODOS OS LIVROS	
	public void deleteAll() {
		
		String sql = "TRUNCATE clientes";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
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
	
	public void cancelarCliente(int idcliente) {
		
		String sql = "UPDATE clientes SET condicao = 'cancelada' WHERE idcliente = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
			pstm.setInt(1, idcliente);
			
			pstm.execute();
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstm != null) {
					pstm.close();
				}if(conn != null) {
					conn.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}	
	
	public void ativarCliente(int idcliente) {
		
		String sql = "UPDATE clientes SET condicao = 'ativa' WHERE idcliente = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
			pstm.setInt(1, idcliente);
			
			pstm.execute();
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstm != null) {
					pstm.close();
				}if(conn != null) {
					conn.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}	

	
	
}
