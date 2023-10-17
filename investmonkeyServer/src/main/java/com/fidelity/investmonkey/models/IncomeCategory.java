package com.fidelity.investmonkey.models;

public enum IncomeCategory {
    CATEGORY_1_10000("1-10000"),
    CATEGORY_10001_250000("10001-250000"),
    CATEGORY_250001_4000000("250001-4000000"),
    CATEGORY_ABOVE_4000000(">4000000");

    private final String value;
    
    

    private IncomeCategory() {
		this.value = "";
	}

	IncomeCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

