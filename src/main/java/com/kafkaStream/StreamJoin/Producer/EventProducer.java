package com.kafkaStream.StreamJoin.Producer;

import com.kafkaStream.StreamJoin.dto.AuditDto;
import com.kafkaStream.StreamJoin.dto.KeyDto;
import com.kafkaStream.StreamJoin.dto.ProductDetails;
import com.kafkaStream.StreamJoin.dto.SalesDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
class EventProducer {
    @Autowired
    private KafkaTemplate<KeyDto, ProductDetails> pdt;

    @Autowired
    private KafkaTemplate<KeyDto, SalesDetails> sales;

    @GetMapping("/generate")
    public String publishDummyEvents() {
        // create composite key
        KeyDto key = KeyDto.builder()
                .catalogNumber("29525")
                .country("001")
                .build();

        AuditDto auditPdt = AuditDto.builder()
                .eventName("Registration")
                .sourceSystem("RGR")
                .build();
        // product payload
        ProductDetails product = ProductDetails.builder()
                .catalogNumber("29525")
                .country("001")
                .isSelling(true)
                .model("29525")
                .productId("int7218")
                .registrationId("int4123")
                .registrationNumber("REG03814")
                .sellingStatusDate("2023-06-30T18:21:31.000000Z")
                .audit(auditPdt)
//                .key(key)
                .build();

        pdt.send("TOPIC_A", key, product);


        AuditDto salesAudit = AuditDto.builder()
                .eventName("SalesOrder")
                .sourceSystem("SLS")
                .build();

        SalesDetails sales1 = SalesDetails.builder()
                .catalogNumber("29525")
                .country("001")
                .orderNumber("ORD-5001")
                .quantity("10")
                .salesDate("2023-07-15T11:45:00.000000Z")
                .audit(salesAudit)
//                .key(key)
                .build();


        sales.send("TOPIC_B", key, sales1);

        return "Dummy Product and Sales events published successfully!";
    }
}