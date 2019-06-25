package com.example.clientasync.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;


@Configuration
public class MetricsCache {

    @Autowired
    private Logger log;

    private ArrayList<Long> startTime = new ArrayList<>();
    private ArrayList<Long> endTime = new ArrayList<>();

    private ConcurrentLinkedDeque<Long> diffTime = new ConcurrentLinkedDeque<>();

    public void calc(){
        Flux.zip(Flux.fromStream(
                startTime.stream()),
                Flux.fromStream(endTime.stream())

        ).subscribe((a)->{
            Long diff = a.getT2() - a.getT1();

            diffTime.add(diff);
            log.info("StartTime: " + a.getT1() + "  EndTime: " + a.getT2() + " difference " + diff);


        });
    }

    public void addStart(Long time){
        startTime.add(time);
    }

    public void addEnd(Long time){
        endTime.add(time);
    }

    public void calcThroughput() throws Exception{
        Long diff = endTime.get(endTime.size() - 1) - startTime.get(0);
        int N = endTime.size();

        double throughput = N/(double)diff;

        log.info("Throughput: " + throughput);
    }

    public  void generateMatlab() throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new FileOutputStream("file.m"));

        StringBuilder str = new StringBuilder();
        str.append("v = [ ");

        diffTime.forEach(
                a -> {
                    str.append(a +" ");
                }
        );

        str.append(" ];");

        out.println(str);
        out.flush();

    }
}
