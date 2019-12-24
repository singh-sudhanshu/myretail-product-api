package com.myretail.product.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myretail.product.model.Price;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Long id;
    @JsonProperty("current_price")
    private Price currentPrice;
}
