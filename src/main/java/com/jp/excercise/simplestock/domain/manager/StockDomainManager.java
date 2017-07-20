package com.jp.excercise.simplestock.domain.manager;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.jp.excercise.simplestock.representation.Stock;

public class StockDomainManager {

	private Logger logger = Logger.getLogger(StockDomainManager.class);

	private HashMap<String, Stock> stocks = null;

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public HashMap<String, Stock> getStocks() {
		return stocks;
	}

	public void setStocks(HashMap<String, Stock> stocks) {
		this.stocks = stocks;
	}

}
