package com.example.springcore.storage;

import java.util.List;
import java.util.Optional;

public interface InMemoryStorage<T> {
    T save(T entity);
    List<T> findAll();
    Optional<T> findById(Long id);
    T deleteById(Long id);
}
