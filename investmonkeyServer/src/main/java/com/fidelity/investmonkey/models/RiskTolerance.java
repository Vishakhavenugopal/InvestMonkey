package com.fidelity.investmonkey.models;

public enum RiskTolerance {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");

    private final String value;

    RiskTolerance(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

	private RiskTolerance() {
		this.value = "";
	}
}

