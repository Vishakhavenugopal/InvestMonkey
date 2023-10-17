package com.fidelity.investmonkey.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.integration.DatabaseException;

class InvestmentPreferenceTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCreateInvestmentPreference()
	{
		InvestmentPreference pref = new InvestmentPreference("125abc","Foreign Trip",IncomeCategory.CATEGORY_10001_250000,
				RiskTolerance.LOW,5);
	}
	
	@Test
	void testCreateInvestmentPreferenceNullClientId()
	{
		Assertions.assertThrows(NullPointerException.class,()->{
		InvestmentPreference pref = new InvestmentPreference("","Foreign Trip",IncomeCategory.CATEGORY_10001_250000,
				RiskTolerance.LOW,5);
		});
	}
	
	@Test
	void testCreateInvestmentPreferenceNullPurpose()
	{
		Assertions.assertThrows(NullPointerException.class,()->{
		InvestmentPreference pref = new InvestmentPreference("125abc","",IncomeCategory.CATEGORY_10001_250000,
				RiskTolerance.LOW,5);
		});
	}
	
	@Test
	void testCreateInvestmentPreferenceNullIncomeCategory()
	{
		Assertions.assertThrows(NullPointerException.class,()->{
		InvestmentPreference pref = new InvestmentPreference("125abc","Foreign Trip",null,
				RiskTolerance.LOW,5);
		});
	}
	
	@Test
	void testCreateInvestmentPreferenceNullRiskTolerance()
	{
		Assertions.assertThrows(NullPointerException.class,()->{
		InvestmentPreference pref = new InvestmentPreference("125abc","Foreign Trip",IncomeCategory.CATEGORY_10001_250000,
				null,5);
		});
	}
	
	@Test
	void testCreateInvestmentPreferenceNegativeLength()
	{
		Assertions.assertThrows(IllegalArgumentException.class,()->{
		InvestmentPreference pref = new InvestmentPreference("125abc","Foreign Trip",IncomeCategory.CATEGORY_10001_250000,
				RiskTolerance.LOW,-5);
		});
	}
	
	@Test
	void testCreateInvestmentPreferenceZeroLength()
	{
		Assertions.assertThrows(IllegalArgumentException.class,()->{
		InvestmentPreference pref = new InvestmentPreference("125abc","Foreign Trip",IncomeCategory.CATEGORY_10001_250000,
				RiskTolerance.LOW,0);
		});
	}
	

}
