package com.artmcoder.spreadsearch.api.repository;

import com.artmcoder.spreadsearch.api.models.CryptocurrencyPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CryptocurrencyPairRepository extends JpaRepository<CryptocurrencyPair, Integer> {
    Optional<CryptocurrencyPair> findByFirstCrypto(String string);
}
