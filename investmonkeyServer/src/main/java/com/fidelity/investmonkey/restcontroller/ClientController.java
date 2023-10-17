package com.fidelity.investmonkey.restcontroller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;

import com.fidelity.investmonkey.models.Balance;
import com.fidelity.investmonkey.models.Client;
import com.fidelity.investmonkey.models.Holdings;
import com.fidelity.investmonkey.models.InvestmentPreference;
import com.fidelity.investmonkey.models.LoginRequest;
import com.fidelity.investmonkey.models.Order;
import com.fidelity.investmonkey.models.PasswordUpdateRequest;
import com.fidelity.investmonkey.models.Price;
import com.fidelity.investmonkey.models.Trade;
import com.fidelity.investmonkey.service.AddInvestmentPreferance;
import com.fidelity.investmonkey.service.BalanceService;
import com.fidelity.investmonkey.service.ClientService;
import com.fidelity.investmonkey.service.FmtsService;
import com.fidelity.investmonkey.service.PortfolioService;
import com.fidelity.investmonkey.service.RoboAdvisorService;
import com.fidelity.investmonkey.service.TradeService;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins="*")
public class ClientController {
	
	@Autowired
	private ClientService service;
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private FmtsService fmtsService;
	
	@Autowired
	private AddInvestmentPreferance prefService;
	
	@Autowired
	private PortfolioService portfolioService;
	
	@Autowired
	private RoboAdvisorService roboService;
	
	@Autowired
	private BalanceService balanceService;
	@GetMapping("/prices")
	public ResponseEntity<List<Price>> getAllPrices()
	{
		try
		{
			List<Price> prices = fmtsService.getTradesPrices();
			
			ResponseEntity<List<Price>> response;
			
			if(prices.size()>0)
			{
				response = ResponseEntity.ok(prices);
			}
			else {
				response = ResponseEntity.noContent().build();
			}
			return response;
		}
		catch(Exception e)
		{
			throw new ServerErrorException("Backend issue", e);
		}
	}
	
