package com.cryptotracker.model;


import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

import static com.cryptotracker.util.CryptoTrackerContants.BITCOIN_CODE;


@Entity
@Table(name = "crypto_price")
@Data
@ToString
public class CryptoPrice implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @SequenceGenerator(name="car_generator", sequenceName = "car_seq", allocationSize=50)
    private Long id;
    @Column( updatable = false)
    @CreationTimestamp
    Timestamp timestamp;
    @Column
    int price;
    @Column(columnDefinition = "varchar(25) default 'btc'")
    String coin;

    public CryptoPrice(){

    }

    public CryptoPrice(int price) {
        this.timestamp =  new Timestamp(System.currentTimeMillis());
        this.price = price;
        this.coin = BITCOIN_CODE;
    }
}
