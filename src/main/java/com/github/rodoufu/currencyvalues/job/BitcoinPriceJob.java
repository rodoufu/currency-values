package com.github.rodoufu.currencyvalues.job;

import com.github.rodoufu.currencyvalues.TickerData;
import com.github.rodoufu.currencyvalues.bean.CryptoTicker;
import com.github.rodoufu.currencyvalues.bean.FiatTicker;
import com.github.rodoufu.currencyvalues.dao.BitcoinPriceDAO;
import com.github.rodoufu.currencyvalues.entity.BitcoinPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Retrieves the data of biticoin and fiat currencies, then calculate the price of biticoin on every fiat currency.
 */
@Component
public class BitcoinPriceJob {
    private BitcoinPriceDAO bitcoinPriceDAO;
    private TickerData tickerData;

    @Autowired
    public BitcoinPriceJob(BitcoinPriceDAO bitcoinPriceDAO, TickerData tickerData) {
        this.bitcoinPriceDAO = bitcoinPriceDAO;
        this.tickerData = tickerData;
    }

    /**
     * Job to calculate the bitcoin prices and save the data.
     *
     * @throws RestClientException  Problem reaching the REST service.
     * @throws InterruptedException Problem interrupting the thread.
     * @throws ExecutionException   Problem obtaining the values from the futures.
     */
    @Scheduled(fixedDelayString = "${bitcoin.price.job.interval}")
    public void scheduleFetchAndSaveBitcoinPrice() throws RestClientException, InterruptedException, ExecutionException {
        final CompletableFuture<FiatTicker> fiatTickerFuture = getTickerData().getFiatTicker();
        final CompletableFuture<CryptoTicker> cryptoTickerFuture = getTickerData().getCryptoTicker();

        CompletableFuture.allOf(fiatTickerFuture, cryptoTickerFuture).join();

        saveBitcoinPrice(calculateBitcoinPrice(cryptoTickerFuture.get(), fiatTickerFuture.get()));
    }

    /**
     * Generate a list of bitcoin prices for a crypto and fiat tickers.
     *
     * @param cryptoTicker Crypto ticker.
     * @param fiatTicker   Fiat ticker.
     * @return The list.
     */
    public List<BitcoinPrice> calculateBitcoinPrice(CryptoTicker cryptoTicker, FiatTicker fiatTicker) {
        final Calendar dateNow = Calendar.getInstance();
        final BigDecimal buyValue = cryptoTicker.getBuy();
        return fiatTicker.getRates().entrySet().stream().map(entry -> new BitcoinPrice(entry.getKey(), buyValue.multiply(entry.getValue()), dateNow))
                .collect(Collectors.toList());
    }

    /**
     * Saves the bitcoin prices in the database.
     *
     * @param bitcoinPrices Bitcoin prices.
     */
    @Transactional
    public void saveBitcoinPrice(Collection<BitcoinPrice> bitcoinPrices) {
        bitcoinPrices.forEach(bitcoinPrice -> getBitcoinPriceDAO().save(bitcoinPrice));
    }

    public TickerData getTickerData() {
        return tickerData;
    }

    public BitcoinPriceDAO getBitcoinPriceDAO() {
        return bitcoinPriceDAO;
    }

}
