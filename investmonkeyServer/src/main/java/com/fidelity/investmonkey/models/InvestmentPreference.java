package com.fidelity.investmonkey.models;


public class InvestmentPreference {
    private String clientId;
    private String investmentPurpose;
    private IncomeCategory incomeCategory;
    private RiskTolerance riskTolerance;
    private int lengthOfInvestment;

    
    public InvestmentPreference(String clientId, String investmentPurpose, IncomeCategory incomeCategory,
                                RiskTolerance riskTolerance, int lengthOfInvestment) {
    	super();
    	
    	if(clientId==null || clientId=="")
    	{
    		throw new NullPointerException("ClientId cannot be Null or empty");
    	}
    	if(investmentPurpose==null || investmentPurpose=="")
    	{
    		throw new NullPointerException("Investment purpose cannot be null or empty");
    	}
    	
    	if(incomeCategory==null)
    	{
    		throw new NullPointerException("Income category cannot be null");
    	}
    	if(riskTolerance==null)
    	{
    		throw new NullPointerException("Risk Tolerance cannot be null");
    	}
    	
    	if(lengthOfInvestment<=0)
    	{
    		throw new IllegalArgumentException("Investment length must be atleast 1 year");
    	}
    	
        this.clientId = clientId;
        this.investmentPurpose = investmentPurpose;
        this.incomeCategory = incomeCategory;
        this.riskTolerance = riskTolerance;
        this.lengthOfInvestment = lengthOfInvestment;
    }
    
    

    public InvestmentPreference() {
		super();
	}




    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getInvestmentPurpose() {
        return investmentPurpose;
    }

    public void setInvestmentPurpose(String investmentPurpose) {
        this.investmentPurpose = investmentPurpose;
    }

    public IncomeCategory getIncomeCategory() {
        return incomeCategory;
    }

    public void setIncomeCategory(IncomeCategory incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    public RiskTolerance getRiskTolerance() {
        return riskTolerance;
    }

    public void setRiskTolerance(RiskTolerance riskTolerance) {
        this.riskTolerance = riskTolerance;
    }

    public int getLengthOfInvestment() {
        return lengthOfInvestment;
    }

    public void setLengthOfInvestment(int lengthOfInvestment) {
        this.lengthOfInvestment = lengthOfInvestment;
    }

    @Override
    public String toString() {
        return "InvestmentPreference{" +
                "clientId='" + clientId + '\'' +
                ", investmentPurpose='" + investmentPurpose + '\'' +
                ", incomeCategory=" + incomeCategory +
                ", riskTolerance=" + riskTolerance +
                ", lengthOfInvestment=" + lengthOfInvestment +
                '}';
    }
}
