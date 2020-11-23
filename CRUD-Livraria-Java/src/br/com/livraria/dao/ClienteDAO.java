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
	 * Toda regra de negócio virá dentro dessa classe. Nela terá o CRUD
	 * 
	 * C: Create
	 * R: Read
	 * U: Update
	 * D: Delete
	 */
	
	
	//CREATE (cria um novo registro de cliente)
	public void save(Clientes cliente) {
		
		//CÓDIGO NA LINGUAGEM SQL
		String sql = "INSERT INTO clientes(sexo, nome) VALUES (?,?)";
		
		
		//CRIA UMA VARIÁVEL QUE VAI RECEBER CONEXÃO
		Connection conn = null;
		
		//CRIA A PREPARAÇÃO PARA RECEBER O CÓDIGO SQL
		PreparedStatement pstm = null;
		
		try {
			//Estabelece uma conexão com o banco de dados
			conn = ConnectionFactory.createConnectionToMySQL();
			
			//Cria uma PreparedStatement para executar uma query (um comando SQL)
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
			//Adiciona os valores que são esperados pela query (pelo código SQL)
			pstm.setString(1, cliente.getSexo());
			pstm.setString(2, cliente.getNome());
			
			
			//Executa a query
			pstm.execute();
			
		}catch (Exception e){
			//Caso dê algum erro
			e.printStackTrace();
		}finally {
			//Fechar as conexões
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
				cliente.setLivrosComprados(rset.getInt("livrosComprados"));
				cliente.setQtdLivrosComprados(rset.getInt("qtdlivroscomprados"));
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
	
	//READ 2 (seleciona apenas um cliente através de seu id)
	public static Clientes getClientes(int idcliente) {
		
		String sql = "SELECT * FROM clientes WHERE idcliente = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		Clientes cliente = new Clientes("Cliente não existente", "t");

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
					cliente.setLivrosComprados(rset.getInt("livrosComprados"));
					cliente.setQtdLivrosComprados(rset.getInt("qtdlivroscomprados")); 
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

	//UPDATE 2 (apenas adciona-se livros ao cliente quando a classe comprar é ativada)
	public void updateQtdLivrosComprados(int idcliente, int qtdNovosLivrosComprados) {
		
		String sql = "UPDATE clientes SET qtdlivroscomprados = ? WHERE idcliente = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
	
//			//CRIA UM NOVO CLIENTE QUE VAI RECEBER A NOVA QUANTIDADE DE LIVROS COMPRADAS
			Clientes cliente = new Clientes("Vai ser substituido", "Vai ser substituido");
			
			//O GETCLIENTES RECEBE UM ID E SE TRANSFORMA NO CLIENTE COM ESSE ID, E O CLIENTE CRIADO RECEBE SE TRANSFORMARÁ NO GETCLIENTES
			cliente = getClientes(idcliente);			
			
			//PASSA A NOVA QUANTIDADE DE LIVROS COMPRADOS PARA O CLIENTE
			cliente.setQtdLivrosComprados(qtdNovosLivrosComprados);
			
			//PASSA PARA A QUANTIDADE DE LIVROS COMPRADOS PARA CÓDIGO SQL
			pstm.setInt(1, cliente.getQtdLivrosComprados());
			
			//PASSA O ID DO CLIENTE PARA O CÓDICO SQL
			pstm.setInt(2, idcliente);			
			
			pstm.execute();						
			
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
