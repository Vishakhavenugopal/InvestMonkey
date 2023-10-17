package com.fidelity.integration;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.investmonkey.InvestmonkeyApplication;
import com.fidelity.investmonkey.exception.ClientNotFoundException;
import com.fidelity.investmonkey.models.Client;
import com.fidelity.investmonkey.models.ClientIdentification;
import com.fidelity.investmonkey.models.Holdings;
import com.fidelity.investmonkey.models.IncomeCategory;
import com.fidelity.investmonkey.models.Instrument;
import com.fidelity.investmonkey.models.InvestmentPreference;
import com.fidelity.investmonkey.models.Order;
import com.fidelity.investmonkey.models.Price;
import com.fidelity.investmonkey.models.RiskTolerance;
import com.fidelity.investmonkey.models.Trade;
import com.fidelity.investmonkey.service.TradeService;


@SpringBootTest(classes=InvestmonkeyApplication.class)
@Transactional
class ClientDaoOracleImplTest 
{
	@Autowired
	private ClientDao dao;
	
	private Client client;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Test
	void testGetClientById()
	{
		Assertions.assertTrue(dao.getClientById("123abc").getFirstName().equals("Jitin"));
	}


	@Test
	void getClientWithNullId() {
		Assertions.assertThrows(NullPointerException.class,()->{
			dao.getClientById(null);
		});
	}
	@Test
	void getValidClient() {
		Client client=dao.getClientById("123abc");
		Assertions.assertEquals(client.getPhone(),"123456789");
		
	}
	@Test
	void getClientNotPresent() {
		Assertions.assertThrows(DatabaseException.class,()->{
			dao.getClientById("xyz123");
		});
	}

	@Test
	void testInsertClientBalance()
	{
		
		Assertions.assertEquals(true,dao.insertClientBalance("123abc", 1000000));
	}
	
	@Test
	void testInsertClientBalanceInvalidClient()
	{
		Assertions.assertThrows(DatabaseException.class, () -> {
			dao.insertClientBalance("125abc", 1000000);
		});
	}
	
	@Test
	void testUpdateClientBalance()
	{
		Assertions.assertEquals(true,dao.updateClientBalance("456abc",10000,"B", 1000000));
	}
	
	@Test
	void testGetClientBalance()
	{
		Assertions.assertEquals(1000000,dao.getClientBalance("456abc"));
	}
	
	@Test
	void testGetClientBalanceInvalidClient()
	{
	
		Assertions.assertThrows(DatabaseException.class, () -> {
		dao.getClientBalance("125abc");
		});
	}

	
	@Test
	void insertNullClient() {
		Assertions.assertThrows(NullPointerException.class,()->{
			dao.insertNewClient(null);
		});
	}
	@Test
	void insertInvalidClient() throws SQLException {
		Assertions.assertThrows(NullPointerException.class,()->{
			ClientIdentification clientIdentification=new ClientIdentification("Aadhar","23456");
			Client client = new Client(null,"v","123456780","xyz123","vv@gmail.com",LocalDate.of(2001, 12, 30),"India","570011","vv123",clientIdentification);
			dao.insertNewClient(client);
		});

	}
	@Test
	void insertExistingClient() throws SQLException {
		Assertions.assertThrows(DuplicateKeyException.class,()->{
			ClientIdentification clientIdentification=new ClientIdentification("Aadhar","23456");
			Client client = new Client("jitin","dodeja","123456780","123abc","vv@gmail.com",LocalDate.of(2001, 12, 30),"India","570011","123abc",clientIdentification);
			dao.insertNewClient(client);
		});
	}
	@Test
	void insertNewClient() throws SQLException {
		int oldSize = JdbcTestUtils.countRowsInTable(jdbcTemplate, "client");
		int oldSizeIdentifications = JdbcTestUtils.countRowsInTable(jdbcTemplate, "client_identification");
		ClientIdentification clientIdentification=new ClientIdentification("Aadhar","23456");
		Client client = new Client("vishaka","v","123456780","xyz123","vv@gmail.com",LocalDate.of(2001, 12, 30),"India","570011","vv123",clientIdentification);
		boolean res = dao.insertNewClient(client);
		Assertions.assertEquals(res, true);
	}
	
