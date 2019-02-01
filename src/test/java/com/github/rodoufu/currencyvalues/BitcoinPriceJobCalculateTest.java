package com.github.rodoufu.currencyvalues;

import com.github.rodoufu.currencyvalues.bean.CryptoTicker;
import com.github.rodoufu.currencyvalues.bean.FiatTicker;
import com.github.rodoufu.currencyvalues.entity.BitcoinPrice;
import com.github.rodoufu.currencyvalues.job.BitcoinPriceJob;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BitcoinPriceJobCalculateTest {
	@Autowired
	private BitcoinPriceJob bitcoinPriceJob;

	private final BigDecimal error = new BigDecimal(1e-5);

	@Test
	public void emptyFiatPrices() {
		final FiatTicker fiatTicker = new FiatTicker();
		final CryptoTicker cryptoTicker = new CryptoTicker();
		cryptoTicker.setBuy(new BigDecimal(1));

		final Collection<BitcoinPrice> bitcoinPrices = bitcoinPriceJob.calculateBitcoinPrice(cryptoTicker, fiatTicker);
		Assert.assertNotNull(bitcoinPrices);
		Assert.assertEquals(0, bitcoinPrices.size());
	}

	@Test
	public void oneFiatPrice() {
		final FiatTicker fiatTicker = new FiatTicker();
		fiatTicker.getRates().put("BRL", new BigDecimal(1));
		final CryptoTicker cryptoTicker = new CryptoTicker();
		cryptoTicker.setBuy(new BigDecimal(2));

		final List<BitcoinPrice> bitcoinPrices = bitcoinPriceJob.calculateBitcoinPrice(cryptoTicker, fiatTicker);
		Assert.assertNotNull(bitcoinPrices);
		Assert.assertEquals(1, bitcoinPrices.size());
		Assert.assertTrue(new BigDecimal(2).subtract(bitcoinPrices.get(0).getValue()).compareTo(error) < 0);
	}

	@Test
	public void twoFiatPrices() {
		final FiatTicker fiatTicker = new FiatTicker();
		// Just changing the Map implementation because in this case I want to keep the order the elements are inserted.
		fiatTicker.setRates(new LinkedHashMap<>());
		fiatTicker.getRates().put("BRL", new BigDecimal(1));
		fiatTicker.getRates().put("USD", new BigDecimal(.25));
		final CryptoTicker cryptoTicker = new CryptoTicker();
		cryptoTicker.setBuy(new BigDecimal(2000));

		final List<BitcoinPrice> bitcoinPrices = bitcoinPriceJob.calculateBitcoinPrice(cryptoTicker, fiatTicker);
		Assert.assertNotNull(bitcoinPrices);
		Assert.assertEquals(2, bitcoinPrices.size());
		Assert.assertTrue(new BigDecimal(2000).subtract(bitcoinPrices.get(0).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(500).subtract(bitcoinPrices.get(1).getValue()).compareTo(error) < 0);
	}

}
