package com.github.rodoufu.currencyvalues.bean;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Currencies quotation at the moment.
 * Fiat currency is a no crypto currency, a regular currency.
 * https://api.exchangeratesapi.io/latest
 */
public class FiatTicker {
	private String base;
	private Calendar date;
	/**
	 * The purpose of this java bean is to handle the data from the rest service.
	 * In a business logic object usually it is better to use method like addRate, removeRate, clearRates
	 * rather than exposing the 'rates' map. 
	 */
	private Map<String, BigDecimal> rates;

	public FiatTicker() {
		setRates(new HashMap<>());
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Map<String, BigDecimal> getRates() {
		return rates;
	}

	public void setRates(Map<String, BigDecimal> rates) {
		this.rates = rates;
	}
}
