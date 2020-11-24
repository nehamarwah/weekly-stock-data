package com.nemarwah.stockdata.weeklystockdata.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nemarwah.stockdata.weeklystockdata.dao.StockRepository;
import com.nemarwah.stockdata.weeklystockdata.model.Stock;
import com.nemarwah.stockdata.weeklystockdata.model.StockCurrency;
import com.nemarwah.stockdata.weeklystockdata.model.StockPrice;

@Service
public class StockService {

	@Autowired
	StockRepository stockRepository;
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	/**
	 * Returns list of stocks linked with the ticker Id
	 * @param tickerId
	 * @return
	 */
	public List<Stock> getStockByTickerId(String tickerId) {
		return stockRepository.findByStockTicker(tickerId);
	}

	/**
	 * Creates a new data set
	 * @param dataSet
	 * @return
	 */
	public Long addStockInfo(Stock dataSet) {
		Stock stock = stockRepository.save(dataSet);
		return stock.getStockId();
	}

	/**
	 * Uploads the Bulkdata set , csv file format is accepted
	 * @param file
	 */
	public void uploadDataSet(MultipartFile file) {
		try {
			List<Stock> stockDataSet = csvToStocks(file.getInputStream());
			stockRepository.saveAll(stockDataSet);
		} catch (Exception e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	
	public static List<Stock> csvToStocks(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<Stock> stockList = new ArrayList<>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				Stock stock = new Stock(Integer.parseInt(csvRecord.get("quarter")), csvRecord.get("stock"), LocalDate.parse(csvRecord.get("date"), formatter).atStartOfDay(),
						getStockPrice(csvRecord.get("open")), getStockPrice(csvRecord.get("high")), getStockPrice(csvRecord.get("low")), getStockPrice(csvRecord.get("close")),
						parseStringToDouble(csvRecord.get("volume")), bigDecimalNullCheck(csvRecord.get("percent_change_price")),
						bigDecimalNullCheck(csvRecord.get("percent_change_volume_over_last_wk")), parseStringToDouble(csvRecord.get("previous_weeks_volume")),
						getStockPrice(csvRecord.get("next_weeks_open")), getStockPrice(csvRecord.get("next_weeks_close")),
						bigDecimalNullCheck(csvRecord.get("percent_change_next_weeks_price")), Integer.parseInt(csvRecord.get("days_to_next_dividend")),
						parseStringToDouble(csvRecord.get("percent_return_next_dividend")));

				stockList.add(stock);
			}

			return stockList;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

	private static BigDecimal bigDecimalNullCheck(String value) {
		if (value == null || value.isEmpty() || value.equals(""))
			return new BigDecimal(0);
		else return new BigDecimal(value);
			
		
	}
	
	private static double parseStringToDouble(String value) {
	    return value == null || value.isEmpty() ? Double.NaN : Double.parseDouble(value);
	}
	
	private static StockPrice getStockPrice(String price) {
		return new StockPrice(new BigDecimal(price.substring(1, price.length()-1)), StockCurrency.USD.getSymbol());
	}
}
