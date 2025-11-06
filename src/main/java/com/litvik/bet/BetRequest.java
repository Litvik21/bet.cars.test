package com.litvik.bet;

import java.math.BigDecimal;

public record BetRequest(String car,
                         BigDecimal bid) {
}
