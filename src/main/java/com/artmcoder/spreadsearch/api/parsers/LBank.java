package com.artmcoder.spreadsearch.api.parsers;

import com.artmcoder.spreadsearch.api.models.CryptocurrencyPair;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author John
 */

@Component
@RequiredArgsConstructor
public class LBank {
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


    /**
     * @return All Cryptocurrencys with USDT
     * @throws IOException
     */
    public List<CryptocurrencyPair> usdtParser() throws  IOException{
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();

        Binance binance = new Binance();
        List<CryptocurrencyPair> some = binance.usdtParser();
        for (CryptocurrencyPair binanceData : some) {
            try {
                CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
                cryptocurrencyPair.setExchange("LBank");
                cryptocurrencyPair.setFirstCrypto(binanceData.getFirstCrypto());
                String a = binanceData.getFirstCrypto();
                cryptocurrencyPair.setAmount(Double.valueOf(getAmount(a.replace(a, a.toLowerCase() + "_usdt"))));
                cryptocurrencyPair.setSecondCrypto("USDT");
                cryptocurrencyPairs.add(cryptocurrencyPair);
            }catch (NullPointerException e){
                continue;
            }
        }
        return cryptocurrencyPairs;
    }


    /**
     * @return All Cryptocurrencys with BTC
     * @throws IOException
     */
    public List<CryptocurrencyPair> btcParser() throws  IOException{
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();

        Binance binance = new Binance();
        List<CryptocurrencyPair> some = binance.btcParser();
        for (CryptocurrencyPair binanceData : some) {
            try {
                CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
                cryptocurrencyPair.setExchange("LBank");
                cryptocurrencyPair.setFirstCrypto(binanceData.getFirstCrypto());
                String a = binanceData.getFirstCrypto();
                cryptocurrencyPair.setAmount(Double.valueOf(getAmount(a.replace(a, a.toLowerCase() + "_btc"))));
                cryptocurrencyPair.setSecondCrypto("BTC");
                cryptocurrencyPairs.add(cryptocurrencyPair);
            }catch (NullPointerException e){
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
    * <h3>Парсит переданную связь в параментры(Например: btc_usdt) из биржи LBank</h3>
    * @param symbol Имя связки, которую нужно спарсить
    * @return Имя связи и цена
    * @throws JsonProcessingException
    */
    public static String getOneSymbolFromLbank(String symbol) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String fuckingRequest = "https://api.lbkex.com/v2/ticker/24hr.do?symbol=" + symbol;
        String fuckingResponse = restTemplate.getForObject(fuckingRequest, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode object = objectMapperForMexc.readTree(fuckingResponse);
        return String.valueOf(object.get("data").get(0).get("symbol"));
    }

    /**
     * @param symbol Имя связки
     * @return Цена связки, переданная в параметры
     * @throws JsonProcessingException
     */
    public static String getAmount(String symbol) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String request = "https://api.lbkex.com/v2/ticker/24hr.do?symbol=" + symbol;
        String response = restTemplate.getForObject(request, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode object = objectMapperForMexc.readTree(response);
        return String.valueOf(object.get("data").get(0).get("ticker").get("latest").textValue());
    }
}
