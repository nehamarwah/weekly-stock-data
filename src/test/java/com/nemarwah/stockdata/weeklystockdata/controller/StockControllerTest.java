package com.nemarwah.stockdata.weeklystockdata.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.nemarwah.stockdata.weeklystockdata.model.Stock;
import com.nemarwah.stockdata.weeklystockdata.model.StockCurrency;
import com.nemarwah.stockdata.weeklystockdata.model.StockPrice;

public class StockControllerTest extends AbstractControllerTest {

	@Test
	public void shouldReturnFoundStockDataSet() throws Exception {

		// given
		List<Stock> stocks = new ArrayList<>();
		
		Stock stock = createStock();
		stock.setStockId(1L);
		stocks.add(stock);

		// when
		when(stockService.getStockByTickerId("AA")).thenReturn(stocks);
		  
		mockMvc.perform(get("/stocks/AA").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].quarter", is(2)))
				.andExpect(jsonPath("$[0].daysToNextDividend", is(41)))
		        .andExpect(jsonPath("$[0].percentReturnNextDividend", is(0.191698D)));
	}

	
	@Test
	public void shouldAddStockInfo() throws Exception {

		String stockInfoBody = "{ \"stockId\": 2605, \"quarter\": 2,\"stockTicker\": \"AA\", \"open\": { \"price\": 14.6,\"currency\": \"$\"}, \"high\": { \"price\": 15.6,\"currency\": \"$\" "+
		
			"},\"low\": {\"price\": 14.5, \"currency\": \"$\" }, \"close\": { \"price\": 15.2, \"currency\": \"$\"}, \"volume\": 99423717,\"percentChangePrice\": 3.82,\"percentChangeVolumeOverLastWeek\": -10.65,"+
			"\"previousWeeksVolume\": 111273573,\"nextWeeksOpen\": { \"price\": 15.2,\"currency\": \"$\" },\"nextWeeksClose\": {\"price\": 16.3,\"currency\": \"$\"},\"percentChangeNextWeeksPrice\": 7.16,"+
			"\"daysToNextDividend\": 40,\"percentReturnNextDividend\": 0.19698,\"date\": \"2011-06-24T00:00:00\"}";
		
		Stock stock = createStock();

		// when
		when(stockService.addStockInfo(stock)).thenReturn(1L);

		// then
		mockMvc.perform(post("/stocks/stockInfo")
				.content(stockInfoBody)
				.contentType(APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk());
	}
	
	private Stock createStock() {
		Stock stock = new Stock();
		LocalDateTime date = LocalDateTime.of(2011, 6, 25, 0, 0);
		stock.setClose(new StockPrice(new BigDecimal("115.23"), StockCurrency.USD.getSymbol()));
		stock.setDate(date);
		stock.setDaysToNextDividend(Integer.parseInt("41"));
		stock.setHigh(new StockPrice(new BigDecimal("115.60"), StockCurrency.USD.getSymbol()));
		stock.setLow(new StockPrice(new BigDecimal("114.56"), StockCurrency.USD.getSymbol()));
		stock.setNextWeeksClose(new StockPrice(new BigDecimal("116.31"), StockCurrency.USD.getSymbol()));
		stock.setNextWeeksOpen(new StockPrice(new BigDecimal("115.22"), StockCurrency.USD.getSymbol()));
		stock.setOpen(new StockPrice(new BigDecimal("114.67"), StockCurrency.USD.getSymbol()));
		stock.setPercentChangeNextWeeksPrice(new BigDecimal("7.116163"));
		stock.setPercentChangePrice(new BigDecimal("3.811731"));
		stock.setPercentChangeVolumeOverLastWeek(new BigDecimal("-10.614929945"));
		stock.setPercentReturnNextDividend(Double.parseDouble("0.191698"));
		stock.setPreviousWeeksVolume(Double.parseDouble("1111273573"));
		stock.setQuarter(Integer.parseInt("2"));
		stock.setStockTicker("AA");
		stock.setVolume(Double.parseDouble("991423717"));
		return stock;
	}
}
