package br.com.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DataBaseDAO {

	// Representa a class do Driver JDBC */
	public static final String DRIVER = "org.postgresql.Driver";
	
	// Representa a URL de conecção para o Driver */
	public static final String URL = "jdbc:postgresql://localhost/atividade_db_sergio";
	
	// Representa o usuário do bando de dados */
	public static final String DB_USER = "postgres";
	
	// Representa a senha do usuário do bando de dados */
	public static final String DB_PASS = "war123";
	
	protected Connection connection;
	
	public DataBaseDAO() throws Exception{
		// Tenta carregar a class do driver
		Class.forName(DRIVER);
		System.out.println("Driver Loaded!: "+DRIVER);
	}
	
	//Obter uma conexão via JDBC
	public void connect()  throws SQLException{
		//Obter uma conexão através do driver carregado
		//Usando URL, USUARIO e SENHA
		connection = java.sql.DriverManager.getConnection(URL, DB_USER, DB_PASS);
		
		//Colocar a conexão em estado de auto commit false, permitindo transações
		connection.setAutoCommit(false);
	}

	//Fechar a conexão existente
	public void close() throws SQLException{
		if(connection != null){
			//Fechar uma conexão já existente
			connection.close();
			System.out.println("Close!: "+ connection);
		}else{
			// Se a conexão já estiver fechada, envia um SQLException
			throw new SQLException("Conexão já fechada");
		}
	}
	
	//Commitar alterações submetidas ao banco de dados	
	public void commit() throws SQLException{
		// Se a conexão não for nula e existir com AutoCommit = FALSE
		if((connection != null) && (connection.getAutoCommit() == false)){
			// Informar ao Banco de de COMMIT
			connection.commit();
			System.out.println("Commit!: "+ connection);
		}else{
			//Arremessa execção de erro de commit
			throw new SQLException("Impossível commitar dados");
		}
	}

	// Desfazer alterações submetidas ao recurso de banco de dados
	public void rollback() throws SQLException{
		// Se a conexão não for nula e existir com AutoCommit = FALSE
		if((connection != null) && (connection.getAutoCommit() == false)){
			connection.rollback();
			System.out.println("RollingBack!: " + connection);
		}else{
			//Arremessa execção de erro de RollBack
			throw new SQLException("Impossível desfazer alterações");
		}
	}
}












