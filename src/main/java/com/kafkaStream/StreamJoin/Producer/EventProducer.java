package com.kafkaStream.StreamJoin.Producer;

import com.kafkaStream.StreamJoin.dto.ProductDetails;
import com.kafkaStream.StreamJoin.dto.SalesDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
class EventProducer {
    @Autowired
    private KafkaTemplate<String, ProductDetails> pdt;

    @Autowired
    private KafkaTemplate<String, SalesDetails> sales;

    @GetMapping("/generate")
    public String publishDummyEvents() {
        ProductDetails product = ProductDetails.builder()
                .catalogNumber("29525")
                .country("001")
                .isSelling(true)
                .model("29525")
                .productId("int7218")
                .registrationId("int4123")
                .registrationNumber("REG03814")
                .sellingStatusDate("2023-06-30T18:21:31.000000Z")
                .eventName("Registration")
                .sourceSystem("RGR")
                .build();

        pdt.send("TOPIC_A", product.getCatalogNumber(), product);


        SalesDetails sales1 = SalesDetails.builder()
                .catalogNumber("29525")
                .country("001")
                .orderNumber("ORD-5001")
                .quantity("10")
                .salesDate("2023-07-15T11:45:00.000000Z")
                .eventName("SalesOrder")
                .sourceSystem("SLS")
                .build();

        sales.send("TOPIC_B", sales1.getCatalogNumber(), sales1);

        return "Dummy Product and Sales events published successfully!";
    }
}