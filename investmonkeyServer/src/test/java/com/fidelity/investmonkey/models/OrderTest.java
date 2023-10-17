package com.fidelity.investmonkey.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.investmonkey.exception.TradeException;

class OrderTest {
	Order order;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCreateOrderSuccess()
	{
		order = new Order("N123456",100,104.75,"B","123abc","123xyz");
		assertEquals("N123456",order.getInstrumentId());
	}
	
	@Test
	void testCreateOrderNullInstrumentId()
	{
		assertThrows(IllegalArgumentException.class, () -> {
		order = new Order(null,100,104.75,"B","123abc","123xyz");
		});
	}
	
	@Test
	void testCreateOrderNegativeQuantity()
	{
		assertThrows(IllegalArgumentException.class, () -> {
			order = new Order("N123456",-100,104.75,"B","123abc","123xyz");
		});
	}
	
	@Test
	void testCreateOrderZeroQuantity()
	{
		assertThrows(IllegalArgumentException.class, () -> {
			order = new Order("N123456",0,104.75,"B","123abc","123xyz");
		});
	}

}
