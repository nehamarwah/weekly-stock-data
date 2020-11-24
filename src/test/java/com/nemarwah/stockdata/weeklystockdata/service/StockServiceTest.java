package com.nemarwah.stockdata.weeklystockdata.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.nemarwah.stockdata.weeklystockdata.dao.StockRepository;
import com.nemarwah.stockdata.weeklystockdata.model.Stock;
import com.nemarwah.stockdata.weeklystockdata.model.StockCurrency;
import com.nemarwah.stockdata.weeklystockdata.model.StockPrice;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceTest {

	@Autowired
	StockService stockService;
	
	@Autowired
	StockRepository stockRepository;
	
	@Test
	public void shouldAddStockInfo() {
        Stock stock = createStock();
		Long stockId = stockService.addStockInfo(stock);
		assertThat("Stock id shouldn't be null", stockId, notNullValue());
	}

	@Test
	public void shouldReturnAddedStockInfo() {
		
		Stock stock = createStock();
		stock.setStockTicker("AAA");
		stockService.addStockInfo(stock);
		List<Stock> stocks = stockService.getStockByTickerId(stock.getStockTicker());

		assertEquals(1, stocks.size(), "There should be one dataset");
		assertEquals(2, stocks.get(0).getQuarter(), "Values are added correctly");
		
	}
	

	@Test
	public void uploadDataSet() throws FileNotFoundException, IOException
	{
		MultipartFile multipartFile = new MockMultipartFile("dow_jones_index.csv", new FileInputStream(new File("src/main/resources/dow_jones_index.csv")));
		stockService.uploadDataSet(multipartFile);
		assertEquals(750, stockRepository.findAll().size(), "Total number of records uploaded via dow_jones_index.csv file");
		
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
