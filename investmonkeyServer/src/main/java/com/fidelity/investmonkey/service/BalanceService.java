package com.fidelity.investmonkey.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.integration.ClientDao;
import com.fidelity.investmonkey.models.Balance;

@Service
public class BalanceService 
{
	List<Balance> balances = new ArrayList<Balance>();
	
	@Autowired
	private ClientDao dao;
	
	
	public double getClientBalance(String clientId)
	{
	   return dao.getClientBalance(clientId);
	  }
	
	public boolean updateClientBalance(String clientId,String direction,double amount)
	{
		if(amount<=0)
		{
			throw new IllegalArgumentException("Amount to be added must be greater than Zero");
		}
		double balance = getClientBalance(clientId);
		return dao.updateClientBalance(clientId, amount, direction, balance);
		 
	}
}
