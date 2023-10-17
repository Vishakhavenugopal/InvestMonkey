package com.fidelity.investmonkey.service;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.integration.ClientDao;
import com.fidelity.investmonkey.exception.NoSufficientBalanceException;
import com.fidelity.investmonkey.exception.TradeException;
import com.fidelity.investmonkey.models.Client;
import com.fidelity.investmonkey.models.ClientInfo;
import com.fidelity.investmonkey.models.Holdings;
import com.fidelity.investmonkey.models.Order;
import com.fidelity.investmonkey.models.OrderInfo;
import com.fidelity.investmonkey.models.Price;
import com.fidelity.investmonkey.models.Trade;
import com.fidelity.investmonkey.models.TradeInfo;

@Service
public class TradeService 
{
	
	@Autowired
	private ClientDao dao;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	BalanceService balanceService;
	
	@Autowired
	PortfolioService portfolioService;
	
	@Autowired
	private FmtsService fmtsService;
	
	 private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	    // Method to generate a random alphanumeric string of the specified length
	    public static String generateRandomString(int length) {
	        SecureRandom random = new SecureRandom();
	        StringBuilder randomString = new StringBuilder(length);

	        for (int i = 0; i < length; i++) {
	            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
	            char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
	            randomString.append(randomChar);
	        }

	        return randomString.toString();
	    }
	    
	private List<Price> priceList;
	
	
	public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }
	public List<Price> getPriceList() {
        return priceList;
    }
	
	public List<Price> getListOfInstruments()
	{
		return priceList;
	}
	
	public Trade generateTrade(Order order, Price price)
	{
		Client client = clientService.getClientById(order.getClientId());
		ClientInfo info = new ClientInfo(client.getEmail(),client.getClientId(),"");
		String token = fmtsService.getTokenFromFmts(info);
		
		
		double cashValue=order.getQuantity()*order.getTargetPrice();
		if(dao.getClientBalance(order.getClientId())<cashValue && order.getDirection().equals("B"))
		{
			throw new NoSufficientBalanceException("Your cash balance is less to buy it");
		}
		 double tolerance = 0.05;
		double difference =  Math.abs(price.getAskPrice()-price.getBidPrice());
		double fivePercent = price.getAskPrice() * tolerance;
		if(difference > fivePercent)
		{
			throw new TradeException("Difference is greater that 5%. Trade cannot be processed now");
		}
		if(order.getDirection().equals("B"))
		{
			if(order.getQuantity()<price.getInstrument().getMinQuantity())
			{
				throw new TradeException("Buy Quantity must be atleast "+price.getInstrument().getMinQuantity());
			}
			if(order.getQuantity()>price.getInstrument().getMaxQuantity())
			{
				throw new TradeException("Buy quantity cannot exceed "+price.getInstrument().getMaxQuantity());
			}
		}
		if(order.getDirection().equals("S")) {
			if(portfolioService.getHoldingByInstrumentId(order.getClientId(),order.getInstrumentId())==null) {
				throw new TradeException("Cannot sell what you haven't purchased yet ");
			}
			Holdings sellHolding=portfolioService.getHoldingByInstrumentId(order.getClientId(),order.getInstrumentId());
			if(order.getQuantity()>sellHolding.getQuantity()) {
				throw new TradeException("Cannot sell more than you own");
			}
		}
		
		OrderInfo orderInfo = new OrderInfo(order.getOrderId(),order.getQuantity(),order.getTargetPrice(),order.getDirection(),order.getClientId(),order.getInstrumentId(),token);
		TradeInfo tradeInfo = fmtsService.executeTrade(orderInfo);
		Trade trade= new Trade(generateRandomString(6),order.getQuantity(),order.getTargetPrice(),order.getDirection(),
				order,cashValue,order.getClientId(),order.getInstrumentId());
		dao.addTrade(trade, price);
		balanceService.updateClientBalance(order.getClientId(), order.getDirection(), cashValue);
		if(order.getDirection().equals("B"))
		{
			Holdings holding = new Holdings(order.getInstrumentId(),price.getInstrument().getInstrumentDescription(),
					price.getInstrument().getCategoryId(),order.getTargetPrice(),order.getQuantity(),cashValue);
			System.out.println(holding.toString());
			portfolioService.updateHoldings(order.getClientId(),holding,trade,price);
		}
		if(order.getDirection().equals("S")) {
			Holdings sellHolding=new Holdings(order.getInstrumentId(),price.getInstrument().getInstrumentDescription(),
					price.getInstrument().getCategoryId(),order.getTargetPrice(),order.getQuantity(),cashValue);
			portfolioService.updateHoldings(order.getClientId(),sellHolding,trade,price);
		}
		
		return trade;
	}
	
}
