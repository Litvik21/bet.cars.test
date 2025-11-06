package com.litvik.bet;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BetService {
    private Map<String, BigDecimal> bets;

    public BetService() {
        this.bets = new ConcurrentHashMap<>();
    }

    public Map<String, BigDecimal> newBet(BetRequest request) {
        Cars car = Arrays.stream(Cars.values())
                .filter(c -> c.name().equalsIgnoreCase(request.car()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown car: " + request.car()));

        String carTitle = car.getValue();

        bets.merge(carTitle, request.bid(), BigDecimal::add);

        return Map.of(carTitle, request.bid());
    }

    public Map<String, BigDecimal> getBet(String carTitle) {
        Cars car = Arrays.stream(Cars.values())
                .filter(c -> c.name().equalsIgnoreCase(carTitle))
                .findFirst()
                .orElse(null);

        if (car == null) {
            return Map.copyOf(bets);
        } else {
            return Map.of(car.getValue(), bets.get(car.getValue()));
        }
    }
}
