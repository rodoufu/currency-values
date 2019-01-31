package com.github.rodoufu.currencyvalues.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Calendar;

@Entity
public class BitcoinPrice {
    @Id
    @GeneratedValue
    private Long id;
    private String currencyCode;
    private BigDecimal value;
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
