package com.github.rodoufu.currencyvalues;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

import com.github.rodoufu.currencyvalues.bean.CryptoTicker;
import com.github.rodoufu.currencyvalues.bean.FiatTicker;
import com.github.rodoufu.currencyvalues.dao.BitcoinPriceDAO;
import com.github.rodoufu.currencyvalues.entity.BitcoinPrice;
import com.github.rodoufu.currencyvalues.job.BitcoinPriceJob;

@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest
public class BitcoinPriceJobTest {
	@Autowired
	private BitcoinPriceDAO bitcoinPriceDAO;
	
	@MockBean
	private BitcoinPriceJob bitcoinPriceJob;
	@MockBean
	private TickerData tickerData;

	private final BigDecimal error = new BigDecimal(1e-5);

	@Before
	public void setUp() {
		doAnswer((invocation) -> tickerData).when(bitcoinPriceJob).getTickerData();
		doAnswer((invocation) -> bitcoinPriceDAO).when(bitcoinPriceJob).getBitcoinPriceDAO();
		bitcoinPriceDAO.deleteAll();
	}

	@Test
	public void emptyFiatPricesJob() throws URISyntaxException, RestClientException, InterruptedException, ExecutionException {
		final FiatTicker fiatTicker = new FiatTicker();
		when(tickerData.getFiatTicker()).thenReturn(CompletableFuture.completedFuture(fiatTicker));

		final CryptoTicker cryptoTicker = new CryptoTicker();
		cryptoTicker.setBuy(new BigDecimal(2));
		when(tickerData.getCryptoTicker()).thenReturn(CompletableFuture.completedFuture(cryptoTicker));

		when(bitcoinPriceJob.calculateBitcoinPrice(cryptoTicker, fiatTicker)).thenCallRealMethod();
		doCallRealMethod().when(bitcoinPriceJob).saveBitcoinPrice(ArgumentMatchers.anyList());
		doCallRealMethod().when(bitcoinPriceJob).scheduleFetchAndSaveBitcoinPrice();

		bitcoinPriceJob.scheduleFetchAndSaveBitcoinPrice();
		final List<BitcoinPrice> top10bitcoinPrice = bitcoinPriceDAO.findTop10ByOrderByIdDesc();
		Assert.assertNotNull(top10bitcoinPrice);
		Assert.assertEquals(0, top10bitcoinPrice.size());
	}

	@Test
	public void oneFiatPriceJob() throws URISyntaxException, RestClientException, InterruptedException, ExecutionException {
		final FiatTicker fiatTicker = new FiatTicker();
		fiatTicker.getRates().put("BRL", new BigDecimal(1));
		when(tickerData.getFiatTicker()).thenReturn(CompletableFuture.completedFuture(fiatTicker));

		final CryptoTicker cryptoTicker = new CryptoTicker();
		cryptoTicker.setBuy(new BigDecimal(2));
		when(tickerData.getCryptoTicker()).thenReturn(CompletableFuture.completedFuture(cryptoTicker));

		when(bitcoinPriceJob.calculateBitcoinPrice(cryptoTicker, fiatTicker)).thenCallRealMethod();
		doCallRealMethod().when(bitcoinPriceJob).saveBitcoinPrice(ArgumentMatchers.anyList());
		doCallRealMethod().when(bitcoinPriceJob).scheduleFetchAndSaveBitcoinPrice();

		bitcoinPriceJob.scheduleFetchAndSaveBitcoinPrice();
		final List<BitcoinPrice> top10bitcoinPrice = bitcoinPriceDAO.findTop10ByOrderByIdDesc();
		Assert.assertNotNull(top10bitcoinPrice);
		Assert.assertEquals(1, top10bitcoinPrice.size());
		Assert.assertTrue(new BigDecimal(2).subtract(top10bitcoinPrice.get(0).getValue()).compareTo(error) < 0);
	}

	@Test
	public void twoFiatPricesJob() throws URISyntaxException, RestClientException, InterruptedException, ExecutionException {
		final FiatTicker fiatTicker = new FiatTicker();
		// Just changing the Map implementation because in this case I want to keep the order the elements are inserted.
		fiatTicker.setRates(new LinkedHashMap<>());
		fiatTicker.getRates().put("BRL", new BigDecimal(1));
		fiatTicker.getRates().put("USD", new BigDecimal(.25));
		when(tickerData.getFiatTicker()).thenReturn(CompletableFuture.completedFuture(fiatTicker));

		final CryptoTicker cryptoTicker = new CryptoTicker();
		cryptoTicker.setBuy(new BigDecimal(2000));
		when(tickerData.getCryptoTicker()).thenReturn(CompletableFuture.completedFuture(cryptoTicker));

		when(bitcoinPriceJob.calculateBitcoinPrice(cryptoTicker, fiatTicker)).thenCallRealMethod();
		doCallRealMethod().when(bitcoinPriceJob).saveBitcoinPrice(ArgumentMatchers.anyList());
		doCallRealMethod().when(bitcoinPriceJob).scheduleFetchAndSaveBitcoinPrice();

		bitcoinPriceJob.scheduleFetchAndSaveBitcoinPrice();
		final List<BitcoinPrice> top10bitcoinPrice = bitcoinPriceDAO.findTop10ByOrderByIdDesc();
		Assert.assertNotNull(top10bitcoinPrice);
		Assert.assertEquals(2, top10bitcoinPrice.size());
		// Descending order
		Assert.assertTrue(new BigDecimal(500).subtract(top10bitcoinPrice.get(0).getValue()).compareTo(error) < 0);
		Assert.assertTrue(new BigDecimal(2000).subtract(top10bitcoinPrice.get(1).getValue()).compareTo(error) < 0);
	}

}
