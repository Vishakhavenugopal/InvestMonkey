package com.fidelity.integration;

import java.sql.SQLException;
import java.util.List;

import com.fidelity.investmonkey.models.Client;
import com.fidelity.investmonkey.models.Holdings;
import com.fidelity.investmonkey.models.InvestmentPreference;
import com.fidelity.investmonkey.models.Price;
import com.fidelity.investmonkey.models.Trade;
import com.fidelity.investmonkey.service.TradeService;

public interface ClientDao {
	
	public int addTrade(Trade trade, Price price);
	public List<Trade> getTradeHistoryOfClientByClientId(String clientId);
	public List<Holdings> getHoldingsOfClient(String clientId);
	public int updateHoldingsOfClient(String clientId, Holdings holding);
	public int deleteHoldings(String clientId,String instrumentId);
	public int insertInstrumentToHoldingsOfClient(String clientId,Trade trade,Price price);
	
	public boolean updateClientBalance(String clientId,double amount,String direction,double balance);
	public boolean insertClientBalance(String clientId,double amount);
	public double getClientBalance(String clientId);
	
	public Client getClientById(String clientId);
	public boolean insertNewClient(Client client) throws SQLException;
	public boolean updateClient(Client client);
	public boolean deleteClientById(String clientId);
	public boolean verifyClientLogin(String email,String password);
	public boolean updatePassword(String clientId,String oldPassword,String Password);
	public Client getClientByEmail(String email);
	
	public boolean insertInvestmentPreference(InvestmentPreference investPref);
	public InvestmentPreference getInvestmentPreferenceOfClient(String clientId);
	public boolean updateInvestmentPreference(InvestmentPreference investPref);
	public List<Holdings> getHoldingOfClientByInstrumentId(String clientId, String instrumentId);

}
