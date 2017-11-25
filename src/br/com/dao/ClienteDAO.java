package br.com.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.models.Cliente;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class ClienteDAO extends DataBaseDAO {

	public ClienteDAO() throws Exception {

	}

	public void createCliente(Cliente cliente) throws SQLException {
		// Verificar se o cliente é nulo
		if (cliente == null) {
			throw new SQLException("Cliente não pode ser nulo");
		}
		// Definir o comendo SQL para o Insert
		String INSERT_SQL = "INSERT INTO public.clientes(id, nome, email, cpf, nascimento, endereco, agencia) VALUES (?, ?, ?, ?, ?, ?,?)";
		PreparedStatement psmtm = null;
		try {
			// Obter o PrepareStament
			psmtm = connection.prepareStatement(INSERT_SQL);
			psmtm.setInt(1, cliente.getId());
			psmtm.setString(2, cliente.getNome());
			psmtm.setString(3, cliente.getEmail());
			psmtm.setString(4, cliente.getCpf());
			// converter Data JAVA para Date SQL
			psmtm.setDate(5, cliente.getNascimento());
			psmtm.setString(6, cliente.getEndereço());
			psmtm.setInt(7, cliente.getAgencia());
			// Executar o comando SQL de INSERT
			System.out.println("Inserindo Cliente: " + cliente);
			int rows = psmtm.executeUpdate();

			if (rows > 0) {
				// efetivar alterações de transação
				super.commit();
				System.out.println(cliente + " Inserido!");
			}
		} catch (SQLException e) {
			try {
				System.out.println(cliente + " Não inserido!");
				super.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			// Fechar recurso aberto anteriormente
			try {
				if (psmtm != null) {
					psmtm.close();
				}
			} catch (Exception e3) {
				e3.printStackTrace();
			}
		}
	}

	public Cliente selectCliente(int codigo) throws SQLException {
		Cliente cliente = null;
		if (codigo <= 0) {
			throw new SQLException("Código menor ou igual a zero!");
		}
		String SELECT_SQL = "select NOME,EMAIL,CPF,NASCIMENTO,ENDERECO, AGENCIA FROM CLIENTES WHERE id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// Obter o prepared statment para execultar a consulta SELECT_SQL
			pstmt = connection.prepareStatement(SELECT_SQL);
			// setar codigo como paramentro da clausura where
			pstmt.setInt(1, codigo);
			// executar consulta para obter um SetResult
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cliente = new Cliente(codigo, rs.getString("NOME"), rs.getString("EMAIL"), rs.getString("CPF"),
						rs.getDate("NASCIMENTO"), rs.getString("ENDERECO"), rs.getInt("AGENCIA"));
			} else {
				// Caso não encontre o Cliente gera um erro
				throw new SQLException("Nenhum cliente encontrado com o código: !" + codigo);
			}

		} finally {
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
		}
		return cliente;
	}

	public List<Cliente> recuperarClienteAll() throws SQLException {
		List<Cliente> clientes = new ArrayList<>();
		String SELECT_SQL = "select id, NOME,EMAIL,CPF,NASCIMENTO,ENDERECO, AGENCIA FROM CLIENTES";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(SELECT_SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				clientes.add(new Cliente(rs.getInt("id"), rs.getString("NOME"), rs.getString("EMAIL"),
						rs.getString("CPF"), rs.getDate("NASCIMENTO"), rs.getString("ENDERECO"), rs.getInt("AGENCIA")));
			}
		} finally {
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
		}
		return clientes;
	}

	public void updateCliente(Cliente cliente) throws SQLException {
		if (cliente == null) {
			throw new SQLException("Cliente não pode ser nulo!");
		}
		String UPDATE_SQL = "UPDATE clientes SET nome=?, email=?, cpf=?, nascimento=?, endereco=?, agencia=? WHERE id=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(UPDATE_SQL);
			pstmt.setString(1, cliente.getNome());
			pstmt.setString(2, cliente.getEmail());
			pstmt.setString(3, cliente.getCpf());
			pstmt.setDate(4, cliente.getNascimento());
			pstmt.setString(5, cliente.getEndereço());
			pstmt.setInt(7, cliente.getId());
			pstmt.setInt(6, cliente.getAgencia());
			System.out.println("Atualizar Cleinte: " + cliente);
			int rows = pstmt.executeUpdate();

			if (rows > 0) {
				super.commit();
				System.out.println(cliente + " atualizado!");
			}
		} catch (SQLException e) {
			try {
				System.out.println("Não atualizado!");
				super.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		} finally {
			// fechar recursos abertos anteriormente
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (Exception e3) {
				e3.printStackTrace();
			}
		}
	}

	public void deleteCliente(int codigo) throws Exception {
		if (codigo <= 0) {
			throw new SQLException("O código do cliente não pode ser menor que 0");
		}
		String DELETE_SQL = "DELETE FROM CLIENTES WHERE id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(DELETE_SQL);
			pstmt.setInt(1, codigo);
			System.out.println("Excluindo cliente!");
			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				super.commit();
				System.out.println(codigo + " Excluído!");
			}
		} catch (SQLException e) {
			try {
				System.out.println("Código não excluido!");
				super.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
			throw new Exception("Impossível excluir! Causa: " + e);
		} catch (RuntimeException e) {
			try {
				System.out.println(codigo + " Não excluido!");
				connection.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
			throw new Exception("Impossível excluir! Causa: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public void deleteClientesDeUmaAencia(int numeroAgencia) throws Exception {
		if (numeroAgencia <= 0) {
			throw new SQLException("O código do agencia não pode ser menor que 0");
		}
		String DELETE_SQL = "DELETE FROM CLIENTES WHERE agencia = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(DELETE_SQL);
			pstmt.setInt(1, numeroAgencia);
			System.out.println("Excluindo clientes!");
			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				super.commit();
				System.out.println(numeroAgencia + " Excluído!");
			}
		} catch (SQLException e) {
			try {
				System.out.println("Código não excluido!");
				super.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
			throw new Exception("Impossível excluir! Causa: " + e);
		} catch (RuntimeException e) {
			try {
				System.out.println(numeroAgencia + " Não excluido!");
				connection.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
			throw new Exception("Impossível excluir! Causa: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Cliente> recuperarClienteAgencia(int id) throws SQLException {
		List<Cliente> clientes = new ArrayList<>();
		String SELECT_SQL = "select id, NOME,EMAIL,CPF,NASCIMENTO,ENDERECO, AGENCIA FROM CLIENTES where agencia = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(SELECT_SQL);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				clientes.add(new Cliente(rs.getInt("id"), rs.getString("NOME"), rs.getString("EMAIL"),
						rs.getString("CPF"), rs.getDate("NASCIMENTO"), rs.getString("ENDERECO"), rs.getInt("AGENCIA")));
			}
		} finally {
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
		}
		return clientes;
	}

}
