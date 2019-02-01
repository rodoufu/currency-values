package com.github.rodoufu.currencyvalues.dao;

import com.github.rodoufu.currencyvalues.entity.BitcoinPrice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Bitcoin price persistence.
 */
@Repository
public interface BitcoinPriceDAO extends CrudRepository<BitcoinPrice, Long> {
    /**
     * @return The top 10 Bitcoin prices.
     */
    List<BitcoinPrice> findTop10ByOrderByDateDesc();
}
