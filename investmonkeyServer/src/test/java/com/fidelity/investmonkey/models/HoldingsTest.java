package com.fidelity.investmonkey.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HoldingsTest {

	private Holdings holding;
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCreateHolding()
	{
		holding = new Holdings("A6758NQ","JP Morgan and Chase Bank","STOCK",104.75,10,1047.5);
		assertEquals("A6758NQ",holding.getInstrumentId());
	}
	
	@Test
	void testCreateHoldingNullInstrumentId()
	{
		assertThrows(IllegalArgumentException.class, () -> {
		holding = new Holdings(null,"JP Morgan and Chase Bank","STOCK",104.75,10,1047.5);
		});
	}
	
	@Test
	void testCreateHoldingNegativeQuantity()
	{
		assertThrows(IllegalArgumentException.class, () -> {
		holding = new Holdings("A6758NQ","JP Morgan and Chase Bank","STOCK",104.75,-10,1047.5);
		});
	}

}
