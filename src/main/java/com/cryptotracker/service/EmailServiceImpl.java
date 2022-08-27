package com.cryptotracker.service;

import com.cryptotracker.dto.CryptoPriceEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    Logger logger = LoggerFactory.getLogger(CryptoPriceFetcher.class);

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendMail(CryptoPriceEmail email) throws MailException {

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getRecipient());
            message.setTo(email.getRecipient());
            message.setSubject(email.getSubject());
            message.setText(email.getMsgBody());
            emailSender.send(message);
        }
        catch (Exception e){
            logger.info("Exception on sending alert email on the Price" + e.getMessage());
        }

    }

}
