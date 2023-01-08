package com.artmcoder.spreadsearch.service;


import com.artmcoder.spreadsearch.api.models.CryptocurrencyPair;
import com.artmcoder.spreadsearch.api.models.Exchange;
import com.artmcoder.spreadsearch.api.parsers.*;
import com.artmcoder.spreadsearch.api.repository.CryptocurrencyPairRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("all")
public class CryptocurrencyPairService {
    private final CryptocurrencyPairRepository cryptocurrencyPairRepository;
    private final Binance binanceParser;
    private final KuCoin kuCoinParser;
    private final LBank lBankParser;
    private final Mexc mexcParser;
    private final Huobi huobiParser;
    private final Bybit bybitParser;
    private final Exchange exchange;


    public CryptocurrencyPair save(){
        CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();

        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();

        List<CryptocurrencyPair> binanceCryptocurrency = binanceParser.parser();
        cryptocurrencyPairs.addAll(binanceCryptocurrency);
        log.info("Binance parsing completed. Count of coins: {}", binanceCryptocurrency.size());

        List<CryptocurrencyPair> kuCoinCryptocurrency = kuCoinParser.parser();
        cryptocurrencyPairs.addAll(kuCoinCryptocurrency);
        log.info("KuCoin parsing completed. Count of coins: {}", kuCoinCryptocurrency.size());

        List<CryptocurrencyPair> lBankCryptocurrency = lBankParser.parser();
        cryptocurrencyPairs.addAll(lBankCryptocurrency);
        log.info("LBank parsing completed. Count of coins: {}", lBankCryptocurrency.size());

        List<CryptocurrencyPair> mexcCryptocurrency = mexcParser.parser();
        cryptocurrencyPairs.addAll(mexcCryptocurrency);
        log.info("Mexc parsing completed. Count of coins: {}", mexcCryptocurrency.size());

        List<CryptocurrencyPair> huobiCryptocurrency = huobiParser.parser();
        cryptocurrencyPairs.addAll(huobiCryptocurrency);
        log.info("Huobi parsing completed. Count of coins: {}", huobiCryptocurrency.size());

        List<CryptocurrencyPair> bybitCryptocurrency = bybitParser.parser();
        cryptocurrencyPairs.addAll(bybitCryptocurrency);


        log.info("ByBit parsing completed. Count of coins: {}", bybitCryptocurrency.size());

        for (int i = 0; i < 313; i++) {
            cryptocurrencyPair.setExchange(cryptocurrencyPairs.get(i).getExchange());
            cryptocurrencyPair.setFirstCrypto(cryptocurrencyPairs.get(i).getFirstCrypto());
            cryptocurrencyPair.setSecondCrypto(cryptocurrencyPairs.get(i).getSecondCrypto());
            cryptocurrencyPair.setAmount(cryptocurrencyPairs.get(i).getAmount());
        }

        return cryptocurrencyPairRepository.save(cryptocurrencyPair);
    }

    public List<Optional<CryptocurrencyPair>> cryptocurrencyPairsSearch(){
        List<Optional<CryptocurrencyPair>> foundedPairs = new ArrayList<>();
        Optional<CryptocurrencyPair> foundedCurrency = null;
        for (int i = 0; i < 1; i++) {
            String firstCrypto = binanceParser.parser().get(i).getFirstCrypto();
            foundedCurrency = cryptocurrencyPairRepository.findByFirstCrypto(firstCrypto);
        }

        foundedPairs.add(foundedCurrency);

        for (int i = 0; i < foundedPairs.size(); i++) {
            if (foundedPairs.get(i).get().getAmount() == Integer.MIN_VALUE) {
                exchange.setBuyExchange(foundedPairs.get(0).get().getExchange());

            }
        }

        return foundedPairs;
    }
}
