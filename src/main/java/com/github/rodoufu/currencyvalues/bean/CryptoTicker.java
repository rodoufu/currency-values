package com.github.rodoufu.currencyvalues.bean;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * https://broker.negociecoins.com.br/api/v3/btcbrl/ticker
 * @author rodoufu
 */
public class CryptoTicker {
	private BigDecimal buy;
	private Calendar date;
	private BigDecimal high;
	private BigDecimal last;
	private BigDecimal low;
	private BigDecimal sell;
	private BigDecimal vol;

	public BigDecimal getBuy() {
		return buy;
	}

	public void setBuy(BigDecimal buy) {
		this.buy = buy;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLast() {
		return last;
	}

	public void setLast(BigDecimal last) {
		this.last = last;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getSell() {
		return sell;
	}

	public void setSell(BigDecimal sell) {
		this.sell = sell;
	}

	public BigDecimal getVol() {
		return vol;
	}

	public void setVol(BigDecimal vol) {
		this.vol = vol;
	}

}
