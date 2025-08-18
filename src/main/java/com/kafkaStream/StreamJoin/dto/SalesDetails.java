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
    private String orderNumber;
    private String quantity;  // if always numeric, prefer Integer
    private String salesDate; // same here, consider LocalDateTime
    private String country;
//    private KeyDto key;
    private AuditDto audit;
}