	@Test
	void insertClientWithNoIdentification() {
		Assertions.assertThrows(NullPointerException.class,()->{
			Client client = new Client("vishkha","v","123456780","xyz123","vv@gmail.com",LocalDate.of(2001, 12, 30),"India","570011","vv123",null);
			dao.insertNewClient(client);
		});
	}
	
	@Test
	void updateNonExistingClient() {
		Assertions.assertThrows(DatabaseException.class,()->{
			ClientIdentification clientIdentification=new ClientIdentification("Aadhar","23456");
			Client client = new Client("vishaka","v","123456780","xyz123","vv@gmail.com",LocalDate.of(2001, 12, 30),"India","570011","vv123",clientIdentification);
			dao.updateClient(client);
		});

	}
	
	@Test
	void updateClient() {
		Assertions.assertEquals(1,JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "client","clientid = '123abc' and lastname='Dodeja'"));
		ClientIdentification clientIdentification=new ClientIdentification("Aadhar","1234567");
		Client client = new Client("jitin","a","123456789","123abc","vv@gmail.com",LocalDate.of(2001, 12, 30),"India","570011","vv123",clientIdentification);
		
		Assertions.assertEquals(true,dao.updateClient(client));
	}
	
	@Test
	void updateInvalidClient() {
		Assertions.assertThrows(NullPointerException.class,()->{
			ClientIdentification clientIdentification=new ClientIdentification("Aadhar","23456");
			Client client = new Client(null,"v","123456780","xyz123","vv@gmail.com",LocalDate.of(2001, 12, 30),"India","570011","vv123",clientIdentification);
			dao.updateClient(client);
		});
	}
	
	
	@Test
	void deleteClientNotPresent() {
		Assertions.assertThrows(DatabaseException.class,()->{
			dao.deleteClientById("xyz123");
		});

	}
	@Test
	void deleteInvalidClient() {
		Assertions.assertThrows(NullPointerException.class,()->{
			dao.deleteClientById(null);
		});
	}
	
	@Test
	void updatepassForNonExistantUser() {
		Assertions.assertThrows(DatabaseException.class,()->{
			dao.updatePassword("xyz123","jd123","sd123");
		});
	}
	
	@Test
	void updatePasswordforWrongOldPassword() {
		Assertions.assertEquals(dao.updatePassword("123abc","jd122","sd123"),false);
	}
	
	@Test
	void updatePassword() {
		Assertions.assertEquals(dao.updatePassword("123abc","jd123","sd123"),true);
		
	}
	
	@Test
	void updateNullPassword() {
		Assertions.assertThrows(NullPointerException.class,()->{
			dao.updatePassword(null, "jd123", "sd123");
		});
	}
	
	@Test
	void testGetInvestmentPreference()
	{
		Assertions.assertEquals(dao.getInvestmentPreferenceOfClient("456abc").getIncomeCategory().getValue(), IncomeCategory.CATEGORY_1_10000.getValue());
	}
	
	@Test
	void testGetInvestmentPreferenceNullClientId()
	{
		Assertions.assertThrows(NullPointerException.class,()->{
		dao.getInvestmentPreferenceOfClient(null);
		});
	}
	
	@Test
	void testGetInvestmentPreferenceInvalidClientId()
	{
		Assertions.assertThrows(DatabaseException.class,()->{
			dao.getInvestmentPreferenceOfClient("125abc");
			});
	}
	
	
	
	@Test
	void testInsertInvestmentPreferenceInvalidClient()
	{
		InvestmentPreference preference = new InvestmentPreference("125abc","Foreign Trip",IncomeCategory.CATEGORY_10001_250000,
				RiskTolerance.LOW,5);
		Assertions.assertThrows(DataIntegrityViolationException.class,()->{
			dao.insertInvestmentPreference(preference);
			});
	}
	
	@Test
	void testInsertInvestmentPreferenceNullClientId()
	{
		Assertions.assertThrows(NullPointerException.class,()->{
			InvestmentPreference preference = new InvestmentPreference("","Foreign Trip",IncomeCategory.CATEGORY_10001_250000,
					RiskTolerance.LOW,5);
			dao.insertInvestmentPreference(preference);
			});
	}
	
	@Test
	void testInsertInvestmentPreferenceNullIncomeCategory()
	{
		Assertions.assertThrows(NullPointerException.class,()->{
			InvestmentPreference preference = new InvestmentPreference("125abc","Foreign Trip",null,
					RiskTolerance.LOW,5);
			dao.insertInvestmentPreference(preference);
			});
	}
	
	@Test
	void testInsertInvestmentPreferenceZeroLengthOfInvestment()
	{
		Assertions.assertThrows(IllegalArgumentException.class,()->{
			InvestmentPreference preference = new InvestmentPreference("125abc","Foreign Trip",IncomeCategory.CATEGORY_10001_250000,
					RiskTolerance.LOW,0);
			dao.insertInvestmentPreference(preference);
			});
	}
	
	@Test
	void testInsertInvestmentPreferenceNegativeLengthOfInvestment()
	{
		Assertions.assertThrows(IllegalArgumentException.class,()->{
			InvestmentPreference preference = new InvestmentPreference("125abc","Foreign Trip",IncomeCategory.CATEGORY_10001_250000,
					RiskTolerance.LOW,-5);
			dao.insertInvestmentPreference(preference);
			});
	}
	
	@Test
	void testUpdateInvestmentPreference()
	{
		InvestmentPreference preference = new InvestmentPreference("456abc","Foreign Trip",IncomeCategory.CATEGORY_10001_250000,
				RiskTolerance.LOW,5);
		Assertions.assertTrue(dao.updateInvestmentPreference(preference)==true);
	}
	
	@Test
	void testUpdateInvestmentPreferenceInvalidClient()
	{
		InvestmentPreference preference = new InvestmentPreference("125abc","Foreign Trip",IncomeCategory.CATEGORY_10001_250000,
				RiskTolerance.LOW,5);
		Assertions.assertThrows(DatabaseException.class,()->{
			dao.updateInvestmentPreference(preference);
			});
	}
	
	@Test
	void testAddTrade() {
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
		int rowsAdded=dao.addTrade(trade,priceOne);
		int holdingAdded=dao.insertInstrumentToHoldingsOfClient("123abc", trade, priceOne);

		Assertions.assertEquals(rowsAdded,1);
 

	}
	@Test
	void testGetTradeHistory() {

		List<Trade> tradeHistory=dao.getTradeHistoryOfClientByClientId("456abc");
		Assertions.assertEquals(tradeHistory.size(),3);

 

	}
	@Test
	void testGetHoldingByClientAndIInstrument() {
		List<Holdings> holdings=dao.getHoldingOfClientByInstrumentId("456abc","instrument1" );
		Assertions.assertEquals(holdings.size(),1);
	}
	@Test
	void getHoldingsAbsentClient() {
		Assertions.assertThrows(DatabaseException.class,()->{
			List<Holdings> holdings=dao.getHoldingOfClientByInstrumentId("4123abc","instrument1" );
		});
	}
	@Test
	void testGetHoldingByClientId() {
		List<Holdings> holdings=dao.getHoldingsOfClient("456abc");
		Assertions.assertTrue(holdings.size()>2);
	}

	@Test
	void testInsertNewHolding() {
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
		int rowsAdded=dao.addTrade(trade,priceOne);
		int holdingAdded=dao.insertInstrumentToHoldingsOfClient("123abc", trade, priceOne);
		Assertions.assertEquals(rowsAdded,1);
		Assertions.assertEquals(holdingAdded,1);
	}
	@Test
	void insertExistingHolding() {
		Instrument instrOne = new Instrument("instrument1","CUSIP","46625H100", "STOCK", "test stock",1000,1);
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		TradeService tradeService = new TradeService();
		tradeService.setPriceList(prices);
		List<Price> result = tradeService.getListOfInstruments();
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","456abc","123pyz");
		double cashValue=order.getQuantity()*order.getTargetPrice();
		Trade trade = new Trade("112cttf",100,104.75,"B",order,cashValue,order.getClientId(),order.getInstrumentId());
		int rowsAdded=dao.addTrade(trade,priceOne);
		Assertions.assertThrows(DatabaseException.class,()->{
			int holdingAdded=dao.insertInstrumentToHoldingsOfClient(order.getClientId(), trade, priceOne);
		});


	}
	@Test
	void insertHoldingForNullTrade() {
		Instrument instrOne = new Instrument("instrument1","CUSIP","46625H100", "STOCK", "test stock",1000,1);
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		TradeService tradeService = new TradeService();
		tradeService.setPriceList(prices);
		List<Price> result = tradeService.getListOfInstruments();
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","456abc","123pyz");
		Assertions.assertThrows(NullPointerException.class,()->{
			int holdingAdded=dao.insertInstrumentToHoldingsOfClient(order.getClientId(),null, priceOne);
		});
	}
	@Test
	void updateExistingHolding() {
		Instrument instrOne = new Instrument("instrument1","CUSIP","46625H100", "STOCK", "test stock",1000,1);
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		TradeService tradeService = new TradeService();
		tradeService.setPriceList(prices);
		List<Price> result = tradeService.getListOfInstruments();
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"Buy","456abc","123pyz");
		double cashValue=order.getQuantity()*order.getTargetPrice();
		Trade trade = new Trade("112cttf",100,104.75,"Buy",order,cashValue,order.getClientId(),order.getInstrumentId());
		int rowsAdded=dao.addTrade(trade,priceOne);
		List<Holdings> holding=dao.getHoldingOfClientByInstrumentId(trade.getClientId(), trade.getInstrumentId());
		int quantity=holding.get(0).getQuantity()+trade.getQuantity();
		double totalPrice=holding.get(0).getTotalPrice()+trade.getCashValue();
		holding.get(0).setQuantity(quantity);
		holding.get(0).setTotalPrice(totalPrice);
		Holdings updatedHolding=holding.get(0);
		int holdingUpdated=dao.updateHoldingsOfClient("456abc",updatedHolding );
		Assertions.assertEquals(holdingUpdated,1);
	}
	@Test
	void updateNonExistingClientHolding() {
		Instrument instrOne = new Instrument("instrument1","CUSIP","46625H100", "STOCK", "test stock",1000,1);
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		List<Price> prices = new ArrayList<Price>();
		prices.add(priceOne);
		TradeService tradeService = new TradeService();
		tradeService.setPriceList(prices);
		List<Price> result = tradeService.getListOfInstruments();
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"Buy","123abc","123pyz");
		double cashValue=order.getQuantity()*order.getTargetPrice();
		Trade trade = new Trade("112cttf",100,104.75,"Buy",order,cashValue,order.getClientId(),order.getInstrumentId());
		int rowsAdded=dao.addTrade(trade,priceOne);
		Assertions.assertThrows(IndexOutOfBoundsException.class,()->{
			List<Holdings> holding=dao.getHoldingOfClientByInstrumentId(trade.getClientId(), trade.getInstrumentId());
			int quantity=holding.get(0).getQuantity()+trade.getQuantity();
			double totalPrice=holding.get(0).getTotalPrice()+trade.getCashValue();
			holding.get(0).setQuantity(quantity);
			holding.get(0).setTotalPrice(totalPrice);
			Holdings updatedHolding=holding.get(0);
			int holdingUpdated=dao.updateHoldingsOfClient("123abc",updatedHolding );
		});

	}

