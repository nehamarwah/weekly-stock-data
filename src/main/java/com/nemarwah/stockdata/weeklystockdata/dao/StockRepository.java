package com.nemarwah.stockdata.weeklystockdata.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nemarwah.stockdata.weeklystockdata.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long>{
 	
	public List<Stock> findByStockTicker(String tickerId);
	
}
