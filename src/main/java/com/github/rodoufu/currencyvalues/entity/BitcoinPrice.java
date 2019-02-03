package com.github.rodoufu.currencyvalues.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Bitcoin price in a specific currency at a specific time.
 */
@Entity
@Table(indexes = { @Index(columnList = "date") })
public class BitcoinPrice {
	/** Id. */
	@Id
	@GeneratedValue
	private Long id;
	/** Currency 3 char code. */
	private String currencyCode;
	/** The actual price. */
	private BigDecimal value;
	/** Date of the quotation. */
	private Calendar date;

	public BitcoinPrice() {
	}

	public BitcoinPrice(String currencyCode, BigDecimal value, Calendar date) {
		setCurrencyCode(currencyCode);
		setValue(value);
		setDate(date);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
}
