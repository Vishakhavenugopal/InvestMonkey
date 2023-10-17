package com.fidelity.integration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.fidelity.investmonkey.exception.ClientNotFoundException;
import com.fidelity.investmonkey.models.Balance;
import com.fidelity.investmonkey.models.Client;
import com.fidelity.investmonkey.models.Holdings;
import com.fidelity.investmonkey.models.InvestmentPreference;
import com.fidelity.investmonkey.models.Order;
import com.fidelity.investmonkey.models.Price;
import com.fidelity.investmonkey.models.Trade;

@Repository
@Primary
public class ClientDaoOracleImpl implements ClientDao {
	

@Autowired
private ClientMapper clientMapper;
	

	public boolean insertNewClientIdentification(Client client) {

		if(client.getClientIdentification()==null)
		{
			throw new NullPointerException("Client Identification cannot be null");
		}
		
		int rows = clientMapper.insertClientIdentification(client);
		if(rows<1)
		{
			throw new DatabaseException("Cannot insert Client Identification for clientId: "+client.getClientId());
		}
		return true;
	}



	@Override
    public Client getClientById(String clientId) {
		if(clientId==null) {
		throw new NullPointerException("Client id cannot be null");
	}
           Client client =  clientMapper.getClientById(clientId);
           if(client==null)
           {
        	   throw new DatabaseException("Client with Client ID: "+clientId+" is not registered");
           }
           return client;
    }

	public void addOrder(Order order) {
		if(order==null)
			{
				throw new IllegalArgumentException("Order object is null. Cannot insert into the "
					+ "database");
			}

			int row=clientMapper.addOrder(order);
			if(row<1) {
				throw new DatabaseException("Cannot execute SQL query for inserting the order with "
						+ "ID: "+order.getOrderId());
			}


	}
	@Override
	public int addTrade(Trade trade,Price price) {
		if(trade==null)
			{
				throw new NullPointerException("Trade object is null. Cannot insert into the "
						+ "database");
			}
		if(trade.getOrder()==null) {
			throw new NullPointerException("ORDER cannot be null");
		}
		Client client=this.getClientById(trade.getClientId());
		if(client==null) {
			throw new IllegalArgumentException("client not found");
		}
		addOrder(trade.getOrder());
		int rows=clientMapper.addTrade(trade);
		if(rows<1) {
			throw new DatabaseException("Cannot execute SQL query for inserting the trade with "
				+ "ID: "+trade.getTradeId());
		}
		return rows;
	}
	@Override
	public List<Trade> getTradeHistoryOfClientByClientId(String clientId){
		if(clientId==null) {
			throw new NullPointerException("Client id cannot be null");
		}
		Client client = getClientById(clientId);
		if(client==null) {
			throw new DatabaseException("No Client With client id "+clientId+" exists");
		}
		List<Trade> tradeHistory=clientMapper.getTradeHistoryOfClientByClientId(clientId);
		//System.out.println("SIZE=> "+tradeHistory.size());

		return tradeHistory;
	}

	@Override
		public List<Holdings> getHoldingsOfClient(String clientId) {
			// TODO Auto-generated method stub
		Client client =  clientMapper.getClientById(clientId);
		if(client==null)
		{
			throw new DatabaseException("No client Found");
		}
			return clientMapper.getHoldingsOfClient(clientId);
		}

	 

		@Override
		public int updateHoldingsOfClient(String clientId, Holdings holding) {
			int updatedQuantity=0;
			double updatedTotalPrice=0;
			if(clientId==null) {
				throw new NullPointerException("Cleint cannot be null");
			}
			List<Holdings> holdings=new ArrayList<>();
			holdings=this.getHoldingOfClientByInstrumentId(clientId,holding.getInstrumentId());
			System.out.println("HOLDING INSTRUMENT => "+holdings.get(0).getInstrumentId());
			if(holdings.size()==0) {
				throw new DatabaseException("client does not own any instument with id or client not present" + holding.getInstrumentId() );

			}
			else {

				Map<String, Object> holdingData = new HashMap<>();
				holdingData.put("clientId", clientId);
				holdingData.put("holding",holding);
				int rows=clientMapper.updateHoldingsOfClient(holdingData);
				if(rows==1) {
					
					return rows;
				}
				else {
					throw new DatabaseException("UPDTE HOLDING FAILED");
				}
			}

		}

	 

