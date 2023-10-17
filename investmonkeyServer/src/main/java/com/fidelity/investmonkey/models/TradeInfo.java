package com.fidelity.investmonkey.models;

import java.util.Objects;

public class TradeInfo {
	
	private String instrumentId;
	private int quantity;
	private double executionPrice;
	private String direction;
	private String clientId;
	private OrderInfo order;
	private String tradeId;
	private double cashValue;
	public TradeInfo(String instrumentId, int quantity, double executionPrice, String direction, String clientId,
			OrderInfo order, String tradeId, double cashValue) {
		super();
		this.instrumentId = instrumentId;
		this.quantity = quantity;
		this.executionPrice = executionPrice;
		this.direction = direction;
		this.clientId = clientId;
		this.order = order;
		this.tradeId = tradeId;
		this.cashValue = cashValue;
	}
	public TradeInfo() {
		super();
	}
	public String getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getExecutionPrice() {
		return executionPrice;
	}
	public void setExecutionPrice(double executionPrice) {
		this.executionPrice = executionPrice;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public OrderInfo getOrder() {
		return order;
	}
	public void setOrder(OrderInfo order) {
		this.order = order;
	}
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public double getCashValue() {
		return cashValue;
	}
	public void setCashValue(double cashValue) {
		this.cashValue = cashValue;
	}
	@Override
	public int hashCode() {
		return Objects.hash(cashValue, clientId, direction, executionPrice, instrumentId, order, quantity, tradeId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradeInfo other = (TradeInfo) obj;
		return Double.doubleToLongBits(cashValue) == Double.doubleToLongBits(other.cashValue)
				&& Objects.equals(clientId, other.clientId) && Objects.equals(direction, other.direction)
				&& Double.doubleToLongBits(executionPrice) == Double.doubleToLongBits(other.executionPrice)
				&& Objects.equals(instrumentId, other.instrumentId) && Objects.equals(order, other.order)
				&& quantity == other.quantity && Objects.equals(tradeId, other.tradeId);
	}
	@Override
	public String toString() {
		return "TradeInfo [instrumentId=" + instrumentId + ", quantity=" + quantity + ", executionPrice="
				+ executionPrice + ", direction=" + direction + ", clientId=" + clientId + ", order=" + order
				+ ", tradeId=" + tradeId + ", cashValue=" + cashValue + "]";
	}
	
	
	

}
