package com.jp.excercise.simplestock.service;

import java.util.Date;
import java.util.HashMap;
import java.util.SortedMap;

import org.apache.log4j.Logger;

import com.jp.excercise.simplestock.domain.manager.StockDomainManager;
import com.jp.excercise.simplestock.representation.Stock;
import com.jp.excercise.simplestock.representation.StockType;
import com.jp.excercise.simplestock.representation.Trade;
import com.jp.excercise.simplestock.representation.TradeType;

public class SimpleStockServiceImpl implements SimpleStockService {

	private static final Logger logger = Logger.getLogger(SimpleStockServiceImpl.class);

	private StockDomainManager stocksDomainManager = null;

	public void setStocksEntityManager(StockDomainManager stocksDomainManager) {
		this.stocksDomainManager = stocksDomainManager;
	}

	public StockDomainManager getStocksDomainManager() {
		return stocksDomainManager;
	}

	@Override
	public double calculateDividendYield(double marketPrice, Stock stock) throws Exception {
		double dividendYield = -1.0;

		try {
			logger.debug("Now Calculating Dividend Yield for the stock symbol: " + stock.getStockSymbol());

			if (stock.getType() == StockType.COMMON) {
				dividendYield = stock.getLastDividend() / marketPrice;
			} else if (stock.getType() == StockType.PREFERRED) {
				dividendYield = stock.getFixedDividend() * stock.getParValue() / marketPrice;
			}
		} catch (Exception e) {
			logger.error("Error while calculating dividend yioeld for the stock : " + stock.getStockSymbol() + ".", e);
			throw new Exception(
					"Error while calculating dividend yioeld for the stock : " + stock.getStockSymbol() + ".", e);
		}
		return dividendYield;
	}

	@Override
	public double calculatePERatio(double marketPrice, Stock stock) throws Exception {
		double peRatio = -1.0;
		try {
			logger.debug("now calculating PE ratIO for the stock symbol: " + stock.getStockSymbol());

			peRatio = marketPrice / stock.getLastDividend();

			logger.debug(" PE ratio is: " + peRatio);

		} catch (Exception e) {
			logger.error("Error while calculating PE ratio for the stock: " + stock.getStockSymbol() + ".", e);
			throw new Exception("Error while calculating PE ratio for the stock: " + stock.getStockSymbol() + ".", e);
		}
		return peRatio;
	}

	@Override
	public double calculateStockPrice(Stock stock) throws Exception {
		Date now = new Date();
		// Date 15 minutes ago
		Date startTime = new Date(now.getTime() - (15 * 60 * 1000));
		SortedMap<Date, Trade> trades = stock.getTrades().tailMap(startTime);

		Double volumeWeigthedStockPrice = 0.0;
		Integer totalQuantity = 0;
		for (Trade trade : trades.values()) {
			totalQuantity += trade.getQuantity();
			volumeWeigthedStockPrice += trade.getPrice() * trade.getQuantity();
		}
		return volumeWeigthedStockPrice / totalQuantity;
	}

	@Override
	public double calculateGBCEAllShareIndex(HashMap<String, Stock> stocks) throws Exception {
		Double allShareIndex = 0.0;
		for (Stock stock : stocks.values()) {
			allShareIndex += getPrice(stock);
		}
		return Math.pow(allShareIndex, 1.0 / stocks.size());
	}

	@Override
	public void boughtShares(int quantity, double marketPrice, Stock stock) {
		Trade trade = new Trade(TradeType.BUY, quantity, marketPrice);
		stock.getTrades().put(new Date(), trade);

	}

	@Override
	public void soldShares(int quantity, double marketPrice, Stock stock) {
		Trade trade = new Trade(TradeType.SELL, quantity, marketPrice);
		stock.getTrades().put(new Date(), trade);

	}

	@Override
	public Double getPrice(Stock stock) {
		if (stock.getTrades().size() > 0) {
			return stock.getTrades().lastEntry().getValue().getPrice();
		} else {
			return 0.0;
		}
	}
}
