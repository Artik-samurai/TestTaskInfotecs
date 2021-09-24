package com.testtaskinfotecs.service;

import com.testtaskinfotecs.entity.KeyValue;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceKeyValue {
    public KeyValue getValue(String key);
    public String setValue(String key, String value, Long ttl);
    public KeyValue removeValue(String key);
    public List<KeyValue> getAllValue();
    public String dumpKeyValue();
    public void loadKeyValue();
    public void checkTime();
}
