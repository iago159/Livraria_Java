package br.com.livraria.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.livraria.factory.ConnectionFactory;
import br.com.livraria.model.Compras;
 
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
			livrodao.updateEstoque(compra.getIdlivro(), -1);	
			
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
			
			LivroDAO livrodao = new LivroDAO();
			livrodao.updateEstoque(idLivro, +1);			
			
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
	public static List<Compras> verComprasCliente(int idcliente){
		String sql = "SELECT * FROM compras comp JOIN clientes c JOIN livros l ON comp.idcliente = ?";
		
		List<Compras> compras = new ArrayList<Compras>();

		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			pstm.setInt(1, idcliente);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				
					Compras compra = new Compras(rset.getDate("data"), rset.getInt("idcliente"), rset.getInt("idlivro"), rset.getDouble("valor"));
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

	
	public static List<Compras> verComprasLivro(int idlivro){
		String sql = "SELECT * FROM compras comp JOIN clientes c JOIN livros l ON c.livrosComprados = l.idlivro and comp.idlivro = ?";
		
		List<Compras> compras = new ArrayList<Compras>();

		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			pstm.setInt(1, idlivro);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				
					Compras compra = new Compras(rset.getDate("data"), rset.getInt("idcliente"), rset.getInt("idlivro"), rset.getDouble("valor"));
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
	
	public static Compras getCompras(int idcompra) {
		
		String sql = "SELECT * FROM compras WHERE idcompra = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
				
		Compras compra = new Compras(new Date (0), 0, 0, 0);

		try {
			
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			pstm.setInt(1, idcompra); 
			rset = pstm.executeQuery();
						
			while(rset.next()) {
				
				if(rset.getInt("idcompra") == idcompra) {
					compra.setCondicaoConta(rset.getString("condicao"));
					compra.setData(rset.getDate("data"));
					compra.setIdcliente(rset.getInt("idcliente"));
					compra.setIdcompra(rset.getInt("idcompra"));
					compra.setIdlivro(rset.getInt("idlivro"));
					compra.setValor(rset.getDouble("valor"));
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
		
		return compra;
		
	}
	
	public static void deletarComprasCliente(int idcliente) {
		
		String sql = "DELETE FROM COMPRAS WHERE idcliente = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.createConnectionToMySQL();
			pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, idcliente);
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
	
	public static void deletarTodasCompras() {

		String sql = "TRUNCATE compras";
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

}


