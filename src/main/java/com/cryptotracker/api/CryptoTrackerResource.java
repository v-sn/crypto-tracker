package com.cryptotracker.api;

import com.cryptotracker.dto.PaginatedResponse;
import com.cryptotracker.model.CryptoPrice;
import com.cryptotracker.service.CryptoTrackerService;
import com.cryptotracker.util.PaginatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author vsn
 *
 */


/**
 * Rest Resource to fetch the list of Crypto Prices
 */
@RestController
@RequestMapping(value = "/api/price")
public class CryptoTrackerResource {

    Logger logger = LoggerFactory.getLogger(CryptoTrackerResource.class);
    @Autowired
    private CryptoTrackerService cryptoTrackerService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * Rest Resource to fetch the list of <strong>BITCOIN</strong>  Prices
     * @param date the date to fetch that particular day prices
     * @param offset the start index of the page /respose
     * @param limit the size of the page / reponse
     *
     * @return List of Bitcoin price with paginate audit fields
     */
    @GetMapping("/btc")
    public PaginatedResponse getBitcoinPrice(@RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date,
                                               @RequestParam(name="offset" ,required=false, defaultValue = "0") int offset,
                                               @RequestParam(name="limit", required = false, defaultValue = "100") int limit,
                                               UriComponentsBuilder uriBuilder) {

        Objects.nonNull(date);
        logger.info("Request for Date : %d ",date );
        PaginatedResponse result = cryptoTrackerService.getCryptoPrice( date, offset,limit );
        eventPublisher.publishEvent( new PaginatedEvent<CryptoPrice>
                ( CryptoPrice.class, uriBuilder, result, date, offset,limit ) );
        logger.info("Response for Date : %d ",result.getCount() );
        return result;

    }
}
