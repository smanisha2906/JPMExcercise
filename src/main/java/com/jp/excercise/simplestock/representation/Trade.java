package com.jp.excercise.simplestock.representation;

import org.apache.log4j.Logger;

public class Trade {
	private Logger logger = Logger.getLogger(Trade.class);
	private int quantity = 0;
	private double price = 0.0;
	private TradeType tradeType = TradeType.BUY;


	public Trade(TradeType type, Integer quantity, Double price) {
		this.setTradeType(type);
		this.setQuantity(quantity);
		this.setPrice(price);
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

}
