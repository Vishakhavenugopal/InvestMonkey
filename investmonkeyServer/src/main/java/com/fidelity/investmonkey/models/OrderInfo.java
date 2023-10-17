package com.fidelity.investmonkey.models;

import java.util.Objects;

public class OrderInfo {
	
	private String orderId;
	private int quantity;
	private double targetPrice;
	private String direction;
	private String clientId;
	private String instrumentId;
	private String token;
	public OrderInfo(String orderId, int quantity, double targetPrice, String direction, String clientId,
			String instrumentId, String token) {
		super();
		this.orderId = orderId;
		this.quantity = quantity;
		this.targetPrice = targetPrice;
		this.direction = direction;
		this.clientId = clientId;
		this.instrumentId = instrumentId;
		this.token = token;
	}
	public OrderInfo(int quantity, double targetPrice, String direction, String clientId, String instrumentId,
			String token) {
		super();
		this.quantity = quantity;
		this.targetPrice = targetPrice;
		this.direction = direction;
		this.clientId = clientId;
		this.instrumentId = instrumentId;
		this.token = token;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getTargetPrice() {
		return targetPrice;
	}
	public void setTargetPrice(double targetPrice) {
		this.targetPrice = targetPrice;
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
	public String getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public int hashCode() {
		return Objects.hash(clientId, direction, instrumentId, orderId, quantity, targetPrice, token);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderInfo other = (OrderInfo) obj;
		return Objects.equals(clientId, other.clientId) && Objects.equals(direction, other.direction)
				&& Objects.equals(instrumentId, other.instrumentId) && Objects.equals(orderId, other.orderId)
				&& quantity == other.quantity
				&& Double.doubleToLongBits(targetPrice) == Double.doubleToLongBits(other.targetPrice)
				&& Objects.equals(token, other.token);
	}
	public OrderInfo() {
		super();
	}
	@Override
	public String toString() {
		return "OrderInfo [orderId=" + orderId + ", quantity=" + quantity + ", targetPrice=" + targetPrice
				+ ", direction=" + direction + ", clientId=" + clientId + ", instrumentId=" + instrumentId + ", token="
				+ token + "]";
	}

	
	
}
