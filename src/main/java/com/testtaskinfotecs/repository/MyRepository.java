package com.testtaskinfotecs.repository;

import com.testtaskinfotecs.entity.KeyValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRepository extends JpaRepository <KeyValue, String> {
}
