package com.fidelity.investmonkey.models;

import java.util.Objects;

public class PasswordUpdateRequest {
	
	private String clientId;
	private String oldPassword;
	private String newPassword;
	public PasswordUpdateRequest(String clientId, String oldPassword, String newPassword) {
		super();
		this.clientId = clientId;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
	public PasswordUpdateRequest() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(clientId, newPassword, oldPassword);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PasswordUpdateRequest other = (PasswordUpdateRequest) obj;
		return Objects.equals(clientId, other.clientId) && Objects.equals(newPassword, other.newPassword)
				&& Objects.equals(oldPassword, other.oldPassword);
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	@Override
	public String toString() {
		return "PasswordUpdateRequest [clientId=" + clientId + ", oldPassword=" + oldPassword + ", newPassword="
				+ newPassword + "]";
	}
	
	

}
