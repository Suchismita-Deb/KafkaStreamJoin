package com.kafkaStream.StreamJoin.Producer;

import com.kafkaStream.StreamJoin.dto.ProductDetails;
import com.kafkaStream.StreamJoin.dto.SalesDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
@RestController
@Slf4j
class EventProducer {
    @Autowired
    private KafkaTemplate<String, ProductDetails> pdt;

    @Autowired
    private KafkaTemplate<String, SalesDetails> sales;

    @GetMapping("/generate")
    public String publishDummyEvents() {
        ProductDetails product = ProductDetails.builder()
                .catalogNumber("29529")
                .country("001")
                .isSelling(true)
                .model("29525")
                .productId("int7218")
                .registrationId("int4123")
                .registrationNumber("REG03814")
                .sellingStatusDate("2023-06-30T18:21:31.000000Z")
                .build();

        pdt.send("TOPIC_A", product.getCatalogNumber(), product);


        SalesDetails sales1 = SalesDetails.builder()
                .catalogNumber("29525")
                .country("001")
                .orderNumber("ORD-5001")
                .quantity("10")
                .salesDate("2023-07-15T11:45:00.000000Z")
                .build();

        sales.send("TOPIC_B", sales1.getCatalogNumber(), sales1);

        return "Dummy Product and Sales events published successfully!";
    }
    @PostMapping("/Topic_A")
    public String publishProduct(@RequestBody ProductDetails product) {
        pdt.send("TOPIC_A", product.getCatalogNumber(), product);
        log.info("Product event published successfully to TOPIC_A!");
        return "Product event published successfully to TOPIC_A!";
    }
    @PostMapping("/Topic_B")
    public String publishSales(@RequestBody SalesDetails salesDetails) {
        sales.send("TOPIC_B", salesDetails.getCatalogNumber(), salesDetails);
        log.info("Product event published successfully to TOPIC_B!");
        return "Sales event published successfully to TOPIC_B!";
    }
}