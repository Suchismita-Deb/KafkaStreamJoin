package com.kafkaStream.StreamJoin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetails {
    private String catalogNumber;
    private boolean isSelling;
    private String model;
    private String productId;
    private String registrationId;
    private String registrationNumber;
    private String sellingStatusDate;  // you may convert this to LocalDateTime if needed
    private String country;
//    private KeyDto key;
    private AuditDto audit;
}