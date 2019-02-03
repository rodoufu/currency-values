package com.github.rodoufu.currencyvalues;

import com.github.rodoufu.currencyvalues.bean.CryptoTicker;
import com.github.rodoufu.currencyvalues.bean.FiatTicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

/**
 * Get ticker data for Crypto and Fiat currencies.
 */
@Component
public class TickerData {
    @Value("${ticker.url.crypto}")
    private String cryptoTickerUrl;
    @Value("${ticker.url.fiat}")
    private String fiatTickerUrl;

    private RestTemplate restTemplate;
    private HttpEntity<String> httpEntity;

    @Autowired
    public TickerData(RestTemplate restTemplate, HttpEntity<String> httpEntity) {
        this.restTemplate = restTemplate;
        this.httpEntity = httpEntity;
    }

    /**
     * Get the data asynchronously.
     *
     * @return The Crypto Ticker information.
     * @throws URISyntaxException Malformed URL.
     */
    @Async
    public CompletableFuture<CryptoTicker> getCryptoTicker() {
        final ResponseEntity<CryptoTicker> exchange = restTemplate.exchange(cryptoTickerUrl, HttpMethod.GET, httpEntity, CryptoTicker.class);
        return CompletableFuture.completedFuture(exchange.getBody());
    }

    /**
     * Get the data asynchronously.
     *
     * @return The Fiat Ticker information.
     * @throws URISyntaxException Malformed URL.
     */
    @Async
    public CompletableFuture<FiatTicker> getFiatTicker() {
        final ResponseEntity<FiatTicker> exchange = restTemplate.exchange(fiatTickerUrl, HttpMethod.GET, httpEntity, FiatTicker.class);
        return CompletableFuture.completedFuture(exchange.getBody());
    }

}
