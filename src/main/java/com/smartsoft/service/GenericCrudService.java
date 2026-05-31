package com.smartsoft.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class GenericCrudService {

    private final Map<String, Map<UUID, Object>> store = new ConcurrentHashMap<>();

    public <T> List<T> list(String key) {
        return new ArrayList<>((Collection<T>) store.computeIfAbsent(key, k -> new ConcurrentHashMap<>()).values());
    }

    public <T> T save(String key, UUID id, T value) {
        store.computeIfAbsent(key, k -> new ConcurrentHashMap<>()).put(id, value);
        return value;
    }

    public void delete(String key, UUID id) {
        store.computeIfAbsent(key, k -> new ConcurrentHashMap<>()).remove(id);
    }
}
