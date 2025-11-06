package com.litvik.bet;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/bets")
public class BetController {
    private BetService betService;

    @PostMapping("/new")
    public ResponseEntity<Object> newBet(@RequestBody BetRequest request) {
        try {
            Map<String, BigDecimal> bet = betService.newBet(request);
            return ResponseEntity.ok(bet);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<Object> getBet(@RequestParam String carTitle) {
        Map<String, BigDecimal> response = betService.getBet(carTitle);
        return ResponseEntity.ok(response);
    }
}
