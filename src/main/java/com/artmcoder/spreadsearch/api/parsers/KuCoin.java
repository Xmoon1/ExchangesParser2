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

@Component
@SuppressWarnings("all")
public class KuCoin {

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
     * @return <h3>All Cryptocurrencys with USDT </h3>
     * @throws JsonProcessingException
     */
    public List<CryptocurrencyPair> usdtParser() throws IOException {
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();
        Binance binance = new Binance();
        List<CryptocurrencyPair> binanceParser = binance.usdtParser();
        for (CryptocurrencyPair binanceData : binanceParser) {
            try {
                CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
                cryptocurrencyPair.setExchange("KuCoin");
                cryptocurrencyPair.setFirstCrypto(binanceData.getFirstCrypto());
                cryptocurrencyPair.setSecondCrypto("USDT");
                String a = binanceData.getFirstCrypto();
                cryptocurrencyPair.setAmount(Double.valueOf(getAmount(a.replace(a, a + "-USDT"))));
                cryptocurrencyPairs.add(cryptocurrencyPair);
            } catch (NullPointerException e) {
                continue;
            }
        }

        return cryptocurrencyPairs;
    }

    /**
     * @return <h3>All Cryptocurrencys with BTC </h3>
     * @throws JsonProcessingException
     */

    public List<CryptocurrencyPair> btcParser() throws IOException {
        List<CryptocurrencyPair> cryptocurrencyPairs = new ArrayList<>();
        Binance binance = new Binance();
        List<CryptocurrencyPair> binanceParser = binance.btcParser();
        for (CryptocurrencyPair binanceData : binanceParser) {
            try {
                CryptocurrencyPair cryptocurrencyPair = new CryptocurrencyPair();
                cryptocurrencyPair.setExchange("KuCoin");
                cryptocurrencyPair.setFirstCrypto(binanceData.getFirstCrypto());
                cryptocurrencyPair.setSecondCrypto("BTC");
                String a = binanceData.getFirstCrypto();
                cryptocurrencyPair.setAmount(Double.valueOf(getAmount(a.replace(a, a + "-BTC"))));
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
    * <h3>Парсит переданную связь в параментры(Например: btc_usdt) из биржи KuCoin</h3>
    * @param symbol Имя связки, которую нужно спарсить
    * @return Имя связки и цена
    * @throws JsonProcessingException
    */
    public static String getOneSymbolFromKuCoin(String symbol) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String req = "https://api.kucoin.com/api/v1/market/orderbook/level2_20?symbol=" + symbol;
        String response = restTemplate.getForObject(req, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode obj = objectMapperForMexc.readTree(response);
        return symbol;
    }

    /**
     * @param symbol Cryptocurrency name
     * @return Price of cryptocurrency
     * @throws JsonProcessingException
     */
    public static String getAmount(String symbol) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String req = "https://api.kucoin.com/api/v1/market/orderbook/level2_20?symbol=" + symbol;
        String response = restTemplate.getForObject(req, String.class);
        ObjectMapper objectMapperForMexc = new ObjectMapper();
        JsonNode obj = objectMapperForMexc.readTree(response);
        return obj.get("data").get("asks").get(0).get(0).textValue();
    }

}