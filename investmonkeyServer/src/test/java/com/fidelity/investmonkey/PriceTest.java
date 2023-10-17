package com.fidelity.investmonkey;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fidelity.investmonkey.models.*;
class PriceTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void createPrice() {
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		Price priceOne = new Price(104.75,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		assertNotNull(priceOne);
	}
	@Test
	void createInvalidPrice() {
		Instrument instrOne = new Instrument("N123456","CUSIP","46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock",1000,1);
		assertThrows(IllegalArgumentException.class,()->{
			Price priceOne = new Price(0,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",instrOne);
		});
		
	}
	@Test
	void createPriceNullInstrument() {
		
		assertThrows(NullPointerException.class,()->{
			Price priceOne = new Price(104.5,104.25,"21-AUG-19 10.00.01.042000000 AM GMT",null);
		});
		
	}
	
}
