package com.artmcoder.spreadsearch.api.parsers;

import com.artmcoder.spreadsearch.api.models.CryptocurrencyPair;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author John
 */

@Component
public class Bybit {
    public List<CryptocurrencyPair> parser() {
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();
        try {
            cryptocurrencyPairs.addAll(usdtParser());
            cryptocurrencyPairs.addAll(btcParser());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cryptocurrencyPairs;
    }

    public List<CryptocurrencyPair> usdtParser() throws IOException{
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        String req = "https://api-testnet.bybit.com/v2/public/tickers";
        String response = restTemplate.getForObject(req, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode obj = objectMapperForMexc.readTree(response);

        for(int i = 0; i < 195; i++) {
            CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
            cryptocurrencyPair.setExchange("ByBit");

            String firstCrypto = obj.get("result").get(i).get("symbol").textValue();
            boolean isSecondCryptoUSDT = firstCrypto.contains("USDT");
            if (isSecondCryptoUSDT){
                cryptocurrencyPair.setFirstCrypto(firstCrypto.replace("USDT", ""));
                cryptocurrencyPair.setAmount(Double.valueOf(obj.get("result").get(i).get("index_price").textValue()));
                cryptocurrencyPair.setSecondCrypto("USDT");

                cryptocurrencyPairs.add(cryptocurrencyPair);
            }else {
                continue;
            }
        }

        return cryptocurrencyPairs;
    }

    public List<CryptocurrencyPair> btcParser() throws IOException{
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        String req = "https://api-testnet.bybit.com/v2/public/tickers";
        String response = restTemplate.getForObject(req, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode obj = objectMapperForMexc.readTree(response);

        for(int i = 0; i < 195; i++) {
            CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
            cryptocurrencyPair.setExchange("ByBit");

            String firstCrypto = obj.get("result").get(i).get("symbol").textValue();
            boolean isSecondCryptoUSDT = firstCrypto.contains("BTC");
            if (isSecondCryptoUSDT){
                cryptocurrencyPair.setFirstCrypto(firstCrypto.replace("BTC", ""));
                cryptocurrencyPair.setAmount(Double.valueOf(obj.get("result").get(i).get("index_price").textValue()));
                cryptocurrencyPair.setSecondCrypto("BTC");

                cryptocurrencyPairs.add(cryptocurrencyPair);
            }else {
                continue;
            }
        }
        return cryptocurrencyPairs;
    }



}