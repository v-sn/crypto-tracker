package com.cryptotracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CryptoPriceEmail {


    private String recipient;
    private String msgBody;
    private String subject;
}
