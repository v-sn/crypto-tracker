package com.cryptotracker.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
public class CryptoPriceDTO {

    Timestamp timestamp;
    int price;
    String coin;

}
