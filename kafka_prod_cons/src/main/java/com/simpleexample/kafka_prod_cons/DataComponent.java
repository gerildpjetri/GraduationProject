package com.simpleexample.kafka_prod_cons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.logging.Logger;

@Component
public class DataComponent {

    private ArrayList<Long> startTime = new ArrayList<>();
    private ArrayList<Long> endTime = new ArrayList<>();


    Logger log  = Logger.getAnonymousLogger();


    void addStartTime(long start){
        startTime.add(start);
    }

    void addEndTime(long end){
        endTime.add(end);
    }

    void calculate(){
        int N = endTime.size();

        for(int i = 0; i < N; i++){
            long diff = endTime.get(i) - startTime.get(i);
            log.info("StartTime: " + startTime.get(i) + " EndTime: " + endTime.get(i) + "diff  " + diff );
        }

        long roundTrip = endTime.get(N - 1) - startTime.get(0);

        double throughput = N/(double)roundTrip;

        log.info(" Throughput: " + throughput);
    }
}
