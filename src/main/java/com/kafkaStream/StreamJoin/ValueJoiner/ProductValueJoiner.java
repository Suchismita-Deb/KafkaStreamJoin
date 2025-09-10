package com.kafkaStream.StreamJoin.ValueJoiner;

import com.kafkaStream.StreamJoin.dto.MergedDetails;
import com.kafkaStream.StreamJoin.dto.ProductDetails;
import com.kafkaStream.StreamJoin.dto.SalesDetails;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class ProductValueJoiner implements ValueJoiner<ProductDetails, SalesDetails, MergedDetails> {
    @Override
    public MergedDetails apply(ProductDetails product, SalesDetails sales) {
        return MergedDetails.builder()
                .catalogNumber(product.getCatalogNumber())
                .country(product.getCountry())
                .isSelling(product.isSelling())
                .model(product.getModel())
                .productId(product.getProductId())
                .registrationId(product.getRegistrationId())
                .registrationNumber(product.getRegistrationNumber())
                .sellingStatusDate(product.getSellingStatusDate())
                .orderNumber(sales.getOrderNumber())
                .quantity(sales.getQuantity())
                .salesDate(sales.getSalesDate())
                .build();
    }
}
