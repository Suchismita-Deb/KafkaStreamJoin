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