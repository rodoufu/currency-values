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
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Component
public class BitcoinPriceJob {
	@Autowired
	private BitcoinPriceDAO bitcoinPriceDAO;
	@Autowired
	private TickerData getDataJob;

//	@Scheduled(fixedDelayString = "${student.job.interval}")
	public void scheduleFixedRateTask() throws RestClientException, URISyntaxException, InterruptedException {
		final Future<FiatTicker> fiatTicker = getDataJob.getFiatTicker();
		final Future<CryptoTicker> cryptoTicker = getDataJob.getCryptoTicker();
	}

	public List<BitcoinPrice> calculateBitcoinPrice(CryptoTicker cryptoTicker, FiatTicker fiatTicker) {
		final Calendar dateNow = Calendar.getInstance();
		return fiatTicker.getRates().entrySet().stream()
			.map(entry -> new BitcoinPrice(entry.getKey(), cryptoTicker.getBuy().multiply(entry.getValue()), dateNow))
			.collect(Collectors.toList());
	}

	@Transactional
	public void saveBitcoinPrice(List<BitcoinPrice> bitcoinPrices) {
		bitcoinPrices.forEach(bitcoinPrice -> bitcoinPriceDAO.save(bitcoinPrice));
	}

}