		@Override
		public List<Holdings> getHoldingOfClientByInstrumentId(String clientId, String instrumentId) {
			// TODO Auto-generated method stub
			if(clientId==null || instrumentId==null) {
				throw new NullPointerException("CLIENT OR INSTRUMENT ID CANNOT BE NULL");
			}
			Client client=this.getClientById(clientId);
			if(client==null) {
				throw new DatabaseException("Client Not Present");
			}
			Map<String, Object> holdingData = new HashMap<>();
			holdingData.put("clientId", clientId);  
			holdingData.put("instrumentId", instrumentId);
			return clientMapper.getHoldingOfClientByInstrumentId(holdingData);
		}

	 

		@Override
		public int insertInstrumentToHoldingsOfClient(String clientId, Trade trade, Price price) {
			// TODO Auto-generated method stub
			if(clientId==null||trade==null||price==null) {
				throw new NullPointerException("Null Deatils Not Allowed");

			}
			Map<String,Object> instrumentHolding=new HashMap<>();
			instrumentHolding.put("clientId", clientId);
			instrumentHolding.put("trade", trade);
			instrumentHolding.put("instrumentDescription", price.getInstrument().getInstrumentDescription());
			instrumentHolding.put("askNumber", price.getAskPrice());
			instrumentHolding.put("categoryId",price.getInstrument().getCategoryId());
			List<Holdings> holdings=new ArrayList<>();
			holdings=this.getHoldingOfClientByInstrumentId(clientId,trade.getInstrumentId());
			//System.out.println("SIZE OF HOLDINGS =>"+holdings.size());
			if(holdings.size()==0) {
				int rows= clientMapper.insertInstrumentToHoldingsOfClient(instrumentHolding);
				if(rows==1) {
					return rows;
				}
				else {
					throw new DatabaseException("cannot insert new holding ");
				}
			}
			else {
				throw new DatabaseException("HOLDING ALREADY PRESENT");
			}

		}

	@Override
	public boolean insertClientBalance(String clientId, double amount) 
	{
		Client client = getClientById(clientId);
		
		Map<String, Object> insertParams = new HashMap<>();
		insertParams.put("clientId", clientId);  
		insertParams.put("balance", amount);
		
		int rows = clientMapper.insertClientBalance(insertParams);
		if(rows<1)
		{
			throw new DatabaseException("Balance cannot be inserted for client with ID: "+clientId);
		}
		return true;
		
	}

	@Override
	public double getClientBalance(String clientId) {
		if(clientId==null)
		{
			throw new NullPointerException("ClientId cannot be null");
		}
		Client client = getClientById(clientId);
		Balance balance = clientMapper.getClientBalance(clientId);
		System.out.println(balance.getBalance());
		return balance.getBalance();
	}

	@Override
	public boolean insertNewClient(Client client) throws SQLException {
		if(client==null)
		{
			throw new NullPointerException("Client cannot be null");
		}
		
		int rows = clientMapper.insertNewClient(client);
		if(rows<1)
		{
			throw new DatabaseException("Cannot insert Client details for clientId: "+client.getClientId());
		}
		boolean res = insertNewClientIdentification(client);
		if(res==true)
		{
			res = insertClientBalance(client.getClientId(),1000000);
			if(res==true)
			{
				return true;
			}
		}
		return false;
		
	}

	@Override
	public boolean updateClient(Client client) {
		if(client==null) 
		{
			throw new NullPointerException("Client Cannot be Null");
		}
		
		if(client.getClientId()==null||client.getClientIdentification()==null||client.getEmail()==null||client.getFirstName()==null||client.getLastName()==null) 
		{
			throw new NullPointerException("no field can be null");
		}

		int rows = clientMapper.updateClient(client);
		if(rows<1)
		{
			throw new DatabaseException("Client with Id clientId: "+client.getClientId()+" cannot be updated");
		}
		return true;
		
	}

