package com.artmcoder.spreadsearch.api;

import com.artmcoder.spreadsearch.api.models.CryptocurrencyPair;
import com.artmcoder.spreadsearch.api.models.Exchange;
import com.artmcoder.spreadsearch.api.parsers.Parser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExchangesSearch {
    private final Parser parser;
    private final Exchange exchange;

    public Exchange searchExchange() {
        List<CryptocurrencyPair> cryptocurrencyPairs = parser.getAllCryptocurrencyPairs();
        return exchange;
    }
}
