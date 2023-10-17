package com.fidelity.investmonkey.service;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.integration.ClientDao;
import com.fidelity.investmonkey.models.Client;
import com.fidelity.investmonkey.models.ClientInfo;

@Service
public class ClientService {
	
	@Autowired
	private ClientDao dao;
	
	@Autowired
	private FmtsService fmtsService;
	

	
	public Client addNewClient(Client client) throws SQLException {
		if(client==null) {
			throw new NullPointerException("Client to be added cannot be null");
		}
		ClientInfo info = new ClientInfo(client.getEmail(),"","");
		System.out.println(info.toString());
		String test=fmtsService.getClientIdFromFmts(info);
		System.out.println("ID=> "+test);
		client.setClientId(fmtsService.getClientIdFromFmts(info));
		
		 dao.insertNewClient(client);
		 return client;
	}
	public boolean removeClient(Client client) {
		if(client==null) {
			throw new NullPointerException("Client to be added cannot be null");
		}
		dao.deleteClientById(client.getClientId());
		return true;
	}
	
	public Client getClientById(String clientId) {
		return dao.getClientById(clientId);
	}
	
	public Client getClientByEmail(String email) {
		return dao.getClientByEmail(email);
	}

	public boolean loginClient(String email,String password){
		return dao.verifyClientLogin(email, password);
	}
	
	public boolean updateClientPassword(String clientId,String oldPassword, String newPassword)
	{
		return dao.updatePassword(clientId, oldPassword, newPassword);
	}
	
	
	
}
