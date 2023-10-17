package com.fidelity.investmonkey.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.integration.ClientDao;
import com.fidelity.investmonkey.exception.ClientNotFoundException;
import com.fidelity.investmonkey.models.Holdings;
import com.fidelity.investmonkey.models.Portfolio;
import com.fidelity.investmonkey.models.Price;
import com.fidelity.investmonkey.models.Trade;

@Service
public class PortfolioService 
{
	
	private List<Trade> tradeHistory = new ArrayList<Trade>();
	
	
	
	@Autowired
	private ClientDao dao;
	
	
	public int updateHoldings(String clientId,Holdings holding,Trade trade,Price price)
	{
		Holdings hld=null;
		
		 hld = getHoldingByInstrumentId(clientId,price.getInstrument().getInstrumentId());
		System.out.println("Before If");
		System.out.println(trade.getDirection());
		if(hld==null && trade.getDirection().equals("B"))
		{
			System.out.println("Inside If");
			return dao.insertInstrumentToHoldingsOfClient(clientId, trade, price);
		}
		if(trade.getDirection().equals("B"))
		{
			System.out.println("Inside another");
			int quantity = holding.getQuantity()+hld.getQuantity();
			hld.setQuantity(quantity);
			double total = holding.getTotalPrice()+hld.getTotalPrice();
			hld.setTotalPrice(total);
		}
		if(trade.getDirection().equals("S"))
		{
			int quantity = hld.getQuantity()-holding.getQuantity();
			if(quantity==0)
			{
				 return dao.deleteHoldings(clientId, hld.getInstrumentId()); 
			}
			hld.setQuantity(quantity);
			double total = hld.getTotalPrice() - holding.getTotalPrice();
			hld.setTotalPrice(total);
		}
		
		return dao.updateHoldingsOfClient(clientId, hld);
		
	}
	public List<Holdings> getPortfolio(String clientId){
		
		return dao.getHoldingsOfClient(clientId);
	}
	
	public Holdings getHoldingByInstrumentId(String clientId,String instrumentId)
	{
		List<Holdings> holdings = dao.getHoldingOfClientByInstrumentId(clientId, instrumentId);
		if(holdings.size()==0)
		{
			return null;
		}
		return holdings.get(0);
	}
	
	
	
	


	
	
	public List<Trade> getAllTrades()
	{
		return this.getAllTrades();
	}
	
	public List<Trade> getTradeHistoryOfClient(String clientId)
	{
		return dao.getTradeHistoryOfClientByClientId(clientId);
	}
}
