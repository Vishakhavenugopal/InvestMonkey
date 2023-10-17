package com.fidelity.investmonkey.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.integration.ClientDao;
import com.fidelity.integration.DatabaseException;
import com.fidelity.investmonkey.models.Holdings;
import com.fidelity.investmonkey.models.Instrument;
import com.fidelity.investmonkey.models.Order;
import com.fidelity.investmonkey.models.Price;
import com.fidelity.investmonkey.models.Trade;

@SpringBootTest
@Transactional
class PortfolioServiceTest {

	@Autowired
	private PortfolioService portfolioService;
	
	@Autowired
	private ClientDao dao;

	@Test
	public void getTradeHistoryOfClientSuccess() throws SQLException
	{
	Assertions.assertTrue(portfolioService.getTradeHistoryOfClient("456abc").size()>1);	
	}
	
	@Test
	public void getTradeHistoryOfClientInvalidClient() throws SQLException
	{
		assertThrows(DatabaseException.class, () -> {
			portfolioService.getTradeHistoryOfClient("125abc");
		});
	}
	
	
	

	@Test
	public void testClientDoesNotHaveAnyTrades()
	{
		Assertions.assertTrue(portfolioService.getTradeHistoryOfClient("123abc").size()==0);	
	}

	

//	@Test 
//	void toSellWhatClientDoesNotOwn(){
//		BalanceService balanceService = new BalanceService();
//		PortfolioService portfolioService = new PortfolioService();
//		TradeService tradeService = new TradeService();
//		ClientIdentification identification = new ClientIdentification("Aadhar","872357934426");
//		Client client = new Client("V","vishakha","6361724765","123abc","vishakhav965@gmail.com",LocalDate.of(2000, 10, 10),"Bharath","573201","vish123", identification);
//		ClientService clientService = new ClientService();
//		clientService.addNewClient(client);
//		balanceService.pushNewBalance("123abc");
//		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
//		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
//		Order orderOne = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","123abc","123xyz");
//		double remainingBalanceOne = tradeService.generateTrade(orderOne, priceOne,balanceService,portfolioService,clientService);
//		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
//		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
//		Assertions.assertThrows(TradeException.class,()->{
//			Order orderTwo = new Order(instrTwo.getInstrumentId(),6,priceTwo.getBidPrice(),"S","123abc","123abc");
//			double remainingBalanceTwo = tradeService.generateTrade(orderTwo, priceOne,balanceService,portfolioService,clientService);
//		});
//		
//	}
//	
//	
	@Test
	public void getPortfolioOfClientInvalidClient()
	{
		assertThrows(DatabaseException.class, () -> {
			portfolioService.getPortfolio("56abc");
		});
	}
	
	@Test
	public void testClientWithoutPortfolio() {
		Assertions.assertTrue(portfolioService.getPortfolio("123abc").size()==0);
	}
	
	@Test
	public void getPortfolioOfClientSuccess()
	{
		Assertions.assertTrue(portfolioService.getPortfolio("456abc").size()>0);	
	}
	
	@Test
	public void testInsertHoldings()
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
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","123abc","123pyz");
		double cashValue=order.getQuantity()*order.getTargetPrice();
		Trade trade = new Trade("112cttf",100,104.75,"B",order,cashValue,"123abc",order.getInstrumentId());
		dao.addTrade(trade,priceOne);
		Holdings holding = new Holdings(order.getInstrumentId(),priceOne.getInstrument().getInstrumentDescription(),
				priceOne.getInstrument().getCategoryId(),order.getTargetPrice(),order.getQuantity(),cashValue);
		int holdingAdded=portfolioService.updateHoldings("123abc",holding, trade, priceOne);
		Assertions.assertEquals(holdingAdded,1);
	}
	
	@Test
	public void testUpadteHoldings()
	{
		Instrument instrOne = new Instrument("instrument1","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		Instrument instrTwo = new Instrument("T67890","CUSIP","9128285M8","GOVT","USA, Note 3.125 15nov2028 10Y",10000,1);
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Price priceTwo = new Price(1.03375,1.03325,"21-AUG-19 10.00.01.042000000 AM GMT",instrTwo);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		prices.add(priceTwo);
		TradeService tradeService = new TradeService();
		tradeService.setPriceList(prices);
		List<Price> result = tradeService.getListOfInstruments();
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","456abc","123pyz");
		double cashValue=order.getQuantity()*order.getTargetPrice();
		Trade trade = new Trade("112cttf",100,104.75,"B",order,cashValue,"456abc",order.getInstrumentId());
		dao.addTrade(trade,priceOne);
		Holdings holding = new Holdings(order.getInstrumentId(),priceOne.getInstrument().getInstrumentDescription(),
				priceOne.getInstrument().getCategoryId(),order.getTargetPrice(),order.getQuantity(),cashValue);
		int holdingUpdated=portfolioService.updateHoldings("456abc",holding, trade, priceOne);
		Assertions.assertEquals(holdingUpdated,1);
	}
}
