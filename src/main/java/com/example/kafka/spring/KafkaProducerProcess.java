package com.example.kafka.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class KafkaProducerProcess implements ItemProcessor<Person,Person> {
    private static final String TOPIC="rui";
    private static final String broker_list="localhost:9090";
    private static KafkaProducer<String,String> producer=null;
    private static ObjectMapper mapper=new ObjectMapper();
    private static Properties initConfig(){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,broker_list);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"lz4");
        return properties;
    }
    static{
        Properties properties = initConfig();
        producer=new KafkaProducer<>(properties);
    }
    @Override
    public Person process(Person item) throws Exception {
        List<Map<String,String>> list=new ArrayList<>();
        Map<String,String> map=new HashMap<>();
        map.put("FirstName",item.getFirstName());
        map.put("LastName",item.getLastName());
        map.put("TrackID",item.getTrackingID());
        map.put("Status",item.getStatue());
        list.add(map);
        ProducerRecord<String,String> record=new ProducerRecord<>(map.get("Status"),mapper.writeValueAsString(list));
        producer.send(record,new Callback(){

            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if(e==null){
                    System.out.println("Success");
                }
            }
        });
        return item;
    }
}
