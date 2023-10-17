package com.fidelity.investmonkey.service;
import java.util.ArrayList;
import java.util.List;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 

import com.fidelity.integration.ClientDao;
import com.fidelity.investmonkey.exception.NullException;
import com.fidelity.investmonkey.models.InvestmentPreference;
@Service
public class AddInvestmentPreferance {

	@Autowired
	private ClientDao clientDao;


	public InvestmentPreference addInvestmentPref(InvestmentPreference investPref) {
		if(investPref == null) {
			throw new NullException("Investment Preferance cannot be null");
		}
		boolean res = clientDao.insertInvestmentPreference(investPref);
		if(res==true)
		{
			return investPref;
		}
		return  null;
	}

	

	public InvestmentPreference updateInvestmentPref(InvestmentPreference investPref) {
		if(investPref == null) {
			throw new NullException("Investment Preferance cannot be null");
		}
		if(clientDao.getClientById(investPref.getClientId())==null) {
			throw new IllegalArgumentException("Client not found");
		}
		if(clientDao.getInvestmentPreferenceOfClient(investPref.getClientId())==null) {
			throw new IllegalArgumentException("There's no Investment Preferance for this Client");
		}

		boolean res = clientDao.updateInvestmentPreference(investPref);
		if(res==true)
		{
			return investPref;
		}
		return  null;
	}
	
	public InvestmentPreference getInvestmentPref(String clientId)
	{
		if(clientId==null)
		{
			throw new NullPointerException("ClientId cannot be null");
		}
		
		return clientDao.getInvestmentPreferenceOfClient(clientId);
	}

	
}