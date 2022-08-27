package com.cryptotracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.cryptotracker.util.CryptoTrackerContants.*;

@EnableAsync
@Component
public class CryptoPriceFetcher {

    Logger logger = LoggerFactory.getLogger(CryptoPriceFetcher.class);

    private  RestTemplate restTemplate;

    @Autowired
    private  CryptoTrackerService cryptoTrackerService;

    public CryptoPriceFetcher(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Scheduled(cron="*/30 * * * * *")
    public void scheduleCryptoPriceTracker() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode node =objectMapper.readValue(restTemplate.getForObject(
                    CRYPTO_PRICE_URI, String.class),
                    JsonNode.class);
            JsonNode bitcoin_node= node.path(BITCOIN);
            if(bitcoin_node != null){
                cryptoTrackerService.insertCryptoPrice(bitcoin_node.get(USD).asInt());
                cryptoTrackerService.checkPriceForAlerting(bitcoin_node.get(USD).asInt());
            }
        } catch (JsonProcessingException e) {
            logger.info("Exception on processing the Bitcon Result" + e.getMessage());
        }
        catch ( MailException e){
            logger.info("Exception on sending alert email on the Price" + e.getMessage());
        }
    }
}
