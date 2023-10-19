package com.fidelity.investmonkey.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fidelity.investmonkey.models.ClientInfo;
import com.fidelity.investmonkey.models.Instrument;
import com.fidelity.investmonkey.models.Price;

@SpringBootTest
@Transactional
class FmtsServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FmtsService fmtsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
     
        
    }



    @Test
    void getClientIdFromFmts_shouldReturnClientId() {
        String apiUrl = "http://ec2-3-111-214-97.ap-south-1.compute.amazonaws.com:3000/fmts/client";

        ClientInfo request = new ClientInfo("jdd@gmail.com", "", "");
        String responseJson = "{\"clientId\": \"123456\"}";

        // Mock the behavior of restTemplate.exchange
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseJson, HttpStatus.OK);
        when(restTemplate.exchange(eq(apiUrl), eq(HttpMethod.POST), any(), eq(String.class)))
                .thenReturn(responseEntity);

        String clientId = fmtsService.getClientIdFromFmts(request);

        // Verify the result
        assertEquals("123456", clientId);
    }
    
    @Test
    void getClientIdFromFmts_shouldReturnToken() {
        String apiUrl = "http://ec2-3-111-214-97.ap-south-1.compute.amazonaws.com:3000/fmts/client";

        ClientInfo request = new ClientInfo("jdd@gmail.com", "", "");
        String responseJson = "{\"token\": \"1506685989\"}";

        // Mock the behavior of restTemplate.exchange
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseJson, HttpStatus.OK);
        when(restTemplate.exchange(eq(apiUrl), eq(HttpMethod.POST), any(), eq(String.class)))
                .thenReturn(responseEntity);

        String token = fmtsService.getTokenFromFmts(request);

        // Verify the result
        assertEquals("1506685989", token);
    }
}
