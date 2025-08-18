package com.kafkaStream.StreamJoin.ValueJoiner;

import com.kafkaStream.StreamJoin.dto.KeyDto;
import com.kafkaStream.StreamJoin.dto.MergedDetails;
import com.kafkaStream.StreamJoin.dto.ProductDetails;
import com.kafkaStream.StreamJoin.dto.SalesDetails;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class ProductValueJoiner implements ValueJoiner<ProductDetails, SalesDetails, MergedDetails> {
    @Override
    public MergedDetails apply(ProductDetails product, SalesDetails sales) {
        return MergedDetails.builder()
                .key(KeyDto.builder().catalogNumber(product.getCatalogNumber()).country(product.getCountry()).build())
                .product(product)
                .sales(sales)
                .build();
    }
}
