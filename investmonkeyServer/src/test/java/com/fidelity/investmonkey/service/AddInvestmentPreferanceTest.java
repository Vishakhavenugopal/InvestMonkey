package com.fidelity.investmonkey.service;

 

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.integration.ClientDao;
import com.fidelity.investmonkey.models.IncomeCategory;
import com.fidelity.investmonkey.models.InvestmentPreference;
import com.fidelity.investmonkey.models.RiskTolerance;

 

@SpringBootTest
@Transactional
public class AddInvestmentPreferanceTest {

	@Autowired	
	private AddInvestmentPreferance service;
	@Autowired
	private ClientDao dao;
	private InvestmentPreference pref1, pref2, pref3, pref4;
	@BeforeEach
	void setUp() throws Exception {
		pref1 = new InvestmentPreference("123abc", "Retirement", IncomeCategory.CATEGORY_1_10000, RiskTolerance.LOW, 10);
		pref2 = new InvestmentPreference("456abc", "Retirement", IncomeCategory.CATEGORY_10001_250000, RiskTolerance.MEDIUM, 15);
		pref3 = new InvestmentPreference("456abc", "Grow Wealth", IncomeCategory.CATEGORY_250001_4000000, RiskTolerance.MEDIUM, 20);
		pref4 = new InvestmentPreference("111pqr", "Grow Wealth", IncomeCategory.CATEGORY_250001_4000000, RiskTolerance.MEDIUM, 20);
	}

 

	@AfterEach
	void tearDown() throws Exception {
		pref1 = null;
		pref2 = null;
		pref3 = null;
	}

	@Test
	void testGetInvestmentPreferance() {
		dao.getInvestmentPreferenceOfClient("456abc");
	}

	@Test
	void testMultipleInvestmentPreference() {
		Exception exception=assertThrows(RuntimeException.class,()->{
			service.addInvestmentPref(pref2);
		});
	}



	@Test
	void testUpdateInvestmentPreference() {
		service.updateInvestmentPref(pref3);
	}

	@Test
	void testInvalidClientUpdateInvestmentPreference() {
		Exception exception=assertThrows(RuntimeException.class,()->{
			service.updateInvestmentPref(pref4);
		});
	}

}