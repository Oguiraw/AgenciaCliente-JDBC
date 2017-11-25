package br.com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.models.Agencia;
import br.com.models.Cliente;

public class AgenciaDAO extends DataBaseDAO{

	public AgenciaDAO() throws Exception {
		super();
	}
	
	public Agencia recuperarAgenciaComClientes(int id_agencia) throws SQLException {
		Agencia agencia = null;
		if (id_agencia <= 0) {
			throw new SQLException("Código menor ou igual a zero!");
		}
		String SELECT_SQL_CLIENTES = "SELECT id, nome, email, cpf, nascimento, endereco, agencia FROM CLIENTES WHERE AGENCIA = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// Obter o prepared statment para execultar a consulta SELECT_SQL
			pstmt = connection.prepareStatement(SELECT_SQL_CLIENTES);
			// setar codigo como paramentro da clausura where
			pstmt.setInt(1, id_agencia);
			// executar consulta para obter um SetResult
			rs = pstmt.executeQuery();
			List<Cliente> clientes = new ArrayList<>();
			while (rs.next()) {
				clientes.add(new Cliente(rs.getInt("id"), rs.getString("NOME"), rs.getString("EMAIL"),
						rs.getString("CPF"), rs.getDate("NASCIMENTO"), rs.getString("ENDERECO"), rs.getInt("agencia")));
			}
			agencia = (recuperarAgencia(id_agencia));
			agencia.setClientes(clientes);
		} finally {

		}
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return agencia;
	}
	
	public void createAgencia(Agencia agencia) throws SQLException{
		if(agencia == null){
			throw new SQLException("Agencia nula!");
		}
		
		String INSERT_SQL = "INSERT INTO AGENCIAS VALUES(?,?,?)";
		PreparedStatement pstmt = null;
		try{
			pstmt = connection.prepareStatement(INSERT_SQL);
			pstmt.setInt(1, agencia.getNumero());
			pstmt.setString(2, agencia.getEndereco());
			pstmt.setString(3, agencia.getNome_gerente());
			
			int rows = pstmt.executeUpdate();
			if(rows > 0){
				super.commit();
				System.out.println("Agencia criada com sucesso!!");
			}
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Não foi possível criar essa agencia!");
			try{
				super.rollback();
			}catch (SQLException e2) {
				e2.printStackTrace();
			}
		}finally {
			try{
				if(pstmt != null){
					pstmt.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void deleteAgencia(int numero) throws Exception{
		if(numero <= 0){
			throw new SQLException("O numero do cliente não pode ser menor que 0");
		}
		String DELETE_SQL = "DELETE FROM AGENCIAS WHERE NUMERO = ?";
		PreparedStatement pstmt = null;
		try{
			pstmt = connection.prepareStatement(DELETE_SQL);
			pstmt.setInt(1, numero);
			System.out.println("Excluindo agencia!");
			int rows = pstmt.executeUpdate();
			if(rows >0){
				super.commit();
				System.out.println(numero + " Excluído!");
			}
		}catch (SQLException e) {
			try{
				System.out.println("Agencia não excluido!");
				super.rollback();
			}catch (SQLException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
			throw new Exception("Impossível excluir! Causa: " + e);
		}catch (RuntimeException e) {
			try{
				System.out.println(numero+ " Não excluido!");
				connection.rollback();
			}catch (SQLException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
			throw new Exception("Impossível excluir! Causa: " + e );
		}finally {
			try{
				if( pstmt != null){
					pstmt.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private Agencia recuperarAgencia(int numero) throws SQLException{
		Agencia agencia = null;
		if(numero <= 0){
			throw new SQLException("numero menor ou igual a zero!");
		}
		String SELECT_SQL = "select numero, endereco, nome_gerente FROM AGENCIAS WHERE NUMERO = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			//Obter o prepared statment para execultar a consulta SELECT_SQL
			pstmt = connection.prepareStatement(SELECT_SQL);
			//setar codigo como paramentro da clausura where
			pstmt.setInt(1, numero);
			//executar consulta para obter um SetResult
			rs = pstmt.executeQuery();
			if(rs.next()){
				agencia = new Agencia(rs.getInt("numero"),rs.getString("endereco"), rs.getString("nome_gerente"));
			}else{
				//Caso não encontre a Agencia gera um erro
				throw new SQLException("Nenhum agencia encontrado com o numero: !" + numero);
			}
		}finally {
		}
			try{
				if(pstmt != null){
					pstmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		return agencia;
	}
	
	public List<Agencia> getAllAgencias() throws SQLException{
		List<Integer> numerosAgencia = new ArrayList<>();
		List<Agencia> agencias = new ArrayList<>(); 
		String SELECT_SQL = "select numero FROM AGENCIAS ";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = connection.prepareStatement(SELECT_SQL);
			rs = pstmt.executeQuery();
			while(rs.next()){
				numerosAgencia.add(rs.getInt("numero"));
			}
		}finally {
			try{
				if(pstmt != null){
					pstmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		for(int i : numerosAgencia){
			agencias.add(recuperarAgenciaComClientes(i));
		}
		return agencias;
	}
	
	
	
	public void updateAgencia(Agencia agencia) throws SQLException{
		if(agencia == null){
			throw new SQLException("Cliente não pode ser nulo!");
		}
		String UPDATE_SQL = "UPDATE AGENCIAS SET ENDERECO=?, NOME_GERENTE=? WHERE NUMERO=?";
		PreparedStatement pstmt = null;
		try{
			pstmt = connection.prepareStatement(UPDATE_SQL);
			pstmt.setString(1, agencia.getEndereco());
			pstmt.setString(2, agencia.getNome_gerente());
			pstmt.setInt(3, agencia.getNumero());
			System.out.println("Atualizar agencia: " + agencia);
			int rows = pstmt.executeUpdate();
			
			if(rows > 0){
				super.commit();
				System.out.println(agencia + " atualizado!");
			}
		}catch (SQLException e) {
			try{
				System.out.println("Não atualizado!");
				super.rollback();
			}catch (SQLException e2) {
				e2.printStackTrace();
			}
		}finally {
			//fechar recursos abertos anteriormente
			try{
				if(pstmt != null){
					pstmt.close();
				}
			}catch (Exception e3) {
				e3.printStackTrace();
			}
		}
	}
	
	
}
