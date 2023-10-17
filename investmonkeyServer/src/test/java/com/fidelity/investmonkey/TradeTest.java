package com.fidelity.investmonkey;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.investmonkey.models.Instrument;
import com.fidelity.investmonkey.models.Order;
import com.fidelity.investmonkey.models.Price;
import com.fidelity.investmonkey.models.Trade;

class TradeTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void createValidTrade() {
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Order orderOne = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","123abc","123xyz");
		Trade trade=new Trade("aa123",10,104.75,"B",orderOne,1040.00,"ABC123","N123456");
		assertNotNull(trade);
		
		
	}
	@Test
	void createInvalidTrade() {
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		Order orderOne = new Order(instrOne.getInstrumentId(),10,priceOne.getBidPrice(),"B","123abc","123xyz");
		assertThrows(IllegalArgumentException.class,()->{
			Trade trade=new Trade("aa123",10,104.75,"B",null,1040.00,"ABC123","N123456");
			throw new IllegalArgumentException("Invalid Trade");
		});
	}
}
