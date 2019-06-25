package com.simpleexample.kafka_prod_cons;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class KafkaProdConsApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KafkaProdConsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getForEntity("http://localhost:8080/api/kafka", SimpleModel.class);
    }
}
