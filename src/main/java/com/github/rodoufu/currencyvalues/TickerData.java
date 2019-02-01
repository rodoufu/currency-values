package com.github.rodoufu.currencyvalues;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.github.rodoufu.currencyvalues.bean.CryptoTicker;
import com.github.rodoufu.currencyvalues.bean.FiatTicker;

/**
 * Get ticker data for Crypto and Fiat currencies.
 */
@Component
public class TickerData {
	@Value("${ticker.url.crypto}")
	private String cryptoTickerUrl;
	@Value("${ticker.url.fiat}")
	private String fiatTickerUrl;
	
	@Autowired
    private RestTemplate restTemplate;

    /**
     * Get the data asynchronously.
     * @return The Crypto Ticker information.
     * @throws URISyntaxException Malformed URL.
     */
	@Async
	public CompletableFuture<CryptoTicker> getCryptoTicker() throws URISyntaxException {
		return CompletableFuture.completedFuture(restTemplate.getForObject(new URI(cryptoTickerUrl), CryptoTicker.class));
	}

    /**
     * Get the data asynchronously.
     * @return The Fiat Ticker information.
     * @throws URISyntaxException Malformed URL.
     */
	@Async
	public CompletableFuture<FiatTicker> getFiatTicker() throws URISyntaxException {
		return CompletableFuture.completedFuture(restTemplate.getForObject(new URI(fiatTickerUrl), FiatTicker.class));
	}

}
