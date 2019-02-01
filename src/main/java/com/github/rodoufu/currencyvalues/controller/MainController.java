package com.github.rodoufu.currencyvalues.controller;

import com.github.rodoufu.currencyvalues.dao.BitcoinPriceDAO;
import com.github.rodoufu.currencyvalues.entity.BitcoinPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

/**
 * Application main controller.
 */
@Controller
public class MainController {
	private BitcoinPriceDAO bitcoinPriceDAO;

	@Autowired
	public MainController(BitcoinPriceDAO bitcoinPriceDAO)  {
		this.bitcoinPriceDAO = bitcoinPriceDAO;
	}

	/**
	 * @return The top 10 Bitcoin prices.
	 */
	@ResponseBody
	@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<BitcoinPrice> index() {
//		return bitcoinPriceDAO.findTop10ByOrderByDateDesc(); // Not a good approach without an index
		return bitcoinPriceDAO.findTop10ByOrderByIdDesc();
	}

}
