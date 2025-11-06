package com.litvik.bet;

public enum Cars {
    FERRARI("Ferrari"),
    BMW("BMW"),
    AUDI("Audi"),
    HONDA("Honda");

    private String value;

    Cars(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Cars fromValue(String value) {
        for (Cars car : Cars.values()) {
            if (car.value.equals(value)) {
                return car;
            }
        }
        throw new IllegalArgumentException("No enum constant with value: " + value);
    }
}
