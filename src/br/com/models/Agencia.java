package br.com.models;

import java.util.ArrayList;
import java.util.List;

public class Agencia {
	private int numero;
	private String endereco;
	private String nome_gerente;
	private List<Cliente> clientes;
	
	public Agencia(){
		this(0,"","",new ArrayList<Cliente>());
	}

	public Agencia(int numero, String endereco, String nome_gerente, List<Cliente> cliente) {
		super();
		this.numero = numero;
		this.endereco = endereco;
		this.nome_gerente = nome_gerente;
		this.clientes = new ArrayList<Cliente>();
	}
	
	public Agencia(int numero, String endereco, String nome_gerente) {
		super();
		this.numero = numero;
		this.endereco = endereco;
		this.nome_gerente = nome_gerente;
		this.clientes = new ArrayList<Cliente>();
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNome_gerente() {
		return nome_gerente;
	}

	public void setNome_gerente(String nome_gerente) {
		this.nome_gerente = nome_gerente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	@Override
	public String toString() {
		String resultado = "Número Agência: " + this.numero + " \n -----Clientes-----\n";
		for(Cliente c:this.clientes){
			resultado += "Nome: " + c.getNome() + "\n";
		}
		return resultado;
	}
}
