package com.jp.excercise.simplestock.service;

import java.util.HashMap;

import com.jp.excercise.simplestock.domain.manager.StockDomainManager;
import com.jp.excercise.simplestock.representation.Stock;

public interface SimpleStockService {
	public double calculateStockPrice(Stock stock) throws Exception;
	public double calculateGBCEAllShareIndex(HashMap<String, Stock> stocks) throws Exception;
	public StockDomainManager getStocksDomainManager();
	public double calculateDividendYield(double d, Stock stock) throws Exception;
	public double calculatePERatio(double d, Stock stock) throws Exception;
	public void boughtShares(int quantity, double marketPrice, Stock stock);
	public void soldShares(int quantity, double marketPrice, Stock stock);
	public Double getPrice(Stock stock);
}
