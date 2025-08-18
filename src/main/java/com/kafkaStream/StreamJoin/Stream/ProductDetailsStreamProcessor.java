package com.kafkaStream.StreamJoin.Stream;

import com.kafkaStream.StreamJoin.ValueJoiner.ProductValueJoiner;
import com.kafkaStream.StreamJoin.dto.KeyDto;
import com.kafkaStream.StreamJoin.dto.MergedDetails;
import com.kafkaStream.StreamJoin.dto.ProductDetails;
import com.kafkaStream.StreamJoin.dto.SalesDetails;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;

@Service
public class ProductDetailsStreamProcessor {
    private final StreamsBuilder streamBuilder;

    public ProductDetailsStreamProcessor(StreamsBuilder streamsBuilder) {
        this.streamBuilder = streamsBuilder;
    }
    @Autowired
    private KafkaProperties kafkaProperties;
    @PostConstruct
    public void joinProducts() {
//        JsonSerde<KeyDto> keySerde = new JsonSerde<>(KeyDto.class);

        Serde<KeyDto> keySerde = Serdes.serdeFrom(
                new JsonSerializer<>(), new JsonDeserializer<>(KeyDto.class)
        );
//        JsonSerde<ProductDetails> pdtDetailSerde = new JsonSerde<>(ProductDetails.class);
//        JsonSerde<SalesDetails> salesDetailSerde = new JsonSerde<>(SalesDetails.class);
//        JsonSerde<MergedDetails> mergedDetailSerde = new JsonSerde<>(MergedDetails.class);

        Serde<ProductDetails> pdtDetailSerde = Serdes.serdeFrom(
                new JsonSerializer<>(), new JsonDeserializer<>(ProductDetails.class)
        );

        Serde<SalesDetails> salesDetailSerde = Serdes.serdeFrom(
                new JsonSerializer<>(), new JsonDeserializer<>(SalesDetails.class)
        );

        Serde<MergedDetails> mergedDetailSerde = Serdes.serdeFrom(
                new JsonSerializer<>(), new JsonDeserializer<>(MergedDetails.class)
        );


//        KStream<String, ProductDetails> product = streamBuilder.stream( "TOPIC_A", Consumed.with(Serdes.String(), pdtDetailSerde));
        KStream<KeyDto, ProductDetails> product = streamBuilder.stream(
                "TOPIC_A", Consumed.with(keySerde, pdtDetailSerde)); // Composite Key.
//        KStream<String, SalesDetails> sales = streamBuilder.stream( "TOPIC_B", Consumed.with(Serdes.String(), salesDetailSerde));
        KStream<KeyDto, SalesDetails> sales = streamBuilder.stream(
                "TOPIC_B", Consumed.with(keySerde, salesDetailSerde));

//        KStream<String, MergedDetails> mergedPdt = streamBuilder.stream( "TOPIC_C", Consumed.with(Serdes.String(), mergedDetailSerde)); // This line is not needed in the stream. It means it consumed from the topic. Here it shoudl add to the topic so the joinedStream.to is used to mention the topic name.


//        KStream<String, MergedDetails> joinedStream = product.join(
//                sales,
//                new ProductValueJoiner(),
//                JoinWindows.of(Duration.ofSeconds(10)),
//                StreamJoined.with(
//                        Serdes.String(),          // Key serde
//                        pdtDetailSerde,           // Left value serde (ProductDetails)
//                        salesDetailSerde          // Right value serde (SalesDetails)
//                )
//        );

        KStream<KeyDto, MergedDetails> joinedStream = product.join(
                sales,
                (prod, sale) -> {
                    MergedDetails merged = new MergedDetails();
                    merged.setKey(new KeyDto(prod.getCatalogNumber(), prod.getCountry()));
                    merged.setProduct(prod);
                    merged.setSales(sale);
                    return merged;
                },
                JoinWindows.of(Duration.ofSeconds(10)),
                StreamJoined.with(
                        keySerde, pdtDetailSerde, salesDetailSerde
                )
        );

        // SteamJoined(key, value of the topic, value of the mergedTopic).
        joinedStream.peek((key, mergedAccountDetail) -> System.out.println("Key = " + key + " Value = " + mergedAccountDetail.toString()));
        joinedStream.to("TOPIC_C", Produced.with(keySerde, mergedDetailSerde));


        Topology topology=streamBuilder.build();

        KafkaStreams streams = new KafkaStreams(topology,new StreamsConfig(kafkaProperties.buildStreamsProperties(null)));
        streams.start();
    }
}
