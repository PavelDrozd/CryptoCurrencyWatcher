package com.example.store.repository;

import com.example.store.entity.Cryptocurrency;
import com.example.store.entity.CryptoSymbol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CryptocurrencyRepository extends JpaRepository<Cryptocurrency, Long> {

    Optional<Cryptocurrency> findBySymbol(CryptoSymbol symbol);
}
