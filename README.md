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

The output Json.

```json
{
 "catalog_number": "29525",
 "is_selling": true,
 "model": "29525",
 "product_id": "int7218",
 "registration_id": "int4123",
 "registration_number": "REG03814",
 "selling_status_date": "2023-06-30T18:21:31.000000Z",
 "country": "001",
 "order_number": "03814",
 "quantity": "2",
 "sales_date": "2023-07-30T18:21:31.000000Z"
}
```

### Start the application.
Start Zookeeper and Kafka port 9092. Application running in 8181.
Postman - POST - http://localhost:8181/Topic_A 
Request Body 
```json
{
  "catalogNumber": "29525",
  "country": "001",
  "isSelling": true,
  "model": "29525",
  "productId": "int7218",
  "registrationId": "int4123",
  "registrationNumber": "REG03814",
  "sellingStatusDate": "2023-06-30T18:21:31.000000Z"
}
```
It will send the data to topic.

Postman - POST - http://localhost:8181/Topic_B
Request Body.
```json
{
  "catalogNumber": "29525",
  "country": "001",
  "orderNumber": "ORD-5001",
  "quantity": "10",
  "salesDate": "2023-07-15T11:45:00.000000Z"
}
```
It will send the data to the topic.


When the catalogNumber and country are same it will send to topic_C. Modify the json and the data will not be added and no console message will be print.


Postman - GET - `http://localhost:8181/generate/Topic_A` Directly add dummy data to the Topic_A and Topic_B and the country and catalogNumber are same so it will add the data to Topic_C. It will be visible in the console.
```json
2025-09-10T10:10:20.790+05:30  INFO 2728 --- [StreamJoin] [nio-8181-exec-3] c.k.StreamJoin.Producer.EventProducer    : Product event published successfully to TOPIC_B!
Key = 29525-001 Value = MergedDetails(catalogNumber=29525, country=001, isSelling=false, model=29525, productId=int7218, registrationId=int4123, registrationNumber=REG03814, sellingStatusDate=2023-06-30T18:21:31.000000Z, orderNumber=ORD-5001, quantity=10, salesDate=2023-07-15T11:45:00.000000Z)
```