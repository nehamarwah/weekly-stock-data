package com.nemarwah.stockdata.weeklystockdata.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nemarwah.stockdata.weeklystockdata.model.ResponseMessage;
import com.nemarwah.stockdata.weeklystockdata.model.Stock;
import com.nemarwah.stockdata.weeklystockdata.service.StockService;

@RestController
@RequestMapping("/stocks")
public class StockController {

	@Autowired
	StockService stockService;

	@RequestMapping("/{tickerId}")
	public List<Stock> getStocksByTickerId(@PathVariable String tickerId) {
		return stockService.getStockByTickerId(tickerId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/stockInfo")
	public Long addStockInfo(@RequestBody Stock stock) {
		return stockService.addStockInfo(stock);

	}

	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadDataSet(@RequestParam("file") MultipartFile file) {
		String message = "";
		String TYPE = "csv";
		String ext = Arrays.stream(file.getOriginalFilename().split("\\.")).reduce((a, b) -> b).orElse(null);
		if (null != ext && TYPE.equals(ext)) {
			try {
				stockService.uploadDataSet(file);

				message = "Uploaded the file successfully: " + file.getOriginalFilename();

				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/stocks/upload")
						.path(file.getOriginalFilename()).toUriString();

				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, fileDownloadUri));
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, ""));
			}
		}

		message = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message, ""));
	}
}
