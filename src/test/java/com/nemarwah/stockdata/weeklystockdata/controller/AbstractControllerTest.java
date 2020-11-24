package com.nemarwah.stockdata.weeklystockdata.controller;

import org.aspectj.lang.annotation.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.nemarwah.stockdata.weeklystockdata.service.StockService;

@RunWith(SpringRunner.class)
@WebMvcTest
public class AbstractControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	protected StockService stockService;

	@Before(value = "")
	public void setUp() {
		Mockito.reset(stockService);
	}
}
