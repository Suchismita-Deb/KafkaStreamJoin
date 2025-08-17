The producer will produce the data and stream will add in the consumer and no need of consumer file.

The producer and consumer directly can create the topic. Stream expect the topic should be created beforehand and it will only work with the data. The topic can be created in terminal of in a Kafka Config newTopic method.

```java
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
```
In the main branch the product dto and sales dto and given and in final the join is made based on the catalog number.

The product dto.
```json
{
  "catalogNumber": "29525",
  "country": "001",
  "isSelling": true,
  "model": "29525",
  "productId": "int7218",
  "registrationId": "int4123",
  "registrationNumber": "REG03814",
  "sellingStatusDate": "2023-06-30T18:21:31.000000Z",
  "eventName": "Registration",
  "sourceSystem": "RGR"
}
```
The sales dto.
```json
{
  "catalogNumber": "CAT-12345",
  "country": "India",
  "orderNumber": "ORD-98765",
  "quantity": "50",
  "salesDate": "2025-08-17",
  "eventName": "ORDER_PLACED",
  "sourceSystem": "E-COMMERCE_PORTAL"
}
```

When the json value is nested then it has the nested class.
Input value.
```json
{
  "key": {
    "catalog_number": "29525",
    "country": "001"
  },
  "value": {
    "catalog_number": "29525",
    "is_selling": true,
    "model": "29525",
    "product_id": "int7218",
    "registration_id": "int4123",
    "registration_number": "REG03814",
    "selling_status_date": "2023-06-30T18:21:31.000000Z",
    "country": "001"
  },
  "audit": {
    "event_name": "Registration",
    "source_system": "RGR"
  }
}
```
The dto file.
```java
import lombok.Data;

@Data
public class ProductDetails {
    private Key key;
    private Value value;
    private Audit audit;

    @Data
    public static class Key {
        private String catalogNumber;
        private String country;
    }

    @Data
    public static class Value {
        private String catalogNumber;
        private boolean isSelling;
        private String model;
        private String productId;
        private String registrationId;
        private String registrationNumber;
        private String sellingStatusDate;
        private String country;
    }

    @Data
    public static class Audit {
        private String eventName;
        private String sourceSystem;
    }
}
```