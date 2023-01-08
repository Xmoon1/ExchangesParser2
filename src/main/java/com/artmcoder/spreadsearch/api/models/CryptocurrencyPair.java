package com.artmcoder.spreadsearch.api.models;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "cryptocurrency_pair")
public class CryptocurrencyPair {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String exchange;
    private String firstCrypto;
    private String secondCrypto;
    private Double amount;
}
