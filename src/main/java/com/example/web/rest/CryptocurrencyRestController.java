package com.example.web.rest;

import com.example.data.dto.CryptoSymbolDto;
import com.example.data.dto.CryptocurrencyDto;
import com.example.data.dto.UserDto;
import com.example.service.CryptocurrencyService;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/crypto")
@RequiredArgsConstructor
public class CryptocurrencyRestController {

    private final CryptocurrencyService cryptoCurrencyService;
    private final UserService userService;

    @GetMapping("/{symbol}")
    public CryptocurrencyDto getBySymbol(@PathVariable String symbol) {
        return cryptoCurrencyService.getBySymbol(CryptoSymbolDto.valueOf(symbol));
    }

    @GetMapping
    public List<CryptocurrencyDto> getAll() {
        return cryptoCurrencyService.getAll();
    }

    //curl -d "username=Toster&symbol=SOL" http://localhost:8080/api/crypto/notify
    @PostMapping("/notify")
    public UserDto register(@RequestParam("username") String username, @RequestParam("symbol") String symbol) {
        UserDto user = new UserDto();
        user.setUsername(username);
        user.setCryptocurrency(cryptoCurrencyService.getBySymbol(CryptoSymbolDto.valueOf(symbol)));
        System.out.println(LocalDate.now());
        user.setRegisterDate(LocalDate.now());
        user.setPriceAtRegister(user.getCryptocurrency().getPriceUSD());
        return userService.create(user);
    }
}