package com.nemarwah.stockdata.weeklystockdata.model;

public enum StockCurrency {

    EUR("€"),
    USD("$"),
    GBP("£");

    private final String symbol;

    StockCurrency(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
