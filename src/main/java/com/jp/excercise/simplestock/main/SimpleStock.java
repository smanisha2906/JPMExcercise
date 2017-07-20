package com.jp.excercise.simplestock.main;

import java.util.HashMap;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jp.excercise.simplestock.domain.manager.StockDomainManager;
import com.jp.excercise.simplestock.representation.Stock;
import com.jp.excercise.simplestock.service.SimpleStockService;

public class SimpleStock {

	private static final Logger logger = Logger.getLogger(SimpleStock.class);

	private static ClassPathXmlApplicationContext applicationCtxt = new ClassPathXmlApplicationContext(
			new String[] { "ApplicationContext.xml" });

	public static void main(String[] args) {
		SimpleStockService simpleStockService = applicationCtxt.getBean("service", SimpleStockService.class);
		StockDomainManager stocksDomainManager = simpleStockService.getStocksDomainManager();
		HashMap<String, Stock> stocks = stocksDomainManager.getStocks();

		for (Stock stock : stocks.values()) {
			try {
				logger.debug(
						stock.getStockSymbol() + " dividend: " + simpleStockService.calculateDividendYield(2.6, stock));
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				logger.debug(stock.getStockSymbol() + " P/E Ratio: " + simpleStockService.calculatePERatio(2.6, stock));
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (int i = 1; i <= 10; i++) {
				Random r = new Random();
				Integer minRange = 1;
				Integer maxRange = 10;
				double randomValue = minRange + (maxRange - minRange) * r.nextDouble();
				simpleStockService.boughtShares(i, randomValue, stock);
				logger.debug(stock.getStockSymbol() + " got " + i + " shares at price" + randomValue);
				randomValue = minRange + (maxRange - minRange) * r.nextDouble();
				simpleStockService.soldShares(i, randomValue, stock);
				logger.debug(stock.getStockSymbol() + " sold " + i + " shares at price" + randomValue);

			}
			logger.debug(stock.getStockSymbol() + " price: $" + simpleStockService.getPrice(stock));
			try {
				logger.debug(stock.getStockSymbol() + " volumeWeightedStockPrice:$"
						+ simpleStockService.calculateStockPrice(stock));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Double GBCEallShareIndex;
		try {
			GBCEallShareIndex = simpleStockService.calculateGBCEAllShareIndex(stocks);
			logger.debug("GBCE All Share Index: " + GBCEallShareIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
