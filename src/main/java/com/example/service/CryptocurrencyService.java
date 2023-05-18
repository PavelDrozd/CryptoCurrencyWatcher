package com.example.service;

import com.example.data.dto.CryptocurrencyDto;
import com.example.data.dto.CryptoSymbolDto;

import java.util.List;

public interface CryptocurrencyService extends AbstractService<CryptocurrencyDto, Long> {

    CryptocurrencyDto getBySymbol(CryptoSymbolDto symbolDto);

    CryptocurrencyDto updatePriceBySymbol(CryptoSymbolDto symbolDto);

    List<CryptocurrencyDto> updatePrices();
}
