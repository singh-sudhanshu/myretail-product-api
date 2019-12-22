package com.myretail.product.model.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.myretail.product.model.Price;
import com.myretail.product.model.ReturnDetails;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    @JsonProperty("current_price")
    private Price currentPrice;
    private ReturnDetails returnDetails;
}
