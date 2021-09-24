package com.testtaskinfotecs.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "testdb")
public class KeyValue {

    @Id
    @Column(name = "key")
    private String key;
    @Column(name = "value")
    private String value;
    @Column(name = "ttl")
    private Long ttl;

    public KeyValue() {
    }

    public KeyValue(String key, String value, Long ttl) {
        this.key = key;
        this.value = value;
        this.ttl = ttl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public boolean checkTime(){
        return this.ttl >= System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyValue keyValue = (KeyValue) o;
        return Objects.equals(key, keyValue.key) && Objects.equals(value, keyValue.value) && Objects.equals(ttl, keyValue.ttl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value, ttl);
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
