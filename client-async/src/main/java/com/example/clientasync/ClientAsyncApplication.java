package com.example.clientasync;

import com.example.clientasync.components.MetricsCache;
import com.example.clientasync.thread.RequestThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.logging.Logger;

@SpringBootApplication
public class ClientAsyncApplication implements CommandLineRunner {

    @Autowired
    private MetricsCache metricsCache;

    Logger  log = Logger.getGlobal();

    public static void main(String[] args) {
        SpringApplication.run(ClientAsyncApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("sth");

        int N = 10;

        RequestThread requestThread[] = new RequestThread[N];

        for(int i = 0; i < N; i++){
           requestThread[i] = new RequestThread(metricsCache);
           requestThread[i].start();
        }

        for(int i = 0; i < N; i++){

            requestThread[i].join();

        }


        metricsCache.calc();
        metricsCache.calcThroughput();

        metricsCache.generateMatlab();

    }

    @Bean
    Logger log(){
        return Logger.getGlobal();
    }
}
