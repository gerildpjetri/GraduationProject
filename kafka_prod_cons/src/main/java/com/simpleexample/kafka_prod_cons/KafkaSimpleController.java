package com.simpleexample.kafka_prod_cons;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/kafka")
public class KafkaSimpleController {

    private KafkaTemplate<String, String> kafkaTemplate;
    private Gson jsonConverter;
    private DataComponent dataComponent;

    @Autowired
    public KafkaSimpleController(
            KafkaTemplate<String, String> kafkaTemplate,
            Gson jsonConverter,
            DataComponent dataComponent
    ){
        this.kafkaTemplate = kafkaTemplate;
        this.jsonConverter = jsonConverter;
        this.dataComponent = dataComponent;
    }

    @GetMapping
    public void post() throws InterruptedException {
       SimpleModel sm = new SimpleModel("field1", "field2");

        String str = jsonConverter.toJson(sm);

        int N = 100;
        for(int i = 0; i < N; i++){
            dataComponent.addStartTime(System.currentTimeMillis());
            kafkaTemplate.send("myTopic", str);
        }

        Thread.sleep(2000);

        dataComponent.calculate();
    }


    @KafkaListener(topics = "myTopic")
    public void getFromKafka(String simpleModel){


        //System.out.println(simpleModel);
        dataComponent.addEndTime(System.currentTimeMillis());
        //SimpleModel simpleModel1 = (SimpleModel) jsonConverter.fromJson(simpleModel, SimpleModel.class);


        //System.out.println(simpleModel1.toString());
    }


}
