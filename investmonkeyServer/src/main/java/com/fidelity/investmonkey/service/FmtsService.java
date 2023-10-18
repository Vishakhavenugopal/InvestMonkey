package com.fidelity.investmonkey.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fidelity.investmonkey.models.ClientInfo;
import com.fidelity.investmonkey.models.OrderInfo;
import com.fidelity.investmonkey.models.Price;
import com.fidelity.investmonkey.models.TradeInfo;

@Service
public class FmtsService {
	
	 
	    private String fmtsApiUrl="https://a721308.roifmr.com";
	    
	    @Autowired
	    private  RestTemplate restTemplate ;
	    
//	    public FmtsService(RestTemplateBuilder restTemplateBuilder) {
//			this.restTemplate = restTemplateBuilder.build();
//			List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//			MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//			converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
//			messageConverters.add(converter);
//			this.restTemplate.setMessageConverters(messageConverters);
//		}

	    public FmtsService() {
			super();
		}



		public List<Price> getTradesPrices() {
			
			String apiUrl = fmtsApiUrl + "/fmts/trades/prices";

	        // Make the GET request and retrieve the response
	        String jsonResponse = restTemplate.getForObject(apiUrl, String.class);

	        // Deserialize the JSON response into a list of Price objects
	        ObjectMapper objectMapper = new ObjectMapper();
	        try {
	            return objectMapper.readValue(jsonResponse, new TypeReference<List<Price>>() {});
	        } catch (IOException e) {
	            throw new RuntimeException("Failed to deserialize response: " + e.getMessage());
	        }
	    
	    }
		
		public Price getTradesPricesByInstrument(String instrumentId) 
		{	
			System.out.println(instrumentId);
	        String apiUrl = fmtsApiUrl + "/fmts/trades/prices";
	        List<Price> pricesArray = getTradesPrices();
	        for(Price price:pricesArray)
	        {
	        	System.out.println(price.getInstrument().getInstrumentId());
	        	if(price.getInstrument().getInstrumentId().equals(instrumentId))
	        	{
	        		return price;
	        	}
	        }
	        return null;
	    }
	    
	    public String getClientIdFromFmts(ClientInfo clientInfo) {
	    	
	    	   String apiUrl = fmtsApiUrl + "/fmts/client";

	    	   
	    	   ClientInfo info = new ClientInfo(clientInfo.getEmail(),"","");
	           HttpHeaders headers = new HttpHeaders();
	           headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	           HttpEntity<ClientInfo> requestEntity = new HttpEntity<>(info, headers);

	    

	           ResponseEntity<String> responseEntity =
	                   restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

	    

	           if (responseEntity.getStatusCode() == HttpStatus.OK) {
	               String responseBody = responseEntity.getBody();
	               ObjectMapper objectMapper = new ObjectMapper();
	               try {
	            	   ClientInfo clientRequest = objectMapper.readValue(responseBody, ClientInfo.class);
	                   System.out.println(clientRequest);
	                   return clientRequest.getClientId();
	               } catch (IOException e) {
	                   throw new RuntimeException("Failed to deserialize response: " + e.getMessage());
	               }
	           } else {
	               throw new RuntimeException("Failed to get client token. HTTP status code: " + responseEntity.getStatusCode());
	           }
	    }
	    
	    public String getTokenFromFmts(ClientInfo clientInfo) {
	    	
	    	   String apiUrl = fmtsApiUrl + "/fmts/client";

	    	   
	    	   ClientInfo info = new ClientInfo(clientInfo.getEmail(),"","");
	           HttpHeaders headers = new HttpHeaders();
	           headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	           HttpEntity<ClientInfo> requestEntity = new HttpEntity<>(info, headers);

	    

	           ResponseEntity<String> responseEntity =
	                   restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

	    

	           if (responseEntity.getStatusCode() == HttpStatus.OK) {
	               String responseBody = responseEntity.getBody();
	               ObjectMapper objectMapper = new ObjectMapper();
	               try {
	            	   ClientInfo clientRequest = objectMapper.readValue(responseBody, ClientInfo.class);
	                   System.out.println(clientRequest);
	                   return clientRequest.getToken();
	               } catch (IOException e) {
	                   throw new RuntimeException("Failed to deserialize response: " + e.getMessage());
	               }
	           } else {
	               throw new RuntimeException("Failed to get client token. HTTP status code: " + responseEntity.getStatusCode());
	           }
	    }
	    
	    public TradeInfo executeTrade(OrderInfo orderInfo) {
	    	
	    	   String apiUrl = fmtsApiUrl + "/fmts/trades/trade";

	    	   
	    	  
	           HttpHeaders headers = new HttpHeaders();
	           headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	           HttpEntity<OrderInfo> requestEntity = new HttpEntity<>(orderInfo, headers);

	    

	           ResponseEntity<String> responseEntity =
	                   restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

	    

	           if (responseEntity.getStatusCode() == HttpStatus.OK) {
	               String responseBody = responseEntity.getBody();
	               ObjectMapper objectMapper = new ObjectMapper();
	               try {
	            	   TradeInfo clientRequest = objectMapper.readValue(responseBody, TradeInfo.class);
	                   System.out.println(clientRequest);
	                   return clientRequest;
	               } catch (IOException e) {
	                   throw new RuntimeException("Failed to deserialize response: " + e.getMessage());
	               }
	           } else {
	               throw new RuntimeException("Failed to get client token. HTTP status code: " + responseEntity.getStatusCode());
	           }
	    }
	    

}
