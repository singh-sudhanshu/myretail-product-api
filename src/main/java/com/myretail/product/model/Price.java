package com.myretail.product.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Price {
    private Double value;
    @JsonProperty("currency_code")
    private String currencyCode;
}
