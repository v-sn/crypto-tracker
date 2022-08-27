package com.cryptotracker.service;

import com.cryptotracker.dto.CryptoPriceEmail;

public interface EmailService {


    void sendMail(CryptoPriceEmail email);

}
