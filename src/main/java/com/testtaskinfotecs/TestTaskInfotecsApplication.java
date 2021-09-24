package com.testtaskinfotecs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;

@SpringBootApplication
public class TestTaskInfotecsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestTaskInfotecsApplication.class, args);
    }

    /**
     * Start internal H2 server so we can query the DB from IDE
     *
     * @return H2 Server instance
     * @throws SQLException
     */
}