	@GetMapping("/{clientId}")
	public ResponseEntity<Client> getClientById(@PathVariable String clientId)
	{
		if (clientId == null || clientId == "" ) {
			
			return ResponseEntity.badRequest().build();
		}
		try {
			Client client = service.getClientById(clientId);
			
			
			ResponseEntity<Client> responseEntity;
			
			if (client != null) {
				responseEntity = ResponseEntity.ok(client); 
			}
			else {
				responseEntity = ResponseEntity.noContent().build();
			}
			return responseEntity;
		} 
		catch (Exception e) {
			throw new ServerErrorException("Backend issue", e);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<Client> loginClient(@RequestBody LoginRequest request)
	{
		if(request==null)
		{
			return ResponseEntity.badRequest().build();
		}
		try {
			boolean res = service.loginClient(request.getEmail(), request.getPassword());
			ResponseEntity<Client> response;
			if(res!=false)
			{
				response = ResponseEntity.ok(service.getClientByEmail(request.getEmail())); 
			}
			else {
				response = ResponseEntity.badRequest().build();
			}
			return response;
		}
		catch (Exception e) {
			throw new ServerErrorException("Backend issue", e);
		}
	}
	
	@GetMapping("/holdings/{clientId}")
	public ResponseEntity<List<Holdings>> getHoldingsById(@PathVariable String clientId)
	{
		if (clientId == null || clientId == "" ) {
			
			return ResponseEntity.badRequest().build();
		}
		try {
			List<Holdings> holdings = portfolioService.getPortfolio(clientId);
			
			
			ResponseEntity<List<Holdings>> responseEntity;
			
			if (holdings != null) {
				responseEntity = ResponseEntity.ok(holdings); 
			}
			else {
				responseEntity = ResponseEntity.noContent().build();
			}
			return responseEntity;
		} 
		catch (Exception e) {
			throw new ServerErrorException("Backend issue", e);
		}
	}
	
	@GetMapping("/pref/{clientId}")
	public ResponseEntity<InvestmentPreference> getPrefsById(@PathVariable String clientId)
	{
		
		if (clientId == null || clientId == "" ) {
			
			return ResponseEntity.badRequest().build();
		}
		try {
		InvestmentPreference preferences = prefService.getInvestmentPref(clientId);
			
			
			ResponseEntity<InvestmentPreference> responseEntity;
			
			if (preferences != null) {
				responseEntity = ResponseEntity.ok(preferences); 
			}
			else {
				responseEntity = ResponseEntity.noContent().build();
			}
			return responseEntity;
		} 
		catch (Exception e) {
			throw new ServerErrorException("Backend issue", e);
		}
	}
	
	
	
	@PostMapping("/register")
	public ResponseEntity<Client> registerClient(@RequestBody Client client)
	{
		System.out.println("DATA RECEIVED FROM ANGULAR => "+client);
		if(client==null)
		{
			return ResponseEntity.badRequest().build();
		}
		
		try {
			Client res = service.addNewClient(client);
			
			
			ResponseEntity<Client> responseEntity;
			
			if (res != null) {
				responseEntity = ResponseEntity.ok(res); 
			}
			else {
				responseEntity = ResponseEntity.noContent().build();
			}
			return responseEntity;
		} 
		catch (Exception e) {
			System.out.print(e.toString());
			throw new ServerErrorException("Backend issue", e);
			
		}
	}
	
	@PostMapping("/pref")
public ResponseEntity<InvestmentPreference> addInvestmentPreference(@RequestBody InvestmentPreference pref)
{
	if(pref==null)
	{
		return ResponseEntity.badRequest().build();
	}
	
	try {
		InvestmentPreference res = prefService.addInvestmentPref(pref);
		
		
		ResponseEntity<InvestmentPreference> responseEntity;
		
		if (res != null) {
			responseEntity = ResponseEntity.ok(res); 
		}
		else {
			responseEntity = ResponseEntity.badRequest().build();
		}
		return responseEntity;
	} 
	catch (Exception e) {
		System.out.print(e.toString());
		throw new ServerErrorException("Backend issue", e);
		
	}
}
	
	@PutMapping("/pref")
	public ResponseEntity<InvestmentPreference> updateInvestmentPreference(@RequestBody InvestmentPreference pref)
	{
		if(pref==null)
		{
			throw new ServerWebInputException("Preferences must not be Null or Empty");
		}
		
		try {
			InvestmentPreference res = prefService.updateInvestmentPref(pref);
			
			
			ResponseEntity<InvestmentPreference> responseEntity;
			
			if (res != null) {
				responseEntity = ResponseEntity.ok(res); 
			}
			else {
				responseEntity = ResponseEntity.badRequest().build();
			}
			return responseEntity;
		} 
		catch (Exception e) {
			System.out.print(e.toString());
			throw new ServerErrorException("Backend issue", e);
			
		}
	}
	
	@PutMapping("/credentials")
	public ResponseEntity<PasswordUpdateRequest> updatePassword(@RequestBody PasswordUpdateRequest request)
	{
		if(request==null)
		{
			throw new ServerWebInputException("Update request must not be Null or Empty");
		}
		
		try {
			boolean res = service.updateClientPassword(request.getClientId(),request.getOldPassword(),request.getNewPassword());
			
			
			ResponseEntity<PasswordUpdateRequest> responseEntity;
			
			if (res != false) {
				responseEntity = ResponseEntity.ok(request); 
			}
			else {
				responseEntity = ResponseEntity.badRequest().build();
			}
			return responseEntity;
		} 
		catch (Exception e) {
			System.out.print(e.toString());
			throw new ServerErrorException("Backend issue", e);
			
		}
	}
	
@PostMapping("/trade")
public ResponseEntity<Trade> executeTrade(@RequestBody Order order)
{
	if(order==null)
	{
		throw new ServerWebInputException("Client must not be Null or Empty");
	}
	System.out.println(order.toString());
	
	try {
		Price price = fmtsService.getTradesPricesByInstrument(order.getInstrumentId());
		System.out.println(price);
		Trade res = tradeService.generateTrade(order,price);
		
		
		ResponseEntity<Trade> responseEntity;
		
		if (res != null) {
			responseEntity = ResponseEntity.ok(res); 
		}
		else {
			responseEntity = ResponseEntity.noContent().build();
		}
		return responseEntity;
	} 
	catch (Exception e) {
		System.out.print(e.toString());
		throw new ServerErrorException("Backend issue", e);
		
	}
}
@GetMapping("/clientBalance/{clientId}")
public ResponseEntity<Double> getClientBalance(@PathVariable String clientId){
	System.out.println("get balance controller");
	double balance=balanceService.getClientBalance(clientId);
	return ResponseEntity.ok(balance);
	
}
@GetMapping("/roboAdvisor/{clientId}")
public ResponseEntity<List<Price>> getPriceFromRoboAdvisor(@PathVariable String clientId){
	System.out.print("HEREEEEEEEEEEEEEEEE");
	List<Price> prices=roboService.getRoboAdviseBuy(clientId);
	System.out.println("PRICES => "+prices.toString());
	ResponseEntity<List<Price>> response=ResponseEntity.ok(prices);
	
	return response;
}

@GetMapping("/tradeHistory/{clientId}")
public ResponseEntity<List<Trade>> getClientTradeHistory(@PathVariable String clientId)
{
	List<Trade> tradeHistory = portfolioService.getTradeHistoryOfClient(clientId);
	ResponseEntity<List<Trade>> response = ResponseEntity.ok(tradeHistory);
	
	return response;
}

}
