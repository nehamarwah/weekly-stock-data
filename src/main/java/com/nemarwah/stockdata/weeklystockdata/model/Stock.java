package com.nemarwah.stockdata.weeklystockdata.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.Data;

@Entity
@Data
@EntityScan(basePackages = "com.nemarwah.stockdata.weeklystockdata")
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long stockId;
	private int quarter;
	private String stockTicker;
	private LocalDateTime Date;
	@OneToOne(cascade = {CascadeType.ALL})
	private StockPrice open;
	@OneToOne(cascade = {CascadeType.ALL})
	private StockPrice high;
	@OneToOne(cascade = {CascadeType.ALL})
	private StockPrice low;
	@OneToOne(cascade = {CascadeType.ALL})
	private StockPrice close;
	private double volume;
	private BigDecimal percentChangePrice;
	private BigDecimal percentChangeVolumeOverLastWeek;
	private double previousWeeksVolume;
	@OneToOne(cascade = {CascadeType.ALL})
	private StockPrice nextWeeksOpen;
	@OneToOne(cascade = {CascadeType.ALL})
	private StockPrice nextWeeksClose;
	private BigDecimal percentChangeNextWeeksPrice;
	private int daysToNextDividend;
	private double percentReturnNextDividend;


	public Stock() {

	}


	public Stock(int quarter, String stockTicker, LocalDateTime date, StockPrice open, StockPrice high, StockPrice low,
			StockPrice close, double volume, BigDecimal percentChangePrice, BigDecimal percentChangeVolumeOverLastWeek,
			double previousWeeksVolume, StockPrice nextWeeksOpen, StockPrice nextWeeksClose,
			BigDecimal percentChangeNextWeeksPrice, int daysToNextDividend, double percentReturnNextDividend) {
		super();
		this.quarter = quarter;
		this.stockTicker = stockTicker;
		Date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		this.percentChangePrice = percentChangePrice;
		this.percentChangeVolumeOverLastWeek = percentChangeVolumeOverLastWeek;
		this.previousWeeksVolume = previousWeeksVolume;
		this.nextWeeksOpen = nextWeeksOpen;
		this.nextWeeksClose = nextWeeksClose;
		this.percentChangeNextWeeksPrice = percentChangeNextWeeksPrice;
		this.daysToNextDividend = daysToNextDividend;
		this.percentReturnNextDividend = percentReturnNextDividend;
	}

}
