package com.nemarwah.stockdata.weeklystockdata.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class StockPrice {

	@Id
	@GeneratedValue
	private Long id;
	
    private BigDecimal price;
    private String currency;

    public StockPrice(BigDecimal price, String currency) {
        this.price = price;
        this.currency = currency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }
    
    public StockPrice() {
    	
    }
}
