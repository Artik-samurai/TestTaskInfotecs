package com.testtaskinfotecs.Tests;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import com.testtaskinfotecs.entity.KeyValue;
import com.testtaskinfotecs.exception.NotValidValueException;
import com.testtaskinfotecs.repository.MyRepository;
import com.testtaskinfotecs.service.ServiceImpl;
import com.testtaskinfotecs.service.ServiceKeyValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;


@SpringBootTest
public class MyTests{

    @TestConfiguration
    class ServiceImplTestContextConfiguration {

        @Bean
        public ServiceKeyValue keyValue(){
            return new ServiceImpl();
        }
    }

    @Autowired
    private ServiceKeyValue serviceKeyValue;

    @MockBean
    MyRepository myRepository;

    @BeforeEach
    void test() throws Exception{
        KeyValue keyValue = new KeyValue("key", "value", 3000000000000L);
        KeyValue keyValueException = new KeyValue("key2", "value", 300L);
        List <KeyValue> keyValues = new ArrayList<>();
        keyValues.add(keyValue);

        Mockito.doReturn(true).when(myRepository).existsById(keyValue.getKey());
        Mockito.doReturn(keyValue).when(myRepository).getById(keyValue.getKey());
    }

    @Test
    void GetValueTest() {
        assertEquals(new KeyValue("key", "value", 3000000000000L), serviceKeyValue.getValue("key"));
    }

    @Test
    void SetValueOldTest() {
        String str = "Операция добавления прошла успешно";
        assertTrue(serviceKeyValue.setValue("key", "Misha", 3500000000000L).equals(str));
    }

    @Test
    void SetValueNewTest() {
        String str = "Операция добавления прошла успешно";
        assertTrue(serviceKeyValue.setValue("key1", "Misha", 3500000000000L).equals(str));
    }

    @Test
    void GetValueExceptionTest() throws NotValidValueException{
        assertThrows(NotValidValueException.class, () -> serviceKeyValue.getValue("key2"));
    }

    @Test
    void SetValueExceptionTest() throws NotValidValueException{
        assertThrows(NotValidValueException.class, () -> serviceKeyValue.getValue(null));
    }

    @Test
    void RemoveValueTest() {
        assertEquals(new KeyValue("key", "value", 3000000000000L), serviceKeyValue.removeValue("key"));
    }

    @Test
    void RemoveValueExceptionTest() throws NotValidValueException{
        assertThrows(NotValidValueException.class, () -> serviceKeyValue.removeValue("KEY"));
    }
}

