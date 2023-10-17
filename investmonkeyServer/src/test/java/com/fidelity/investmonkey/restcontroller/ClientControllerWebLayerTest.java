package com.fidelity.investmonkey.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.fidelity.investmonkey.models.Client;
import com.fidelity.investmonkey.models.ClientIdentification;
import com.fidelity.investmonkey.service.ClientService;

@WebMvcTest
@AutoConfigureMybatis
class ClientControllerWebLayerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ClientService mockBusinessService;

	@MockBean
	private RestTemplate mockRestTemplate;

	private Client expectedClient = new Client("Vishakha", "V", "6361724765", "123abc", "vishakhav965@gmail.com",
			LocalDate.of(2000, 10, 9), "India", "573201", "vis123", new ClientIdentification("Aadhar", "872357934426"));

	@Test
	public void testQueryForClientById() throws Exception {
		String clientId = "123abc";
		Client client = expectedClient;

		when(mockBusinessService.getClientById(clientId)).thenReturn(client);

		mockMvc.perform(get("/client/" + clientId)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Vishakha"));

	}

//	@Test
//	public void testQueryForGetShipInvalidId() throws Exception {
//		int id = -1;
//		
//		mockMvc.perform(get("/ships/" + id))
//		       .andDo(print())
//		       .andExpect(status().isBadRequest());
//		       
//	}
//	
//	@Test
//	public void testQueryForGetShipsByIdNonExistantId() throws Exception {
//		int id = 99;
//		when(mockBusinessService.findShipById(id))
//			.thenReturn(null);
//		
//		mockMvc.perform(get("/ships/" + id))
//		       .andDo(print())
//		       .andExpect(status().isNoContent());
//		       
//	}
//	
//	@Test
//	public void testQueryForGetShipByIdDaoException() throws Exception {
//		int id = 5;
//		when(mockBusinessService.findShipById(id))
//			.thenThrow(new RuntimeException("mock exception"));
//		
//		mockMvc.perform(get("/ships/" + id))
//		       .andDo(print())
//		       .andExpect(status().isInternalServerError());
//		      
//	}

}
