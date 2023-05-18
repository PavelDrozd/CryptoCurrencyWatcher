package com.example.service.impl;

import com.example.data.dto.CryptoSymbolDto;
import com.example.data.dto.CryptocurrencyDto;
import com.example.data.dto.UserDto;
import com.example.service.CryptocurrencyService;
import com.example.share.exception.service.ServiceNotFoundException;
import com.example.share.exception.service.ServiceValidationException;
import com.example.share.util.inspector.PriceInspector;
import com.example.share.util.mapper.EntityDtoMapper;
import com.example.store.entity.CryptoSymbol;
import com.example.store.entity.Cryptocurrency;
import com.example.store.repository.CryptocurrencyRepository;
import com.example.store.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CryptocurrencyServiceImpl implements CryptocurrencyService {

    private final CryptocurrencyRepository cryptoCurrencyRepository;
    private final UserRepository userRepository;
    private final EntityDtoMapper mapper;
    private final RestTemplate restTemplate;
    private final PriceInspector inspector;

    private Map<UserDto, CryptocurrencyDto> map;

    private static final String COINLORE_API_URL = "https://api.coinlore.net/api/";

    @Override
    public CryptocurrencyDto create(CryptocurrencyDto cryptoCurrencyDto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<CryptocurrencyDto> getAll() {
        return cryptoCurrencyRepository.findAll().stream().map(mapper::mapToCryptocurrencyDto).toList();
    }

    @Override
    public CryptocurrencyDto get(Long id) {
        checkId(id);
        return cryptoCurrencyRepository.findById(id)
                .map(mapper::mapToCryptocurrencyDto)
                .orElseThrow(() -> new ServiceNotFoundException("Crypto currency with id: " + id + " doesn't exist"));
    }

    @Override
    public CryptocurrencyDto update(CryptocurrencyDto cryptoCurrencyDto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("It is forbidden to delete cryptocurrency");
    }

    @Override
    public CryptocurrencyDto getBySymbol(CryptoSymbolDto symbolDto) {
        checkSymbol(symbolDto);
        return cryptoCurrencyRepository.findBySymbol(CryptoSymbol.valueOf(symbolDto.toString()))
                .map(mapper::mapToCryptocurrencyDto)
                .orElseThrow(() -> new ServiceNotFoundException(
                        "Crypto currency with symbol: " + symbolDto + " doesn't exist"));
    }

    @Override
    public CryptocurrencyDto updatePriceBySymbol(CryptoSymbolDto symbolDto) {
        CryptocurrencyDto cryptocurrencyDto = getBySymbol(symbolDto);
        String url = COINLORE_API_URL + "ticker/?id=" + cryptocurrencyDto.getId();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = getJsonNode(response, objectMapper);
        return mapper.mapToCryptocurrencyDto(getUpdatedCryptocurrency(cryptocurrencyDto, root));
    }

    @Scheduled(fixedRate = 60000)
    @Override
    public List<CryptocurrencyDto> updatePrices() {
        List<CryptocurrencyDto> cryptocurrencyDtos = getAll();
        List<Cryptocurrency> updatedCryptocurrencies = cryptocurrencyDtos.stream()
                .map(cryptocurrencyDto -> updatePriceBySymbol(cryptocurrencyDto.getSymbol()))
                .map(mapper::mapToCryptocurrency)
                .toList();
        List<Cryptocurrency> cryptocurrencies = cryptoCurrencyRepository.saveAll(updatedCryptocurrencies);
        inspectActualPrices();
        return cryptocurrencies.stream().map(mapper::mapToCryptocurrencyDto).toList();
    }

    private void inspectActualPrices() {
        inspector.inspectPrices(userRepository.findAll().stream().map(mapper::mapToUserDto).toList());
    }

    private Cryptocurrency getUpdatedCryptocurrency(CryptocurrencyDto cryptocurrencyDto, JsonNode root) {
        Cryptocurrency cryptocurrency = mapper.mapToCryptocurrency(cryptocurrencyDto);
        if (root != null) {
            JsonNode priceUSDNode = root.get(0).get("price_usd");
            cryptocurrency.setPriceUSD(priceUSDNode.asDouble());
        }
        return cryptocurrency;
    }

    private JsonNode getJsonNode(ResponseEntity<String> response, ObjectMapper objectMapper) {
        JsonNode root;
        try {
            root = objectMapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new ServiceValidationException(e);
        }
        return root;
    }

    private void checkSymbol(CryptoSymbolDto symbolDto) {
        if (symbolDto == null) {
            throw new ServiceValidationException("Symbol is null");
        }
    }

    private void checkId(Long id) {
        if (id == null) {
            throw new ServiceValidationException("ID is null");
        }
        if (id < 0L) {
            throw new ServiceValidationException("ID less than 0");
        }
    }


}
