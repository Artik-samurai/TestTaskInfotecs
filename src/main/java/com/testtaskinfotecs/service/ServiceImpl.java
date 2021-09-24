package com.testtaskinfotecs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtaskinfotecs.entity.KeyValue;
import com.testtaskinfotecs.exception.NotValidValueException;
import com.testtaskinfotecs.repository.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Scanner;

@Service
@Transactional
public class ServiceImpl implements ServiceKeyValue{

    private MyRepository myRepository;

    @Autowired
    public ServiceImpl(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    public ServiceImpl() {
    }

    @Override
    public KeyValue getValue(String key) {
        if (!myRepository.existsById(key)) throw new NotValidValueException("Not find value with key: " + key);
        KeyValue keyValue = new KeyValue(myRepository.getById(key).getKey(),myRepository.getById(key).getValue(),
                myRepository.getById(key).getTtl());
        if (!keyValue.checkTime()) {
            myRepository.delete(keyValue);
            throw new NotValidValueException("Not find value with key: " + key);
        }
        return keyValue;
    }

    @Override
    public String setValue(String key, String value, Long ttl) {

        if (key.isEmpty() || key == null || value == null || value.isEmpty() || ttl <= 0){
            throw new NotValidValueException("Операция добавления прошла не успешно, проверьте параметры");
        }

        if(!myRepository.existsById(key)){
            KeyValue keyValue = new KeyValue(key, value, ttl + System.currentTimeMillis());
            myRepository.saveAndFlush(keyValue);
            return "Операция добавления прошла успешно";
        } else {
            KeyValue keyValue = myRepository.getById(key);
            keyValue.setValue(value);
            keyValue.setTtl(ttl + System.currentTimeMillis());
            myRepository.saveAndFlush(keyValue);
            return "Операция добавления прошла успешно";
        }
    }

    @Override
    public KeyValue removeValue(String key) {
        KeyValue keyValue = getValue(key);
        myRepository.delete(keyValue);
        return keyValue;
    }

    @Override
    public List<KeyValue> getAllValue() {
        List <KeyValue> getAllValue = myRepository.findAll();
        return getAllValue;
    }

    @Override
    public String dumpKeyValue() {
        File file = new File("dump.txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        List <KeyValue> listValues = getAllValue();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileWriter fileWriter = new FileWriter(file);
            for (KeyValue keyValue:listValues){
                if (keyValue.checkTime()){
                    fileWriter.write(keyValue.toString() + "\n");
                    stringBuilder.append(keyValue.toString() + "\n");
                } else myRepository.delete(keyValue);
            }
            fileWriter.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return stringBuilder.toString();
    }

    @Override
    public void loadKeyValue() {
        File file = new File("dump.txt");
        try (Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()){
                String json = scanner.nextLine();
                StringReader reader = new StringReader(json);
                ObjectMapper objectMapper = new ObjectMapper();
                KeyValue keyValue = objectMapper.readValue(reader, KeyValue.class);
                if (keyValue.checkTime()) {
                    setValue(keyValue.getKey(), keyValue.getValue(), keyValue.getTtl() - System.currentTimeMillis());
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkTime() {
        List <KeyValue> keyValues = getAllValue();
        for (KeyValue keyValue:keyValues){
            if (!keyValue.checkTime()){
                myRepository.delete(keyValue);
            }
        }
    }
}
