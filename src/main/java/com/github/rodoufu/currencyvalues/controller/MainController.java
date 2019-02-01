package com.github.rodoufu.currencyvalues.controller;

import com.github.rodoufu.currencyvalues.dao.BitcoinPriceDAO;
import com.github.rodoufu.currencyvalues.entity.BitcoinPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Application main controller.
 */
@Controller
public class MainController {
	@Autowired
	private BitcoinPriceDAO bitcoinPriceDAO;

	/**
	 * @return The top 10 Bitcoin prices.
	 */
	@ResponseBody
	@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<BitcoinPrice> index() {
		return bitcoinPriceDAO.findTop10ByOrderByIdDesc();
	}

}
