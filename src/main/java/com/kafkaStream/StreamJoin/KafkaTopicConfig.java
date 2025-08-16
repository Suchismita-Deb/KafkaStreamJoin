package com.kafkaStream.StreamJoin;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic topicA() {
        return TopicBuilder.name("TOPIC_A")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicB() {
        return TopicBuilder.name("TOPIC_B")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicC() {
        return TopicBuilder.name("TOPIC_C")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
