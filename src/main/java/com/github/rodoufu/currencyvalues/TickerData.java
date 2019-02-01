package com.github.rodoufu.currencyvalues;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.github.rodoufu.currencyvalues.bean.CryptoTicker;
import com.github.rodoufu.currencyvalues.bean.FiatTicker;

@Component
public class TickerData {
	@Value("${ticker.url.crypto}")
	private String cryptoTickerUrl;
	@Value("${ticker.url.fiat}")
	private String fiatTickerUrl;
	
	@Autowired
    private RestTemplate restTemplate;

	@Async
	public Future<CryptoTicker> getCryptoTicker() throws RestClientException, URISyntaxException, InterruptedException {
		for (int i = 0; i < 10; ++i) {
			System.out.println("CryptoTicker");
			Thread.sleep(100);
		}
		return new AsyncResult<CryptoTicker>(restTemplate.getForObject(new URI(cryptoTickerUrl), CryptoTicker.class));
	}
	
	@Async
	public Future<FiatTicker> getFiatTicker() throws RestClientException, URISyntaxException, InterruptedException {
		for (int i = 0; i < 10; ++i) {
			System.out.println("FiatTicker");
			Thread.sleep(100);
		}
		return new AsyncResult<FiatTicker>(restTemplate.getForObject(new URI(fiatTickerUrl), FiatTicker.class));
	}

}
