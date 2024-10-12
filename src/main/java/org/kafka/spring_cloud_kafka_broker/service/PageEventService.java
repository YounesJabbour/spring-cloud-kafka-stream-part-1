package org.kafka.spring_cloud_kafka_broker.service;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.kafka.spring_cloud_kafka_broker.entities.PageEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class PageEventService {

    @Bean
    public Consumer<PageEvent> pageEventConsumer() {
        return (pageEvent -> {
            System.out.println("******------************");
            System.out.println(pageEvent.toString());
            System.out.println("*******-----**********");
        });
    }

    @Bean
    public Supplier<PageEvent> pageEventSupplier() {
        return () -> {
            return PageEvent.builder()
                    .name((Math.random() > 0.5) ? "P1" : "P2")
                    .user((Math.random() > 0.5) ? "U1" : "U2")
                    .date(new Date())
                    .duration(new Random().nextInt(1000))
                    .build();
        };
    }
    @Bean
    public Function<PageEvent, PageEvent> pageEventFunction() {
        return (pageEvent -> {
            pageEvent.setName("Name Changed");
            return pageEvent;
        });
     }
}
