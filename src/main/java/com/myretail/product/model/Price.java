package com.myretail.product.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Price {
    private Double value;
    private String currencyCode;
}
