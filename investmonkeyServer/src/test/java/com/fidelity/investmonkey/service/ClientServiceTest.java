package com.fidelity.investmonkey.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.integration.ClientDao;
import com.fidelity.integration.DatabaseException;
import com.fidelity.investmonkey.exception.ClientNotFoundException;
import com.fidelity.investmonkey.models.Client;
import com.fidelity.investmonkey.models.ClientIdentification;
import com.fidelity.investmonkey.models.ClientInfo;


@SpringBootTest
@Transactional
class ClientServiceTest {

	 	@Mock
	    private ClientDao dao;

	    @Mock
	    private FmtsService fmtsService;

	    @Autowired
	    private ClientService service;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.initMocks(this);
	    }

    
	
	
	private ClientIdentification indianIdentification=new ClientIdentification("Aadhar","123456789");;
	private ClientIdentification usIdentification=new ClientIdentification("SSN","123456789");;
	private Client indianClient=new Client("jitin","dodeja","9663321676","123abc","jd@gmail.com",LocalDate.of(2001, 8, 27),"India","570011","123abc",indianIdentification);;
	private Client usClient=new Client("selmonBhai","dodeja","9663321676","xyz123","sbhai@gmail.com",LocalDate.of(2001, 8, 27),"India","570011","jd123",usIdentification);;
	
	
	@Test
	void addNullClient() {
		assertThrows(NullPointerException.class,()->{
			service.addNewClient(null);
		});
	}
	
	@Test
	void addDuplicateClient() throws SQLException{
		assertThrows(DuplicateKeyException.class,()->{
			service.addNewClient(indianClient);
		});
		
	}
	
	@Test
	void addNewClientWithBalance() throws SQLException {
		
		Assertions.assertTrue(service.addNewClient(usClient)==usClient);
		
	}
	
	@Test
    void addNewClient_shouldAddClient() throws SQLException {
        // Mocking client and its info
        Client client = usClient;
        ClientInfo clientInfo = new ClientInfo(client.getEmail(), "", "");

        // Mock the behavior of fmtsService.getClientIdFromFmts()
       

        // Call the method being tested
        Client result = service.addNewClient(client);

        // Verify the result
        Assertions.assertTrue(result.getFirstName().equals("selmonBhai"));

      
    }

//    @Test
//    void addNewClient_shouldNotAddClientWhenClientIdIsNull() {
//        // Mocking client with null clientId
//        Client client = new Client("John", "Doe", "john@example.com", "password", "USA", null);
//
//        // Call the method being tested
//        boolean result = clientService.addNewClient(client);
//
//        // Verify the result
//        Assertions.assertFalse(result);
//
//        // Verify that fmtsService.getClientIdFromFmts() was not called
//        verify(fmtsService, never()).getClientIdFromFmts(any(ClientInfo.class));
//
//        // Verify that dao.insertNewClient() was not called
//        verify(dao, never()).insertNewClient(any(Client.class));
//    }
	
	
	@Test
	void removeNonExistingClient() throws SQLException {
		assertThrows(DatabaseException.class,()->{
			service.removeClient(usClient);
		});
	}
	@Test
	void removeNullClient() throws SQLException {
		assertThrows(NullPointerException.class,()->{
			service.removeClient(null);
		});
	}
	@Test
	void getClientById() throws SQLException {
		
		Client receivedClient=service.getClientById("123abc");
		assertEquals(receivedClient.getFirstName(),"Jitin");
	}
	@Test
	void getInvalidClientById() throws SQLException {
		assertThrows(DatabaseException.class,()->{
		service.getClientById("Aui123");
		});
		
	}
	@Test
	void loginClient() throws SQLException{
		boolean login=service.loginClient("jd@gmail.com", "jd123");
		assertEquals(login,true);
	}
	@Test
	void loginClientWithWrongPassword() throws SQLException{
		Assertions.assertEquals(service.loginClient("jd@gmail.com", "jd1d23"), false);
	}
	
	@Test
	void loginWithInvalidClient() throws SQLException {
		assertThrows(ClientNotFoundException.class,()->{
		service.loginClient("jda@gmail.com", "jd123");
		});
	}
	
	@Test
	void testUpdateClientPassword() throws SQLException
	{
		Assertions.assertEquals(service.updateClientPassword("123abc", "jd123", "vv123"), true);
	}
	
	@Test
	void testUpdatePasswordInvalidClient() throws SQLException
	{
		assertThrows(DatabaseException.class,()->{
		service.updateClientPassword("125abc", "jd123", "vv123");
		});
	}
	
	@Test
	void testUpdateClientPasswordInvalidOldPassword() throws SQLException
	{
		Assertions.assertEquals(service.updateClientPassword("123abc", "gh123", "vv123"), false);
	}
}
