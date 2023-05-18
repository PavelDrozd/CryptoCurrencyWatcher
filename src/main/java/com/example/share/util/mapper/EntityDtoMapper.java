package com.example.share.util.mapper;

import com.example.data.dto.CryptocurrencyDto;
import com.example.data.dto.UserDto;
import com.example.store.entity.Cryptocurrency;
import com.example.store.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EntityDtoMapper {

    private final ModelMapper mapper;

    public UserDto mapToUserDto(User user) {
        return mapper.map(user, UserDto.class);
    }

    public User mapToUser(UserDto userDto) {
        return mapper.map(userDto, User.class);
    }

    public CryptocurrencyDto mapToCryptocurrencyDto(Cryptocurrency cryptocurrency) {
        return mapper.map(cryptocurrency, CryptocurrencyDto.class);
    }

    public Cryptocurrency mapToCryptocurrency(CryptocurrencyDto cryptocurrencydto) {
        return mapper.map(cryptocurrencydto, Cryptocurrency.class);
    }

}
