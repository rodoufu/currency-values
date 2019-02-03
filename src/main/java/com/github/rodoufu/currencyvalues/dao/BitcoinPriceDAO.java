package com.github.rodoufu.currencyvalues.dao;

import com.github.rodoufu.currencyvalues.entity.BitcoinPrice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Bitcoin price persistence.
 */
@Repository
public interface BitcoinPriceDAO extends CrudRepository<BitcoinPrice, Long> {
    /** The top 10 Bitcoin prices by date. */
    List<BitcoinPrice> findTop10ByOrderByDateDesc();
    /** The top 10 Bitcoin prices by Id, use primary key index. */
    List<BitcoinPrice> findTop10ByOrderByIdDesc();
    
    @Modifying
    void deleteAll();
}
