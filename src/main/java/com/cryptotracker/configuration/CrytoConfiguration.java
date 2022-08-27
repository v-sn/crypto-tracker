package com.cryptotracker.configuration;

import com.cryptotracker.service.CryptoTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableScheduling
public class CrytoConfiguration {

    Logger logger = LoggerFactory.getLogger(CryptoTrackerService.class);

    @Autowired
    private Environment environment;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        try {
            mailSender.setHost(environment.getProperty("host"));
            mailSender.setPort(Integer.parseInt(Objects.requireNonNull(environment.getProperty("port"))));

            mailSender.setUsername(environment.getProperty("username"));
            mailSender.setPassword(environment.getProperty("password"));

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");
        }
        catch (Exception e ){
            logger.info("Set the required enviornment varibales");
        }
        return mailSender;
    }
}
