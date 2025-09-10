package com.kafkaStream.StreamJoin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesDetails {

    private String catalogNumber;
    private String country;

    // Sales fields
    private String orderNumber;
    private String quantity;
    private String salesDate;

    // Audit fields
//    private String eventName;
//    private String sourceSystem;
}
