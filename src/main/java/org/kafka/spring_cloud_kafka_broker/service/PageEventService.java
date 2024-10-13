package org.kafka.spring_cloud_kafka_broker.service;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.kafka.spring_cloud_kafka_broker.entities.PageEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
//    @Bean
    public Function<PageEvent, PageEvent> pageEventFunction() {
        return (pageEvent -> {
            pageEvent.setName("Name Changed");
            return pageEvent;
        });
     }

    @Bean
    public Function<KStream<String, PageEvent>, KStream<String, Long>> kStreamFunction() {
        return input -> input
                .filter((k, v) -> v.getDuration() > 100)
                .map((k, v) -> new KeyValue<>(v.getName(), 0L))
                .groupBy((k, v) -> k, Grouped.with(Serdes.String(), Serdes.Long()))
                .windowedBy(TimeWindows.of(Duration.ofSeconds(5)))
                .count(Materialized.as("counts-store"))
                .toStream()
                .map((k, v) -> new KeyValue<>(
                        "Window: [" + k.window().startTime() + " - " + k.window().endTime() + "] Key: " + k.key(), v
                ));
    }

}
