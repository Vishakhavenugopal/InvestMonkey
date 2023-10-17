package com.fidelity.investmonkey.models;

 

import com.fidelity.investmonkey.exception.*;
public class ClientIdentification {
	private String clientIdentificationType;
	private String clientIdentificationValue;


	public ClientIdentification() {
		super();
	}
	public ClientIdentification(String clientIdentificationType, String clientIdentificationValue) {
		super();
		if(!clientIdentificationType.equals("Aadhar") && !clientIdentificationType.equals("SSN") && !clientIdentificationType.equals("aadhar") ) {
			throw new InvalidIdentificationType("Identification type should be aadhar or SSN");
		}
		if(clientIdentificationValue==null) {
			throw new NullPointerException("Client Idnetification Value Cannot be null");
		}
		this.clientIdentificationType = clientIdentificationType;
		this.clientIdentificationValue = clientIdentificationValue;
	}
	public String getClientIdentificationType() {
		return clientIdentificationType;
	}
	public void setClientIdentificationType(String clientIdentificationType) {
		this.clientIdentificationType = clientIdentificationType;
	}
	public String getClientIdentificationValue() {
		return clientIdentificationValue;
	}
	public void setClientIdentificationValue(String clientIdentificationValue) {
		this.clientIdentificationValue = clientIdentificationValue;
	}

}