	@Override
	public boolean deleteClientById(String clientId) 
	{
		if(clientId==null) 
		{
			throw new NullPointerException("client id cannot be null");
		}
		
		boolean res = deleteClientIdentification(clientId);
		if(res==true)
		{
			int rows = clientMapper.deleteClient(clientId);
			if(rows<1)
			{
				throw new DatabaseException("Cannot delete Client with ClientId: "+clientId);
			}
		}
		return true;
		
		
	}

	private boolean deleteClientIdentification(String clientId) 
	{
		if(clientId==null) 
		{
			throw new NullPointerException("client id cannot be null");
		}
		
		int rows = clientMapper.deleteClientIdentification(clientId);
		
		if(rows<1)
		{
			throw new DatabaseException("ClientIdentification cannot be found for client with Id: "+clientId);
		}
		return true;	
	}



	@Override
	public boolean verifyClientLogin(String email, String password) 
	{
		if(email == null || email == "")
			throw new IllegalArgumentException("Email cannot be empty");
		
		if(!email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
			throw new IllegalArgumentException("Invalid Email");
		}
		
		if(password == null || password == "")
			throw new IllegalArgumentException("Invalid Password");
		
		Client client = getClientByEmail(email);
		
		if(client == null)
			throw new ClientNotFoundException("User not registered");
		
		if(client.getPassword().contentEquals(password)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updatePassword(String clientId, String oldPassword, String password) {
		if(clientId==null||password==null || oldPassword==null) 
		{
			throw new NullPointerException("email and password cannot be null");
		}
	
		Client client=getClientById(clientId);
		
		if(!oldPassword.equals(client.getPassword())) 
		{
			return false;
		}
		if(oldPassword.equals(client.getPassword())) 
		{
			Map<String, String> updateParams = new HashMap<>();
			updateParams.put("password",password); 
			updateParams.put("clientId", clientId);
			int rows = clientMapper.updatePassword(updateParams);
			if(rows<1)
			{
				throw new DatabaseException("Client Password cannot be updated for client with ID: "+clientId);
			}
		}
		return true;
	}

	@Override
	public Client getClientByEmail(String email) 
	{
		if(email==null) 
		{
			throw new NullPointerException("Client email cannot be null");
		}
		Client client = clientMapper.getClientByEmail(email);
		return client;
	}
	
	@Override
	public boolean insertInvestmentPreference(InvestmentPreference investPref) {
		if(investPref == null)
		{
			throw new NullPointerException("Investment Preference object cannot be null");
		}
		
		int rows = clientMapper.insertInvestmentPreference(investPref);
		if(rows<1)
		{
			throw new DatabaseException("Insert investment preference cannot be performed for clientId: "+investPref.getClientId());
		}
		return true;
	}



	@Override
	public InvestmentPreference getInvestmentPreferenceOfClient(String clientId)
	{
		if(clientId==null)
		{
			throw new NullPointerException("Client ID cannot be null");
		}
		Client client = getClientById(clientId);
		
		InvestmentPreference pref = clientMapper.selectInvestmentPreferenceByClientId(client.getClientId());
		if(pref==null)
        {
     	   throw new DatabaseException("Preference with Client ID: "+clientId+" is not yet created");
        }
        return pref;
		
	}



	@Override
	public boolean updateInvestmentPreference(InvestmentPreference investPref) {
		if(investPref == null)
		{
			throw new NullPointerException("Investment Preference object cannot be null");
		}
		
		int rows = clientMapper.updateInvestmentPreference(investPref);
		if(rows<1)
		{
			throw new DatabaseException("Upadte investment preference cannot be performed for clientId: "+investPref.getClientId());
		}
		return true;
	}



	@Override
	public boolean updateClientBalance(String clientId, double amount, String direction, double balance) {
		if(direction.equals("B"))
		{
			balance-=amount;
		}
		if(direction.equals("S"))
		{
			balance+=amount;
		}
		Balance bal = new Balance(clientId,balance);
		
		int rows = clientMapper.updateClientBalance(bal);
		if(rows<1)
		{
			throw new DatabaseException("Update od client balance cannot be performed");
		}
		return true;
		
	}



	@Override
	public int deleteHoldings(String clientId,String instrumentId) {
		boolean res = clientMapper.deleteHolding(clientId,instrumentId);
		if(res==false)
		{
			throw new DatabaseException("Update od client balance cannot be performed");
		}
		return 1;
	}
	
	
}
