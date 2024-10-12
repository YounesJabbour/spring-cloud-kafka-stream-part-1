package org.kafka.spring_cloud_kafka_broker.entities;

import lombok.*;

import java.util.Date;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class PageEvent {
    private String name;
    private String user;
    private Date date;
    private long duration;
}
