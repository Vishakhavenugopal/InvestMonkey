package com.fidelity.investmonkey.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fidelity.integration.ClientDao;
import com.fidelity.investmonkey.exception.ClientNotFoundException;
import com.fidelity.investmonkey.models.InvestmentPreference;
import com.fidelity.investmonkey.models.Price;
@Service
public class RoboAdvisorService {
	@Autowired
	private ClientDao dao;
	@Autowired
	private FmtsService fmts;
	private final List<String> category1=List.of("N123456","N123789","Q123","T67878","T67883");
	private final List<String> category2=List.of("Q456","T67883","C100","T67878");
	private final List<String> category3=List.of("T67880","T67899","T67897","T67894","Q123");
	
	
	
	public List<Price> getRoboAdviseBuy(String clientId) {
		List<Price> allPrices=fmts.getTradesPrices();
		List<Price> advisedPrice=new ArrayList<>();
		if(clientId.isEmpty()) {
			throw new IllegalArgumentException("Client id cannot be empty");
		}
		InvestmentPreference preference=dao.getInvestmentPreferenceOfClient(clientId);
		if(preference==null) {
			throw new ClientNotFoundException("Cannot find preference");
		}
		int score=this.getScore(preference);
		if(score<=10) {
			for(Price price:allPrices) {
				int index=category3.indexOf(price.getInstrument().getInstrumentId());
				if(index!=-1) {
					advisedPrice.add(price);
				}
			}
		}
		if(score>10 && score<25) {
			
				for(Price price:allPrices) {
					int index=category2.indexOf(price.getInstrument().getInstrumentId());
					if(index!=-1) {
						advisedPrice.add(price);
					}
				
		}
		}
		if(score>=25) {
			for(Price price:allPrices) {
				int index=category1.indexOf(price.getInstrument().getInstrumentId());
				if(index!=-1) {
					advisedPrice.add(price);
				}
		}
		}
		return advisedPrice;
	}

	private int getScore(InvestmentPreference preference) {
		HashMap<String,Integer> riskToleranceScores=new HashMap<>();
		riskToleranceScores.put("Low", 3);
		riskToleranceScores.put("Medium", 5);
		riskToleranceScores.put("High", 10);
		int score=0;
		if(preference.getIncomeCategory().getValue()=="1-10000") {
			score+=1;
		}
		if(preference.getIncomeCategory().getValue()=="10001-250000") {
			score+=3;
		}
		if(preference.getIncomeCategory().getValue()=="250001-4000000") {
			score+=7;
		}
		if(preference.getIncomeCategory().getValue()==">4000000") {
			score+=10;
		}
		if(preference.getLengthOfInvestment()<5) {
			score+=3;
		}
		if(preference.getLengthOfInvestment()>=5 && preference.getLengthOfInvestment()<10) {
			score+=5;
		}
		else {
			score+=10;
		}
		score=score+riskToleranceScores.get(preference.getRiskTolerance().getValue());
		System.out.println("SCORE => "+score);
		return score;
		
		
	}
}
