package br.com.livraria.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.livraria.factory.ConnectionFactory;
import br.com.livraria.model.*;
 
public class CompraDAO {
	
	public void save(Compras compra) {
		
		String sql = "INSERT INTO compras (data, idlivro, idcliente, valor) VALUES (?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql); 
			
			
			pstm.setDate(1, new Date(compra.getData().getTime()));
			pstm.setInt(2, compra.getIdlivro());
			pstm.setInt(3, compra.getIdcliente());
			pstm.setDouble(4, compra.getValor());
			
			
			LivroDAO livrodao = new LivroDAO();
			
			livrodao.updateQuantidadeVendidos(compra.getIdlivro(), 1);

			
			livrodao.updateEstoque(compra.getIdlivro(), -1);
			
			ClienteDAO clientedao = new ClienteDAO();
			
			clientedao.updateQtdLivrosComprados(compra.getIdcliente(), 1);

			
			
			pstm.execute();

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstm != null) {
					pstm.close();
				}
				if(conn != null)
					conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void cancelarCompra(int idcompra) {
		
		String sql = "UPDATE compras SET condicao = 'cancelada' WHERE idcompra = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
			int idLivro = getCompras(idcompra).getIdlivro();
			int idCliente = getCompras(idcompra).getIdcliente();
			
			ClienteDAO clientedao = new ClienteDAO();
			clientedao.updateQtdLivrosComprados(idCliente, -1);
			
			LivroDAO livrodao = new LivroDAO();
			livrodao.updateEstoque(idLivro, +1);
			livrodao.updateQuantidadeVendidos(idLivro, -1);
			
			
			pstm.setInt(1, idcompra);
			pstm.execute();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (conn != null)
					conn.close();
				if (pstm != null)
					pstm.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
//	SELECIONA COMPRA EM UMA DATA ESPECÍFICA
	public Compras getCompras(int idcompra) {
		
		String sql = "SELECT * FROM compras WHERE idcompra = ? ORDER BY data";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		//apenas criando a compra, todos os valores serão substituidos.
		Compras compra = new Compras(new Date(0), 1, 1, 1);
		
		try {
			
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
			pstm.setInt(1, idcompra);
			
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				
				if(rset.getInt("idcompra") == idcompra) {
					compra.setData(rset.getDate("data"));
					compra.setIdlivro(rset.getInt("idlivro"));
					compra.setIdcliente(rset.getInt("idcliente"));
					compra.setCondicaoConta(rset.getString("condicao"));
					compra.setValor(rset.getDouble("valor"));
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
		
		return compra;
		
	}

//	SELECIONA COMPRAS ENTRE DUAS DATAS
	public static List<Compras> getCompras(Date dataInicial, Date dataFinal) {
		
		String sql = "SELECT * FROM compras WHERE data BETWEEN ? AND ? ORDER BY data";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		ResultSet rset = null;
		
		List <Compras> compras = new ArrayList<Compras>();
		
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
			pstm.setDate(1, dataInicial);
			pstm.setDate(2, dataFinal);
			
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				Compras compra = new Compras(rset.getDate("data"), rset.getInt("idcliente"), rset.getInt("idlivro"), rset.getDouble("valor"));
				compra.setIdcompra(rset.getInt("idcompra"));
				compra.setCondicaoConta(rset.getString("condicao"));
				compras.add(compra);
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
		
		return compras;
	}
	
	public static List<Compras> getComprasCliente(int idcliente) {
		
		String sql = "SELECT * FROM compras WHERE idcliente = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		List <Compras> compras = new ArrayList<Compras>();
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			
			pstm.setInt(1, idcliente);
			
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				if(rset.getInt("idcliente") == idcliente) {
					Compras compra = new Compras(rset.getDate("data"), rset.getInt("idcliente"), rset.getInt("idlivro"), rset.getDouble("valor"));
					compra.setIdcompra(rset.getInt("idcompra"));
					compra.setCondicaoConta(rset.getString("condicao"));
					compras.add(compra);
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}if(pstm != null) {
					pstm.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return compras;
	}
	
}


