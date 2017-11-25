package br.com.junit;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import br.com.dao.AgenciaDAO;
import br.com.dao.ClienteDAO;
import br.com.models.Agencia;
import br.com.models.Cliente;

@FixMethodOrder(MethodSorters.JVM)
public class TesteJUnit_Cliente_Agencia {
	
	private static AgenciaDAO agenciaDAO;
	private static ClienteDAO clienteDAO ;
	
	@BeforeClass
	public static void inicialização(){
		try{
			agenciaDAO = new AgenciaDAO();
			clienteDAO = new ClienteDAO();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//######################  Agência  ########################
	@Test
	public void createAgencia(){
		Agencia agencia1 = new Agencia(3333,"Rio Tinto", "João");
		try{
			agenciaDAO.connect();
			agenciaDAO.createAgencia(agencia1);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateAgencia(){
		Agencia agencia2 = new Agencia(3333,"Mamanguape-PB", "Rodrigo");
		try{
			agenciaDAO.connect();
			agenciaDAO.updateAgencia(agencia2);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void selectAgencia(){
		System.out.println("======== Get Agência ========\n");
		Agencia agencia = null;
		try{
			agenciaDAO.connect();
			agencia = agenciaDAO.recuperarAgenciaComClientes(2222);
			System.out.println(agencia);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void selectAgenciasAll(){
		System.out.println("======== Get All Agências ========\n");
		List<Agencia> agencias = new ArrayList<>();
		try{
			agenciaDAO.connect();
			agencias.addAll(agenciaDAO.getAllAgencias());
		}catch (Exception e) {
			e.printStackTrace();
		}
		for(Agencia a : agencias){
			System.out.println(a + "\n#####################\n");
		}
	}
	
//############################ Cliente ########################################3
	
	@Test
	public void createCliente(){
		Cliente cliente = new Cliente(4,"João","uelio@hotmail.com", "123123", new java.sql.Date(System.currentTimeMillis()), "Conjunto", 3333 );
		try{
			clienteDAO.connect();
			clienteDAO.createCliente(cliente);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateCliente(){
		//Onde nesse update pode ser alterado qualquer dado do cliente, inclusive sua agência!
		Cliente cliente = new Cliente(3,"Matheus","Matehus@hotmail.com", "1222", new java.sql.Date(System.currentTimeMillis()), "Rua da aurora", 2222 );
		try{
			clienteDAO.connect();
			clienteDAO.updateCliente(cliente);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void selectCliente(){
		System.out.println("======== Get Clientes ========\n");
		Cliente cliente = null;
		try{
			clienteDAO.connect();
			cliente = clienteDAO.selectCliente(1);
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(cliente);
	}
	
	@Test
	public void selectClienteAll(){
		System.out.println("======== Get All Clientes ========\n");
		List<Cliente> clientes = new ArrayList<>();
		try{
			clienteDAO.connect();
			clientes.addAll(clienteDAO.recuperarClienteAll());
		}catch (Exception e) {
			e.printStackTrace();
		}
		for(Cliente c : clientes){
			System.out.println(c + "\n#######################\n");
		}
	}
	
	@Test
	public void selectClientesDeUmaAgencia(){
		System.out.println("======== Get Clientes de uma Agência ========\n");
		List<Cliente> clientes = new ArrayList<>();
		try{
			clienteDAO.connect();
			clientes.addAll(clienteDAO.recuperarClienteAgencia(25));
		}catch (Exception e) {
			e.printStackTrace();
		}
		for(Cliente c : clientes){
			System.out.println(c + "\n#######################\n");
		}
	}
	
//###################### DELETE  / Agência / Cliente   ########################
	@Test
	public void deleteClienteId(){
		try{
			clienteDAO.connect();
			clienteDAO.deleteCliente(2);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteClientesDeUmaAgencia(){
		try{
			clienteDAO.connect();
			clienteDAO.deleteClientesDeUmaAencia(3333);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteAgencia(){
		try{
			agenciaDAO.connect();
			agenciaDAO.deleteAgencia(3333);
		}catch (Exception e) {	
			e.printStackTrace();
		}
	}
	



	

}
