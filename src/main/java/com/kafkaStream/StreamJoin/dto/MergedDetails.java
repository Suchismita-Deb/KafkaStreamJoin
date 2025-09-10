package com.kafkaStream.StreamJoin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MergedDetails {

    // Key fields
    private String catalogNumber;
    private String country;

    // Product fields
    private boolean isSelling;
    private String model;
    private String productId;
    private String registrationId;
    private String registrationNumber;
    private String sellingStatusDate;

    // Sales fields
    private String orderNumber;
    private String quantity;
    private String salesDate;
}