//	
	@Test
	void testAddTradeNullOrder()
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

		double cashValue=9787336;

		Assertions.assertThrows(NullPointerException.class, () -> {
			Trade trade = new Trade("112ghtf",100,104.75,"B",null,cashValue,"123abc","N123456");
			dao.addTrade(trade,priceOne);
			});
	}
//	
	@Test
	void testAddNullTrade()
	{
		Assertions.assertThrows(NullPointerException.class, () -> {
			dao.addTrade(null,null);
			});
	}
//	
	@Test
	void testAddTradeInvalidClient()
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
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","13abc","123xyz");
		double cashValue=order.getQuantity()*order.getTargetPrice();
		Trade trade = new Trade("112ghtf",100,104.75,"B",order,cashValue,"13abc",order.getInstrumentId());
		Assertions.assertThrows(DatabaseException.class, () -> {
		dao.addTrade(trade,priceOne);
		});
	}
//	
	@Test
	void testAddTradeSell()
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
		Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","123abc","123xyz");
		double cashValue=order.getQuantity()*order.getTargetPrice();
		Trade trade = new Trade("112ghtf",100,104.25,"S",order,cashValue,"123abc",order.getInstrumentId());
		int tradeAdded=dao.addTrade(trade,priceOne);
		Assertions.assertEquals(tradeAdded,1);

	}

 

	@Test
	void testGetHoldingsOfClientWithNoBuy()
	{
		Assertions.assertEquals(dao.getHoldingsOfClient("123abc").size(),0);	
	}
	@Test
	void testGetHoldingsForClientWithNoInstrument() {
		Assertions.assertEquals(dao.getHoldingOfClientByInstrumentId("456abc","instrument9").size(),0);	
	}

 

 

	@Test
	void testGetTradeHistoryOfClientMoreThanHundredValues()
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



		for(int i=0;i<120;i++)
		{
			String orderId = "abc"+i;
			Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","123abc",orderId);
			double cashValue=order.getQuantity()*order.getTargetPrice();
			Trade trade = new Trade(orderId,100,104.25,"B",order,cashValue,"123abc",order.getInstrumentId());
			dao.addTrade(trade,priceOne);
	}

		Assertions.assertEquals(100,dao.getTradeHistoryOfClientByClientId("123abc").size());
	}
	
	@Test
	void testGetTradeHistoryOfClientLessThanHundredValues()
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

		for(int i=0;i<10;i++)
		{
			String orderId = i+"abc";
			Order order = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","123abc",orderId);
			double cashValue=order.getQuantity()*order.getTargetPrice();
			Trade trade = new Trade(orderId,100,104.25,"S",order,cashValue,"123abc",order.getInstrumentId());
			dao.addTrade(trade,priceOne);
	}
		List<Trade> tradeHistory = dao.getTradeHistoryOfClientByClientId("123abc");
		Assertions.assertEquals(10,tradeHistory.size());


	}

 

	@Test
	void testGetTradeHistoryOfClientNoTradeDone()
	{
		Assertions.assertEquals(0,dao.getTradeHistoryOfClientByClientId("123abc").size());
	}
	
	@Test
	void testVerifyClientLoginThrowsErrorIfUsernameIsNull() {
		Assertions.assertThrows(IllegalArgumentException.class, ()-> {
			dao.verifyClientLogin(null, "abc123");
		});
	}
	@Test
	void testVerifyClientLoginThrowsErrorIfUsernameIsAnEmptyString() {
		Assertions.assertThrows(IllegalArgumentException.class, ()-> {
			dao.verifyClientLogin("", "abc123");
		});
	}
	@Test
	void testVerifyClientLoginThrowsErrorIfUsernameIsNotinTheCorrectFormat() {
		Assertions.assertThrows(IllegalArgumentException.class, ()-> {
			dao.verifyClientLogin("abcgmail.com", "abc123");
		});
	}
	@Test
	void testVerifyClientLoginThrowsErrorIfPasswordIsNull() {
		Assertions.assertThrows(IllegalArgumentException.class, ()-> {
			dao.verifyClientLogin("abc@gmail.com", null);
		});
	}
	@Test
	void testVerifyClientLoginThrowsErrorIfPasswordIsAnEmptyString() {
		Assertions.assertThrows(IllegalArgumentException.class, ()-> {
			dao.verifyClientLogin("", "");
		});
	}
	@Test
	void testVerifyClientLoginThrowsErrorIfUserDoesNotExist() {
		Assertions.assertThrows(ClientNotFoundException.class, ()-> {
			dao.verifyClientLogin("testDoesnotExist@gmail.com", "abc123");
		});
	}
	@Test
	void testVerifyClientLoginReturnsFalseIfWrongPasswordIsGiven() throws SQLException {
		client = dao.getClientById("123abc");
		
		Assertions.assertFalse(
				dao.verifyClientLogin("jd@gmail.com", "abc123")
		);
	}
	@Test
	void testVerifyClientLoginReturnsTrueIfRightPasswordIsGiven() throws SQLException {
		client = dao.getClientById("123abc");
		System.out.println(client.getPassword());
		Assertions.assertTrue(
				dao.verifyClientLogin("jd@gmail.com", "jd123")
		);
	}

}
