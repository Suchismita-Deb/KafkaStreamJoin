package com.kafkaStream.StreamJoin.Stream;

import com.kafkaStream.StreamJoin.ValueJoiner.ProductValueJoiner;
import com.kafkaStream.StreamJoin.dto.MergedDetails;
import com.kafkaStream.StreamJoin.dto.ProductDetails;
import com.kafkaStream.StreamJoin.dto.SalesDetails;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.Properties;

@Service
public class ProducrDetailsStreamProcessor {
//    @Autowired
//    private StreamsBuilder streamBuilder;

    private final StreamsBuilder streamBuilder;

    public ProducrDetailsStreamProcessor(StreamsBuilder streamsBuilder) {
        this.streamBuilder = streamsBuilder;
    }
    @Autowired
    private KafkaProperties kafkaProperties;
    @PostConstruct
    public void joinProducts() {
        JsonSerde<ProductDetails> pdtDetailSerde = new JsonSerde<>(ProductDetails.class);
        JsonSerde<SalesDetails> salesDetailSerde = new JsonSerde<>(SalesDetails.class);
        JsonSerde<MergedDetails> mergedDetailSerde = new JsonSerde<>(MergedDetails.class);

        KStream<String, ProductDetails> product = streamBuilder.stream( "TOPIC_A", Consumed.with(Serdes.String(), pdtDetailSerde));
        KStream<String, SalesDetails> sales = streamBuilder.stream( "TOPIC_B", Consumed.with(Serdes.String(), salesDetailSerde));
        KStream<String, MergedDetails> mergedPdt = streamBuilder.stream( "TOPIC_C", Consumed.with(Serdes.String(), mergedDetailSerde));


        KStream<String, MergedDetails> joinedStream = product.join(
                sales,
                new ProductValueJoiner(),
                JoinWindows.of(Duration.ofSeconds(10)),
                StreamJoined.with(
                        Serdes.String(),          // Key serde
                        pdtDetailSerde,           // Left value serde (ProductDetails)
                        salesDetailSerde          // Right value serde (SalesDetails)
                )
        );

        // SteamJoined(key, value of the topic, value of the mergedTopic).
        joinedStream.peek((key, mergedAccountDetail) -> System.out.println("Key = " + key + " Value = " + mergedAccountDetail.toString()));
        joinedStream.to("TOPIC_C", Produced.with(Serdes.String(), mergedDetailSerde));


        Topology topology=streamBuilder.build();

        KafkaStreams streams = new KafkaStreams(topology,new StreamsConfig(kafkaProperties.buildStreamsProperties(null)));
        streams.start();
    }
}
