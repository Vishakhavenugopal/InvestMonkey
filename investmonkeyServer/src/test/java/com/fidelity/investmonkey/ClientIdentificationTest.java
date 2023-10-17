package com.fidelity.investmonkey;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.investmonkey.exception.InvalidIdentificationType;
import com.fidelity.investmonkey.models.ClientIdentification;

public class ClientIdentificationTest {

	@BeforeEach
	public void setUp() throws Exception {
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void createValidClientIdentifiction() {
		ClientIdentification identification=new ClientIdentification("Aadhar","123456789");
		Assertions.assertNotNull(identification);
	}
	@Test
	public void createInvalidClientIdentificationType() {
		Assertions.assertThrows(InvalidIdentificationType.class,()->{
			ClientIdentification identification=new ClientIdentification("Pan","123456789");
		});
		
	}
	@Test
	public void createInvalidClientIdentificationValue() {
		Assertions.assertThrows(NullPointerException.class,()->{
			ClientIdentification identification=new ClientIdentification("Aadhar",null);
		});
		
	}
}
