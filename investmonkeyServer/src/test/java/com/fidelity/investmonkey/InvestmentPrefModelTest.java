package com.fidelity.investmonkey;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.investmonkey.exception.NegativeValueException;
import com.fidelity.investmonkey.exception.NullException;
import com.fidelity.investmonkey.exception.ZeroValueException;
import com.fidelity.investmonkey.models.InvestmentPrefModel;


public class InvestmentPrefModelTest {
	
	
	@BeforeEach
	public void setUp() throws Exception {
		InvestmentPrefModel investmentPrefModel = new InvestmentPrefModel("100", "Education", "0 to 20,000", "Average", 10, true); 
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateInstance() {
		InvestmentPrefModel investmentPrefModel = new InvestmentPrefModel("100", "Education", "0 to 20,000", "Average", 10, true); 
		Assertions.assertNotNull(investmentPrefModel);
	}
	
	@Test
	public void testTermsAndCondition() {
		Exception exception=Assertions.assertThrows(RuntimeException.class,()->{
			InvestmentPrefModel investmentPrefModel = new InvestmentPrefModel("100", "Education", "0 to 20,000", "Average", 10, false); 
		});
		Assertions.assertTrue(exception.getMessage().contains("Please Accept the terms and conditions before Proceeding"));
	}
	
	@Test
	public void testNullClientId() {
		Exception exception=Assertions.assertThrows(NullException.class,()->{
			InvestmentPrefModel investmentPrefModel = new InvestmentPrefModel("", "Education", "0 to 20,000", "Average", 10, true); 
		});
		Assertions.assertTrue(exception.getMessage().contains("ClientId cannot be empty"));
	}
	
	@Test
	public void testNullPurpose() {
		Exception exception=Assertions.assertThrows(NullException.class,()->{
			InvestmentPrefModel investmentPrefModel = new InvestmentPrefModel("100", "", "0 to 20,000", "Average", 10, true); 
		});
		Assertions.assertTrue(exception.getMessage().contains("Investment Purpose cannot be empty"));
	}
	
	@Test
	public void testNullIncomeCategory() {
		Exception exception=Assertions.assertThrows(NullException.class,()->{
			InvestmentPrefModel investmentPrefModel = new InvestmentPrefModel("100", "Education", "", "Average", 10, true); 
		});
		Assertions.assertTrue(exception.getMessage().contains("Income Category cannot be empty"));
	}
	
	@Test
	public void testNullRiskTolerance() {
		Exception exception=Assertions.assertThrows(NullException.class,()->{
			InvestmentPrefModel investmentPrefModel = new InvestmentPrefModel("100", "Education", "0 to 20,000", "", 10, true); 
		});
		Assertions.assertTrue(exception.getMessage().contains("Risk Tolerance cannot be empty"));
	}
	
	@Test
	public void testZeroLengthOfInvestment() {
		Exception exception=Assertions.assertThrows(ZeroValueException.class,()->{
			InvestmentPrefModel investmentPrefModel = new InvestmentPrefModel("100", "Education", "0 to 20,000", "Average", 0, true); 
		});
		Assertions.assertTrue(exception.getMessage().contains("Length of investment cannot be empty"));
	}
	
	@Test
	public void testNegativeLengthOfInvestment() {
		Exception exception=Assertions.assertThrows(NegativeValueException.class,()->{
			InvestmentPrefModel investmentPrefModel = new InvestmentPrefModel("100", "Education", "0 to 20,000", "Average", -10, true); 
		});
		Assertions.assertTrue(exception.getMessage().contains("Length of investment cannot be negative"));
	}

}
