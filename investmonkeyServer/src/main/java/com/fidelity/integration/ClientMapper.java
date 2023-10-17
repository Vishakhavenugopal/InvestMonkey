package com.fidelity.integration;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fidelity.investmonkey.models.Balance;
import com.fidelity.investmonkey.models.Client;
import com.fidelity.investmonkey.models.Holdings;
import com.fidelity.investmonkey.models.InvestmentPreference;
import com.fidelity.investmonkey.models.Order;
import com.fidelity.investmonkey.models.Trade;

@Mapper
public interface ClientMapper 
{
	Client getClientById(String clientId);

	int addOrder(Order order);

	int addTrade(Trade trade);

	List<Trade> getTradeHistoryOfClientByClientId(String clientId);

	List<Holdings> getHoldingOfClientByInstrumentId(Map<String,Object> holdingData);

	List<Holdings> getHoldingsOfClient(String clientId);
	
	boolean deleteHolding(@Param("clientId") String clientId,@Param("instrumentId") String instrumentId);

	int insertInstrumentToHoldingsOfClient(Map<String,Object> instrumentHolding);

	int updateHoldingsOfClient(Map<String, Object> updateHolding);

    
	int insertClientIdentification(Client client);
	int insertNewClient(Client client);
	int updateClient(Client client);
	int deleteClientIdentification(String clientId);
	int deleteClient(String clientId);
	Client getClientByEmail(String email);
	int updatePassword(Map<String,String>updateParams);
	Balance getClientBalance(String clientId);
	int insertClientBalance(Map<String,Object>insertParams);
	int insertInvestmentPreference(InvestmentPreference preference);
	InvestmentPreference selectInvestmentPreferenceByClientId(String clientId);
	int updateInvestmentPreference(InvestmentPreference preference);
	int updateClientBalance(Balance balance);
}
