package com.example.store.entity.converter;

import com.example.store.entity.CryptoSymbol;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CryptoSymbolConverter implements AttributeConverter<CryptoSymbol, String> {
    @Override
    public String convertToDatabaseColumn(CryptoSymbol attribute) {
        return attribute.toString().toUpperCase();
    }

    @Override
    public CryptoSymbol convertToEntityAttribute(String dbData) {
        return CryptoSymbol.valueOf(dbData.toUpperCase());
    }
}
