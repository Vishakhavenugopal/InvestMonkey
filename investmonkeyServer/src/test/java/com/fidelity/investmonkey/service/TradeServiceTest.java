package com.fidelity.investmonkey.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.integration.DatabaseException;
import com.fidelity.investmonkey.exception.NoSufficientBalanceException;
import com.fidelity.investmonkey.exception.TradeException;
import com.fidelity.investmonkey.models.Instrument;
import com.fidelity.investmonkey.models.Order;
import com.fidelity.investmonkey.models.Price;

@SpringBootTest
@Transactional
class TradeServiceTest 
{
	@Autowired
	private TradeService tradeService;
	
	@Test
	public void testGetPriceList()
	{
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		TradeService tradeService = new TradeService();
		tradeService.setPriceList(prices);
		List<Price> result = tradeService.getListOfInstruments();
		Assertions.assertEquals(result.get(0).getAskPrice(),priceOne.getAskPrice());
	}
	
	@Test
	public void testGetPriceListNullList()
	{
		TradeService tradeService = new TradeService();
		List<Price> result = tradeService.getListOfInstruments();
		Assertions.assertEquals(result,null);
	}
	
	@Test
	public void testGenerateTradeBuySuccess()
	{
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		
		
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","456abc","123xyz");
		
	Assertions.assertTrue(tradeService.generateTrade(order, priceOne)!=null);	
		
		
	}
	
	@Test
	public void testGenerateTradeSellSuccess()
	{
		Instrument instrOne = new Instrument("instrument1","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		
		
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"S","456abc","123xyz");
		
	Assertions.assertTrue(tradeService.generateTrade(order, priceOne)!=null);	
		
		
	}
	
	@Test 
	void testToSellMoreThanBought() {
		Instrument instrOne = new Instrument("instrument1","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		
		
		Order order = new Order(instrOne.getInstrumentId(),1000,priceOne.getBidPrice(),"S","456abc","123xyz");
		Assertions.assertThrows(TradeException.class, () -> {
	tradeService.generateTrade(order, priceOne);
		});
	}

	
	@Test
	public void testGenerateTradeBuyQuantityLessThanMinimum()
	{
		Instrument instrOne = new Instrument("instrument1","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",100,100);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		
		
		Order order = new Order(instrOne.getInstrumentId(),1,priceOne.getBidPrice(),"B","456abc","123xyz");
		Assertions.assertThrows(TradeException.class, () -> {
	tradeService.generateTrade(order, priceOne);
		});
	}
	
	@Test
	public void testGenerateTradeQuantityGreaterThanMaximum() throws SQLException
	{
		Instrument instrOne = new Instrument("instrument1","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",100,100);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		
		
		Order order = new Order(instrOne.getInstrumentId(),102,priceOne.getBidPrice(),"B","456abc","123xyz");
		Assertions.assertThrows(TradeException.class, () -> {
	tradeService.generateTrade(order, priceOne);
		});
	}
	
	@Test
	public void testGenerateTradeInvalidClient()
	{
		Instrument instrOne = new Instrument("instrument1","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",100,100);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		
		
		Order order = new Order(instrOne.getInstrumentId(),102,priceOne.getBidPrice(),"B","5646abc","123xyz");
		Assertions.assertThrows(DatabaseException.class, () -> {
	tradeService.generateTrade(order, priceOne);
		});
	}
	
	@Test
	public void testGenerateTradeDifferenceLessThan5Percent()
	{
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		
		
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","456abc","123xyz");
		
	Assertions.assertTrue(tradeService.generateTrade(order, priceOne)!=null);	
	}
	
	@Test
	public void testGenerateTradeDifferenceLargerThan5Percent()
	{
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(104.75,120.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		
		
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","456abc","123xyz");
		
		Assertions.assertThrows(TradeException.class, () -> {
			tradeService.generateTrade(order, priceOne);
				});
	}

	@Test
	public void testGenerateTradewhenBalanceLessThanTargetPrice() throws SQLException
	{
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		
		
		Price priceOne = new Price(10000004.75,10000004.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		
		
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","456abc","123xyz");
		
		Assertions.assertThrows(NoSufficientBalanceException.class, () -> {
			tradeService.generateTrade(order, priceOne);
				});
	}

}
