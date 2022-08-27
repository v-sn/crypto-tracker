package com.cryptotracker.service;

import com.cryptotracker.api.CryptoTrackerResource;
import com.cryptotracker.dto.CryptoPriceDTO;
import com.cryptotracker.dto.CryptoPriceEmail;
import com.cryptotracker.dto.PaginatedResponse;
import com.cryptotracker.model.CryptoPrice;
import com.cryptotracker.repository.CryptoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.cryptotracker.util.CryptoTrackerContants.*;

/**
 * @author vsn
 *
 */

/**
 * Service layer for the CryptoTracker price
 * Actions : insert , check-alerting , get
 *
 */
@Service
public class CryptoTrackerService {

    Logger logger = LoggerFactory.getLogger(CryptoTrackerService.class);

    @Autowired
    private CryptoRepository cryptoRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private EmailServiceImpl emailService;


    /**
     * Get the Bitcoin crypto prices
     * @param date the date to fetch that particular day prices
     * @param offset the start index of the page /respose
     * @param limit the size of the page / reponse
     *
     * @return List of Bitcoin price with paginate audit fields
     */
    public PaginatedResponse getCryptoPrice( LocalDate date, int offset, int limit){
        LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIDNIGHT);
        LocalDateTime endOfDate = LocalTime.MAX.atDate(date);
        PaginatedResponse response = new PaginatedResponse();


        List<CryptoPrice> priceList= cryptoRepository.getCryptoPriceByTimestamp(Timestamp.valueOf(startOfDay),
                Timestamp.valueOf(endOfDate));
        response.setCount(priceList.size());
        response.setData(priceList.subList((int)Math.min(priceList.size(), offset),
                (int)Math.min(priceList.size(), offset + limit)).stream().map( price ->new CryptoPriceDTO(price.getTimestamp(),
                price.getPrice(), price.getCoin()) ).collect(Collectors.toList()));
        return response;
    }

    /**
     * Inserting the Bitcoin crypto prices
     * @param price the price of the Bitcon
     *
     * @return void
     */
    public void insertCryptoPrice(int price){
        CryptoPrice cryptoPrice = new CryptoPrice(price);
        cryptoRepository.save(cryptoPrice);
    }

    /**
     * Monitoring to alert the price b/w MAX and MIN price
     * @param price the price of the Bitcon
     *
     * @return void
     */
    public void checkPriceForAlerting(int price) throws MailException {
        int min_price = Integer.parseInt(Objects.requireNonNull(environment.getProperty(MIN)));
        int max_price = Integer.parseInt(Objects.requireNonNull(environment.getProperty(MAX)));
        CryptoPriceEmail mail = new CryptoPriceEmail();
        mail.setRecipient(environment.getProperty(EMAIL));

        if(price < min_price){
            mail.setSubject(MIN_PRICE_SUBJECT.replace("%s",String.valueOf(price)));
            mail.setMsgBody(MIN_PRICE_BODY.replace("%s",String.valueOf(price)));
            emailService.sendMail(mail);
        }

        if(price > max_price){
            mail.setSubject(MAX_PRICE_SUBJECT.replace("%s",String.valueOf(price)));
            mail.setMsgBody(MAX_PRICE_BODY.replace("%s",String.valueOf(price)));
            emailService.sendMail(mail);
        }
    }


}
