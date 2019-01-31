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

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

@Component
public class BitcoinPriceJob {
	@Autowired
	private BitcoinPriceDAO bitcoinPriceDAO;
	@Autowired
	private TickerData getDataJob;

	@Scheduled(fixedDelayString = "${student.job.interval}")
	public void scheduleFixedRateTask() throws RestClientException, URISyntaxException, InterruptedException, ExecutionException {
	}

	public Collection<BitcoinPrice> calculateBitcoinPrice(CryptoTicker cryptoTicker, FiatTicker fiatTicker) {
		return Collections.emptyList();
	}

}
