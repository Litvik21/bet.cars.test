package com.litvik.bet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BetControllerTest {

    @Mock
    private BetService betService;

    @InjectMocks
    private BetController betController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnOkResponseWhenBetIsSuccessful() {
        BetRequest request = new BetRequest("honda", new BigDecimal("150"));
        Map<String, BigDecimal> mockResponse = Map.of("HONDA", new BigDecimal("150"));
        when(betService.newBet(request)).thenReturn(mockResponse);

        ResponseEntity<Object> response = betController.newBet(request);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockResponse, response.getBody());
        verify(betService, times(1)).newBet(request);
    }

    @Test
    void shouldReturnErrorResponseWhenCarIsUnknown() {
        BetRequest request = new BetRequest("tracTOR", new BigDecimal("100"));
        when(betService.newBet(request)).thenThrow(new IllegalArgumentException("Unknown car: tracTOR"));

        ResponseEntity<Object> response = betController.newBet(request);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody() instanceof Map);
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertEquals("Unknown car: tracTOR", body.get("error"));

        verify(betService, times(1)).newBet(request);
    }

    @Test
    void shouldReturnOkResponseWithBet() {
        String carTitle = "Bmw";
        Map<String, BigDecimal> mockResponse = Map.of("BMW", new BigDecimal("500"));
        when(betService.getBet(carTitle)).thenReturn(mockResponse);

        ResponseEntity<Object> response = betController.getBet(carTitle);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockResponse, response.getBody());
        verify(betService, times(1)).getBet(carTitle);
    }

    @Test
    void shouldReturnEmptyMapWhenNoBets() {
        String carTitle = "honda";
        when(betService.getBet(carTitle)).thenReturn(Map.of());

        ResponseEntity<Object> response = betController.getBet(carTitle);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(((Map<?, ?>) response.getBody()).isEmpty());
        verify(betService, times(1)).getBet(carTitle);
    }
}