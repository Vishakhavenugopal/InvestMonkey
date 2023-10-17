package com.fidelity.investmonkey.models;

import java.util.Objects;

public class ClientInfo {
	
	private String email;
	private String clientId;
	private String token;
	public ClientInfo(String email, String clientId, String token) {
		super();
		this.email = email;
		this.clientId = clientId;
		this.token = token;
	}
	public ClientInfo() {
		super();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public int hashCode() {
		return Objects.hash(clientId, email, token);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientInfo other = (ClientInfo) obj;
		return Objects.equals(clientId, other.clientId) && Objects.equals(email, other.email)
				&& Objects.equals(token, other.token);
	}
	@Override
	public String toString() {
		return "ClientInfo [email=" + email + ", clientId=" + clientId + ", token=" + token + "]";
	}
	
	
	

}
