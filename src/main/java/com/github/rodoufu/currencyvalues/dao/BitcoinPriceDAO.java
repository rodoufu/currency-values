package com.github.rodoufu.currencyvalues.dao;

import com.github.rodoufu.currencyvalues.entity.BitcoinPrice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BitcoinPriceDAO extends CrudRepository<BitcoinPrice, Long> {
}
