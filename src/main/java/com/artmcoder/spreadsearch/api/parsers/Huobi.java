package com.artmcoder.spreadsearch.api.parsers;

import com.artmcoder.spreadsearch.api.models.CryptocurrencyPair;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author John
 */

@Component
public class Huobi {
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

    public List<CryptocurrencyPair> usdtParser() throws IOException {
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();
        Binance binance = new Binance();

        List<CryptocurrencyPair> binanceParser = binance.usdtParser();
        for (CryptocurrencyPair binanceData : binanceParser) {
            try{
                CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
                cryptocurrencyPair.setExchange("Huobi");
                cryptocurrencyPair.setFirstCrypto(binanceData.getFirstCrypto());
                cryptocurrencyPair.setSecondCrypto("USDT");
                String a = binanceData.getFirstCrypto();
                cryptocurrencyPair.setAmount(Double.valueOf(getAmount(a.replace(a, a.toLowerCase() + "usdt"))));
                cryptocurrencyPairs.add(cryptocurrencyPair);
            }catch (NullPointerException e){
                continue;
            }

        }

        return cryptocurrencyPairs;
    }


    public List<CryptocurrencyPair> btcParser() throws IOException {
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();
        Binance binance = new Binance();

        List<CryptocurrencyPair> binanceParser = binance.btcParser();
        for (CryptocurrencyPair binanceData : binanceParser) {
            try {
                CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
                cryptocurrencyPair.setExchange("Huobi");
                cryptocurrencyPair.setFirstCrypto(binanceData.getFirstCrypto());
                cryptocurrencyPair.setSecondCrypto("BTC");
                String a = binanceData.getFirstCrypto();
                cryptocurrencyPair.setAmount(Double.valueOf(getAmount(a.replace(a, a.toLowerCase() + "btc"))));
                cryptocurrencyPairs.add(cryptocurrencyPair);
            } catch (NullPointerException e) {
                continue;
            }
        }

        return cryptocurrencyPairs;
    }


    /**
     * @param requestLink Принимает ссылку на GET запрос для получение всех связок
     * @return название всех существующих пар
     */
    public static String getAllCurrencyPairs(String requestLink){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(requestLink, String.class);
    }


    /**
     * <h3>Парсит переданную связь в параментры(Например: btcusdt) из биржи Huobi</h3>
     * @param symbol Имя связки, которую нужно спарсить
     * @return Имя связи и цена
     * @throws JsonProcessingException
     */
    public static String getOneSymbolFromHuobi(String symbol) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String req = "https://api.huobi.pro/market/trade?symbol=" + symbol;
        String response = restTemplate.getForObject(req, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode obj = objectMapperForMexc.readTree(response);
        return symbol;
    }

    public static String getAmount(String symbol) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String req = "https://api.huobi.pro/market/trade?symbol=" + symbol;
        String response = restTemplate.getForObject(req, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode obj = objectMapperForMexc.readTree(response);
        return String.valueOf(obj.get("tick").get("data").get(0).get("price"));
    }
}