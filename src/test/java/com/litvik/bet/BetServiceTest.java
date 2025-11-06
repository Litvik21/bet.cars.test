package com.litvik.bet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class BetServiceTest {

    private BetService betService;

    @BeforeEach
    void setUp() {
        betService = new BetService();
    }

    @Test
    void shouldAddNewBetForCar() {
        BetRequest request = new BetRequest("Honda", new BigDecimal("100"));
        Map<String, BigDecimal> result = betService.newBet(request);

        assertEquals(new BigDecimal("100"), result.get("Honda"));
        assertEquals(new BigDecimal("100"), betService.getBet("Honda").get("Honda"));
    }

    @Test
    void shouldAccumulateBetsForSameCar() {
        betService.newBet(new BetRequest("BMW", new BigDecimal("50")));
        betService.newBet(new BetRequest("BMW", new BigDecimal("75")));

        Map<String, BigDecimal> result = betService.getBet("BMW");
        assertEquals(new BigDecimal("125"), result.get("BMW"));
    }

    @Test
    void shouldBeCaseInsensitiveForCarName() {
        betService.newBet(new BetRequest("HoNdA", new BigDecimal("200")));
        betService.newBet(new BetRequest("honDA", new BigDecimal("300")));

        Map<String, BigDecimal> result = betService.getBet("HONda");
        assertEquals(new BigDecimal("500"), result.get("Honda"));
    }

    @Test
    void shouldReturnAllBetsIfCarIsUnknownInGetBet() {
        betService.newBet(new BetRequest("BMW", new BigDecimal("100")));
        betService.newBet(new BetRequest("HoNda", new BigDecimal("200")));
        betService.newBet(new BetRequest("Honda", new BigDecimal("500")));
        betService.newBet(new BetRequest("audi", new BigDecimal("200")));
        betService.newBet(new BetRequest("AUDI", new BigDecimal("50")));
        betService.newBet(new BetRequest("FERrari", new BigDecimal("100400")));
        betService.newBet(new BetRequest("ferrari", new BigDecimal("100")));

        Map<String, BigDecimal> result = betService.getBet("unknown");
        assertEquals(4, result.size());
        assertTrue(result.containsKey("BMW"));
        assertEquals(new BigDecimal("100"), result.get("BMW"));
        assertTrue(result.containsKey("Honda"));
        assertEquals(new BigDecimal("700"), result.get("Honda"));
        assertTrue(result.containsKey("Audi"));
        assertEquals(new BigDecimal("250"), result.get("Audi"));
        assertTrue(result.containsKey("Ferrari"));
        assertEquals(new BigDecimal("100500"), result.get("Ferrari"));
    }

    @Test
    void shouldThrowExceptionForUnknownCarInNewBet() {
        BetRequest request = new BetRequest("TRAcTOR", new BigDecimal("100"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> betService.newBet(request));

        assertEquals("Unknown car: TRAcTOR", exception.getMessage());
    }
}