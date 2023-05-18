package com.example.service;

import java.util.List;

public interface AbstractService<T, K> {

    T create(T t);

    List<T> getAll();

    T get(K id);

    T update(T t);

    void delete(K id);

}
