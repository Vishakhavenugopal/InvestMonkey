package com.fidelity.investmonkey.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.integration.DatabaseException;

@SpringBootTest
@Transactional
class BalanceServiceTest {

	@Autowired
	private BalanceService balanceService;

	
	
	@Test
	public void testGetClientBalanceSuccess()
	{
		assertEquals(balanceService.getClientBalance("456abc"),1000000);
	}
	
	@Test
	public void testGetClientBalanceInvalidClient()
	{
		assertThrows(DatabaseException.class, () -> {
		balanceService.getClientBalance("173xyz");
		});
	}
//	
	@Test
	public void testUpdateClientBalanceBuy()
	{
		Assertions.assertEquals(balanceService.updateClientBalance("456abc", "B", 100000),true);

	}
	
	
	@Test
	public void testUpdateClientBalanceBuyAmountNegative()
	{
		
		assertThrows(IllegalArgumentException.class, () -> {
		balanceService.updateClientBalance("123abc", "B", -100);
		});
	}
	
	@Test
	public void testUpdateClientBalanceSell()
	{
		Assertions.assertEquals(balanceService.updateClientBalance("456abc", "S", 100000),true);
	}
	

	@Test
	public void testUpdateClientBalanceSellAmountNegative()
	{
		assertThrows(IllegalArgumentException.class, () -> {
		balanceService.updateClientBalance("123abc", "S", -100);
		});
	}
}

