package com.example.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptocurrencyDto {

    private Long id;

    private CryptoSymbolDto symbol;

    private String name;

    private Double priceUSD;

}
