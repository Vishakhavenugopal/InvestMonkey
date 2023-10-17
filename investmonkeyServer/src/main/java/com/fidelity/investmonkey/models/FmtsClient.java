package com.fidelity.investmonkey.models;

import java.util.Objects;

public class FmtsClient {
	private String email;
	private int clientId;
	private int token;
	public FmtsClient(String email, int clientId, int token) {
		super();
		this.email = email;
		this.clientId = clientId;
		this.token = token;
	}
	public FmtsClient() {
		super();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public int getToken() {
		return token;
	}
	public void setToken(int token) {
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
		FmtsClient other = (FmtsClient) obj;
		return clientId == other.clientId && Objects.equals(email, other.email) && token == other.token;
	}
	@Override
	public String toString() {
		return "FmtsClient [email=" + email + ", clientId=" + clientId + ", token=" + token + "]";
	}
	
	

}
