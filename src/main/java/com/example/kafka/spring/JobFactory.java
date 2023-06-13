package com.example.kafka.spring;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Configuration
@Component
public class JobFactory {
    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    @Autowired
    KafkaProducerProcess kafkaProducerProcess;
    @Bean
    public Job job(){
        return jobBuilderFactory.get("job").start(step1()).build();
    }

    private Step step1() {
        return stepBuilderFactory
                .get("step1")
                .<Person,Person> chunk(10)
                .reader(listItemReader())
                .processor(kafkaProducerProcess)
                .writer(list->list.forEach(System.out::println))
                .build();
    }

    private ItemReader<Person> listItemReader() {
       List<Person> list=new ArrayList<>();
       list.add(new Person("Rui","Wang","111","delivered"));
       list.add(new Person("jiebing","wen","222","shipped"));
       list.add(new Person("puqing","ning","333","shipped"));
        return new ListItemReader<>(list);
    }


}
