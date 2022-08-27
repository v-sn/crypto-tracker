package com.cryptotracker.repository;

import com.cryptotracker.model.CryptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
/**
 * @author vsn
 *
 */


/**
 * Entity manager layer for CRUD operation / native query fetch
 */
@Repository
public interface CryptoRepository extends JpaRepository<CryptoPrice,Integer> {

        @Query("SELECT u FROM CryptoPrice u WHERE u.timestamp >= ?1 and u.timestamp <= ?2")
        List<CryptoPrice> getCryptoPriceByTimestamp(Timestamp startDate, Timestamp endDate);
}