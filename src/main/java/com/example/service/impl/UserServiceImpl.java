package com.example.service.impl;

import com.example.data.dto.UserDto;
import com.example.service.UserService;
import com.example.share.exception.service.ServiceNotFoundException;
import com.example.share.exception.service.ServiceValidationException;
import com.example.share.util.mapper.EntityDtoMapper;
import com.example.store.entity.User;
import com.example.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EntityDtoMapper mapper;

    @Override
    public UserDto create(UserDto userDto) {
        checkUserNull(userDto);
        User user = userRepository.save(mapper.mapToUser(userDto));
        return mapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(mapper::mapToUserDto).toList();
    }

    @Override
    public UserDto get(Long id) {
        checkId(id);
        return userRepository.findById(id)
                .map(mapper::mapToUserDto)
                .orElseThrow(() -> new ServiceNotFoundException("User with id: " + id + " doesn't exist"));
    }

    @Override
    public UserDto update(UserDto userDto) {
        checkUserNull(userDto);
        checkId(userDto.getId());
        User user = userRepository.save(mapper.mapToUser(userDto));
        return mapper.mapToUserDto(user);
    }

    @Override
    public void delete(Long id) {
        checkId(id);
        userRepository.findById(id).orElseThrow(() -> new ServiceNotFoundException("User with id: " + id + " doesn't exist"));
        userRepository.deleteById(id);
    }

    private void checkUserNull(UserDto userDto) {
        if (userDto == null) {
            throw new ServiceValidationException("User is null");
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
