package br.com.models;

import java.sql.Date;

public class Cliente {
	private int id;
	private String nome;
	private String email;
	private String cpf;
	private Date nascimento;
	private String endereço;
	private int agencia;
	
	public Cliente(){
		
	}

	public Cliente(int codigo, String nome, String email, String cpf, Date nascimento, String endereço, int agencia) {
		super();
		this.id = codigo;
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.nascimento = nascimento;
		this.endereço = endereço;
		this.agencia = agencia;
	}
	
	public int getAgencia() {
		return agencia;
	}

	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	public int getId() {
		return id;
	}

	public void setId(int codigo) {
		this.id = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereço() {
		return endereço;
	}

	public void setEndereço(String endereço) {
		this.endereço = endereço;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}
	
	@Override
	public String toString() {
		String dados = "Nome: " + this.nome ;
		return dados;
	}
	
	
	
}
