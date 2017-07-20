package com.jp.excercise.simplestock.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.jp.excercise.simplestock.representation.Stock;
import com.jp.excercise.simplestock.representation.StockType;
import com.jp.excercise.simplestock.representation.Trade;
import com.jp.excercise.simplestock.representation.TradeType;

public class SimpleStockServiceTest {

	private SimpleStockService simpleStockService = new SimpleStockServiceImpl();

	@Test
	public void testDividendYield() {
		Stock stockALE = new Stock("ALE", StockType.COMMON, 23.0, 0.0, 60.0);
		Stock stockGIN = new Stock("GIN", StockType.PREFERRED, 8.0, 0.02, 100.0);
		

		Double dividendForALE = 0.0;
		Double dividendForGIN = 0.0;
		try {
			dividendForALE = simpleStockService.calculateDividendYield(1.0, stockALE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Double expectedDividendALE = stockALE.getLastDividend() / 1.0;
		assertEquals(expectedDividendALE, dividendForALE, 0.0);

		try {
			dividendForGIN = simpleStockService.calculateDividendYield(1.0, stockGIN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Double expectedDividendGIN = stockGIN.getFixedDividend() * stockGIN.getParValue() / 1.0;
		assertEquals(expectedDividendGIN, dividendForGIN, 0.0);
	}

	@Test
	public void testPERatio() {
		Stock stockALE = new Stock("ALE", StockType.COMMON, 23.0, 0.0, 60.0);
		Double peRatioALE = 0.0;
		try {
			peRatioALE = simpleStockService.calculatePERatio(1.0, stockALE);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		Double expectedPeRatioALE = 1.0 / stockALE.getLastDividend();
		assertEquals(expectedPeRatioALE, peRatioALE, 0.0);
	}

	@Test
	public void testboughtShares() {
		Stock stockALE = new Stock("ALE", StockType.COMMON, 23.0, 0.0, 60.0);
		simpleStockService.boughtShares(1, 10.0, stockALE);
		assertEquals(10.0, simpleStockService.getPrice(stockALE), 0.0);
	}

	@Test
	public void testsoldShares() {
		Stock stockALE = new Stock("ALE", StockType.COMMON, 23.0, 0.0, 60.0);
		simpleStockService.soldShares(1,  10.0, stockALE);
		assertEquals(10.0, simpleStockService.getPrice(stockALE), 0.0);
	}

	@Test
	public void testGetPrice() {
		Stock stockALE = new Stock("ALE", StockType.COMMON, 23.0, 0.0, 60.0);
		simpleStockService.soldShares(1, 10.0, stockALE);
		simpleStockService.boughtShares(1,  11.0, stockALE);
		assertEquals(11.0, simpleStockService.getPrice(stockALE), 0.0);
	}

	@Test
	public void testCalculateVolumeWeightedStockPrice() {
		Stock stockALE = new Stock("ALE", StockType.COMMON, 23.0, 0.0, 60.0);
		simpleStockService.soldShares(2, 10.0, stockALE);
		simpleStockService.boughtShares(2,  10.0, stockALE);
		Double volumeWeightedStockPrice = 0.0;
		try {
			volumeWeightedStockPrice = simpleStockService.calculateStockPrice(stockALE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(10.0, volumeWeightedStockPrice, 0.0);
		Date now = new Date();
		Date startTime = new Date(now.getTime() - (20 * 60 * 1000));
		stockALE.getTrades().put(startTime, new Trade(TradeType.BUY, 1, 20.0));
		try {
			volumeWeightedStockPrice = simpleStockService.calculateStockPrice(stockALE);
		} catch (Exception e) {
			e.printStackTrace();
		};
		assertEquals(10.0, volumeWeightedStockPrice, 0.0);
	}

}
