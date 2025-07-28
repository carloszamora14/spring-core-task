package com.example.springcore.storage;

import com.example.springcore.model.Identifiable;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryStorageImpl<T extends Identifiable> implements InMemoryStorage<T> {

    private final Map<Long, T> storage = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public T save(T entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.getAndIncrement());
        }

        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public T deleteById(Long id) {
        return storage.remove(id);
    }
}
