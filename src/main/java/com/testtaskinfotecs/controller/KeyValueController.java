package com.testtaskinfotecs.controller;

import com.testtaskinfotecs.entity.KeyValue;
import com.testtaskinfotecs.service.ServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableScheduling
public class KeyValueController {

    private final ServiceImpl serviceImpl;

    public KeyValueController(ServiceImpl serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @GetMapping("/get")
    @ResponseBody
    public ResponseEntity<KeyValue> getValue(@RequestParam(name = "key") String key) {
        KeyValue keyValue = this.serviceImpl.getValue(key);
        return new ResponseEntity<>(keyValue, HttpStatus.OK);
    }

    @PutMapping("/set")
    @ResponseBody
    public ResponseEntity<String> setValue(@RequestParam(name = "key") String key, @RequestParam(name = "value") String value,
                             @RequestParam(name = "ttl", defaultValue = "500") Long ttl){
        String str = serviceImpl.setValue(key, value, ttl);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    @ResponseBody
    public ResponseEntity<KeyValue> removeValue(@RequestParam(name = "key") String key){
        KeyValue keyValue = serviceImpl.removeValue(key);
        return new ResponseEntity<>(keyValue, HttpStatus.OK);
    }

    @PostMapping("/dump")
    @ResponseBody
    public ResponseEntity<String> dumpKeyValue(){
        String str = serviceImpl.dumpKeyValue();
        return new ResponseEntity<>("Следующие данные были загружены в файл dump.txt: \n" + str, HttpStatus.OK);
    }

    @PostMapping("/load")
    @ResponseBody
    public ResponseEntity<String> loadKeyValue(){
        serviceImpl.loadKeyValue();
        return new ResponseEntity<>("Проверьте базу данных, загрузка закончена", HttpStatus.OK);
    }

    @Scheduled(fixedRate = 10)
    public void check(){
        serviceImpl.checkTime();
    }
}
