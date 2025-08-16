package com.kafkaStream.StreamJoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@EnableKafkaStreams
@SpringBootApplication
public class StreamJoinApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamJoinApplication.class, args);
	}

}
