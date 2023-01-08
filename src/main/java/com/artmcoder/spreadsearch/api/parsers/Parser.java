package com.artmcoder.spreadsearch.api.parsers;
import com.artmcoder.spreadsearch.api.models.CryptocurrencyPair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("all")
public class Parser {
    private final Binance binanceParser;
    private final KuCoin kuCoinParser;
    private final LBank lBankParser;
    private final Mexc mexcParser;
    private final Huobi huobiParser;
    private final Bybit bybitParser;

    public List<CryptocurrencyPair> getAllCryptocurrencyPairs() {
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

        return cryptocurrencyPairs;
    }

    public static void main(String[] args) {
        long m = System.currentTimeMillis();
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();

        Binance binanceParser = new Binance();
        List<CryptocurrencyPair> binanceCryptocurrency = binanceParser.parser();
        cryptocurrencyPairs.addAll(binanceCryptocurrency);
        log.info("Binance parsing completed. Count of coins: {}", binanceCryptocurrency.size());

        // 1,15 minutes
        KuCoin kuCoinParser = new KuCoin();
        List<CryptocurrencyPair> kuCoinCryptocurrency = kuCoinParser.parser();
        cryptocurrencyPairs.addAll(kuCoinCryptocurrency);
        log.info("KuCoin parsing completed. Count of coins: {}", kuCoinCryptocurrency.size());


        // 0,5 minutes
        LBank lBankParser = new LBank();
        List<CryptocurrencyPair> lBankCryptocurrency = lBankParser.parser();
        cryptocurrencyPairs.addAll(lBankCryptocurrency);
        log.info("LBank parsing completed. Count of coins: {}", lBankCryptocurrency.size());

        // 0,07 minutes
        Mexc mexcParser = new Mexc();
        List<CryptocurrencyPair> mexcCryptocurrency = mexcParser.parser();
        cryptocurrencyPairs.addAll(mexcCryptocurrency);
        log.info("Mexc parsing completed. Count of coins: {}", mexcCryptocurrency.size());


        // 0,94 minutes
        Huobi huobiParser = new Huobi();
        List<CryptocurrencyPair> huobiCryptocurrency = huobiParser.parser();
        cryptocurrencyPairs.addAll(huobiCryptocurrency);
        log.info("Huobi parsing completed. Count of coins: {}", huobiCryptocurrency.size());

        Bybit bybitParser = new Bybit();
        List<CryptocurrencyPair> bybitCryptocurrency = bybitParser.parser();
        cryptocurrencyPairs.addAll(bybitCryptocurrency);
        log.info("ByBit parsing completed. Count of coins: {}", bybitCryptocurrency.size());

        cryptocurrencyPairs.forEach(System.out::println); // 3 minutes
        System.out.println((double) (System.currentTimeMillis() - m));
    }
}