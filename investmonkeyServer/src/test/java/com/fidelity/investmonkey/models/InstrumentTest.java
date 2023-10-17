package com.fidelity.investmonkey.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.investmonkey.exception.TradeException;

class InstrumentTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCreateInstrumentSuccess()
	{
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		assertEquals(instrOne.getInstrumentId(),"N123456");
	}
	
	@Test
	void testCreateInstrumentNullInstrumentId() 
	{
		assertThrows(IllegalArgumentException.class, () -> {
		Instrument instrOne = new Instrument(null,"CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		
		});
	}
	
	@Test
	void testCreateInstrumentNegativeMaxQuantity() 
	{
		assertThrows(IllegalArgumentException.class, () -> {
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",-1000,1);
		
		});
	}
}
