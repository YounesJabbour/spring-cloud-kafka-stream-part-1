package org.kafka.spring_cloud_kafka_broker.web;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.kafka.spring_cloud_kafka_broker.entities.PageEvent;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

@RequiredArgsConstructor
@RestController
public class PageEventRestController {

    private final StreamBridge streamBridge;

@GetMapping("publish/{topic}/{name}")
public PageEvent publish(@PathVariable String name, @PathVariable String topic){
    PageEvent pageEvent=new PageEvent();
    pageEvent.setName(name);
    pageEvent.setDate(new Date());
    pageEvent.setDuration(new Random().nextInt(1000));
    pageEvent.setUser(Math.random()>0.5?"U1":"U2");
    streamBridge.send(topic,pageEvent);
    return pageEvent;
}
}
