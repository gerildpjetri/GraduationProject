package com.example.clientasync.thread;

import com.example.clientasync.components.MetricsCache;
import com.example.clientasync.model.Example;
import org.springframework.web.client.RestTemplate;

public class RequestThread extends Thread {

    private MetricsCache metricsCache;

    public RequestThread(MetricsCache metricsCache){
        this.metricsCache = metricsCache;
    }

    @Override
    public void run() {

        Example ex = Example.builder().field("asetg").build();


        RestTemplate restTemplate = new RestTemplate();
        metricsCache.addStart(System.currentTimeMillis());
        restTemplate.postForEntity("http://127.0.0.1:8081/api", ex, Example.class );
        metricsCache.addEnd(System.currentTimeMillis()); }

}
