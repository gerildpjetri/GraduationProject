package com.restclient.restclient;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

@SpringBootApplication
public class RestclientApplication implements CommandLineRunner {


    public Logger log = Logger.getAnonymousLogger();

    public static void main(String[] args) {
        SpringApplication.run(RestclientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        ArrayList<Long> startTime = new ArrayList<>();
        ArrayList<Long> endTime = new ArrayList<>();


        Example ex = Example.builder().field("ex").build();

        long startTtime = System.currentTimeMillis();

        int N = 100;

        for(int i = 0; i < N; i++){

            startTime.add(System.currentTimeMillis());

            restTemplate.postForEntity(Environment.uri, ex, Example.class);

            endTime.add(System.currentTimeMillis());
        }

        long endTtime = System.currentTimeMillis();

        for(int i = 0; i < startTime.size(); i++){
            long diff = endTime.get(i) - startTime.get(i);
            log.info("StartTime: " + startTime.get(i) + "  EndTime: " +endTime.get(i) + " difference " + diff);
        }

        double throughput = N/(double)(endTtime - startTtime);

        log.info("Throughput: " + throughput);

        PrintWriter out = new PrintWriter(new FileOutputStream("file.m"));
        out.println("printing");
        out.flush();
    }
}
