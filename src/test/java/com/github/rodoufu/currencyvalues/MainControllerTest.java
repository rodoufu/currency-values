package com.github.rodoufu.currencyvalues;

import com.github.rodoufu.currencyvalues.bean.CryptoTicker;
import com.github.rodoufu.currencyvalues.bean.FiatTicker;
import com.github.rodoufu.currencyvalues.controller.MainController;
import com.github.rodoufu.currencyvalues.dao.BitcoinPriceDAO;
import com.github.rodoufu.currencyvalues.entity.BitcoinPrice;
import com.github.rodoufu.currencyvalues.job.BitcoinPriceJob;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainControllerTest {
	@Autowired
	private BitcoinPriceJob bitcoinPriceJob;
	@Autowired
	private BitcoinPriceDAO bitcoinPriceDAO;
	@Autowired
	private MainController mainController;

	private final BigDecimal error = new BigDecimal(1e-5);

	@Before
	public void setUp() {
		bitcoinPriceDAO.deleteAll();
	}

	@Test
	public void emptyFiatPricesController() {
		final FiatTicker fiatTicker = new FiatTicker();
		final CryptoTicker cryptoTicker = new CryptoTicker();
		cryptoTicker.setBuy(new BigDecimal(1));

		bitcoinPriceJob.saveBitcoinPrice(bitcoinPriceJob.calculateBitcoinPrice(cryptoTicker, fiatTicker));
		final List<BitcoinPrice> bitcoinPrices = new ArrayList<>(mainController.index());
		Assert.assertNotNull(bitcoinPrices);
		Assert.assertEquals(0, bitcoinPrices.size());
	}

	@Test
	public void oneFiatPriceController() {
		final FiatTicker fiatTicker = new FiatTicker();
		fiatTicker.getRates().put("BRL", new BigDecimal(1));
		final CryptoTicker cryptoTicker = new CryptoTicker();
		cryptoTicker.setBuy(new BigDecimal(2));

		bitcoinPriceJob.saveBitcoinPrice(bitcoinPriceJob.calculateBitcoinPrice(cryptoTicker, fiatTicker));
		final List<BitcoinPrice> bitcoinPrices = new ArrayList<>(mainController.index());
		Assert.assertNotNull(bitcoinPrices);
		Assert.assertEquals(1, bitcoinPrices.size());
		Assert.assertTrue(new BigDecimal(2).subtract(bitcoinPrices.get(0).getValue()).compareTo(error) < 0);
	}

	@Test
	public void twoFiatPricesController() {
		final FiatTicker fiatTicker = new FiatTicker();
		// Just changing the Map implementation because in this case I want to keep the order the elements are inserted.
		fiatTicker.setRates(new LinkedHashMap<>());
		fiatTicker.getRates().put("BRL", new BigDecimal(1));
		fiatTicker.getRates().put("USD", new BigDecimal(.25));
		final CryptoTicker cryptoTicker = new CryptoTicker();
		cryptoTicker.setBuy(new BigDecimal(4000));

		bitcoinPriceJob.saveBitcoinPrice(bitcoinPriceJob.calculateBitcoinPrice(cryptoTicker, fiatTicker));
		final List<BitcoinPrice> bitcoinPrices = new ArrayList<>(mainController.index());
		Assert.assertNotNull(bitcoinPrices);
		Assert.assertEquals(2, bitcoinPrices.size());
		Assert.assertTrue(new BigDecimal(1000).subtract(bitcoinPrices.get(0).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(4000).subtract(bitcoinPrices.get(1).getValue()).compareTo(error) < 0);
	}

	@Test
	public void tenFiatPricesController() {
		final FiatTicker fiatTicker = new FiatTicker();
		// Just changing the Map implementation because in this case I want to keep the order the elements are inserted.
		fiatTicker.setRates(new LinkedHashMap<>());
		fiatTicker.getRates().put("BRL", new BigDecimal(1));
		fiatTicker.getRates().put("USD", new BigDecimal(.25));
		fiatTicker.getRates().put("EUR", new BigDecimal(.2));
		fiatTicker.getRates().put("UYX", new BigDecimal(25));
		fiatTicker.getRates().put("AAB", new BigDecimal(2));
		fiatTicker.getRates().put("USA", new BigDecimal(3));
		fiatTicker.getRates().put("BAR", new BigDecimal(.5));
		fiatTicker.getRates().put("TYR", new BigDecimal(2.5));
		fiatTicker.getRates().put("ZLT", new BigDecimal(4));
		fiatTicker.getRates().put("PSW", new BigDecimal(1.1));
		final CryptoTicker cryptoTicker = new CryptoTicker();
		cryptoTicker.setBuy(new BigDecimal(4000));

		bitcoinPriceJob.saveBitcoinPrice(bitcoinPriceJob.calculateBitcoinPrice(cryptoTicker, fiatTicker));
		final List<BitcoinPrice> bitcoinPrices = new ArrayList<>(mainController.index());
		Assert.assertNotNull(bitcoinPrices);
		Assert.assertEquals(10, bitcoinPrices.size());
		Assert.assertTrue(new BigDecimal(4400).subtract(bitcoinPrices.get(0).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(16000).subtract(bitcoinPrices.get(1).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(10000).subtract(bitcoinPrices.get(2).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(2000).subtract(bitcoinPrices.get(3).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(6000).subtract(bitcoinPrices.get(4).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(8000).subtract(bitcoinPrices.get(5).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(100000).subtract(bitcoinPrices.get(6).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(800).subtract(bitcoinPrices.get(7).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(1000).subtract(bitcoinPrices.get(8).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(4000).subtract(bitcoinPrices.get(9).getValue()).compareTo(error) < 0);
	}

	@Test
	public void elevenFiatPricesController() {
		final FiatTicker fiatTicker = new FiatTicker();
		// Just changing the Map implementation because in this case I want to keep the order the elements are inserted.
		fiatTicker.setRates(new LinkedHashMap<>());
		fiatTicker.getRates().put("BRL", new BigDecimal(1));
		fiatTicker.getRates().put("USD", new BigDecimal(.25));
		fiatTicker.getRates().put("EUR", new BigDecimal(.2));
		fiatTicker.getRates().put("UYX", new BigDecimal(25));
		fiatTicker.getRates().put("AAB", new BigDecimal(2));
		fiatTicker.getRates().put("USA", new BigDecimal(3));
		fiatTicker.getRates().put("BAR", new BigDecimal(.5));
		fiatTicker.getRates().put("TYR", new BigDecimal(2.5));
		fiatTicker.getRates().put("ZLT", new BigDecimal(4));
		fiatTicker.getRates().put("PSW", new BigDecimal(1.1));
		fiatTicker.getRates().put("PPR", new BigDecimal(1.2));
		final CryptoTicker cryptoTicker = new CryptoTicker();
		cryptoTicker.setBuy(new BigDecimal(4000));

		bitcoinPriceJob.saveBitcoinPrice(bitcoinPriceJob.calculateBitcoinPrice(cryptoTicker, fiatTicker));
		final List<BitcoinPrice> bitcoinPrices = new ArrayList<>(mainController.index());
		Assert.assertNotNull(bitcoinPrices);
		Assert.assertEquals(10, bitcoinPrices.size());
		Assert.assertTrue(new BigDecimal(4800).subtract(bitcoinPrices.get(0).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(4400).subtract(bitcoinPrices.get(1).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(16000).subtract(bitcoinPrices.get(2).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(10000).subtract(bitcoinPrices.get(3).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(2000).subtract(bitcoinPrices.get(4).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(6000).subtract(bitcoinPrices.get(5).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(8000).subtract(bitcoinPrices.get(6).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(12000).subtract(bitcoinPrices.get(7).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(100000).subtract(bitcoinPrices.get(8).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(1000).subtract(bitcoinPrices.get(9).getValue()).compareTo(error) < 0);
	}

}
