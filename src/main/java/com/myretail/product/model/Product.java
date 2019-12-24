package com.myretail.product.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.classgraph.json.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(value = "product_price")
public class Product {

    @Id
    private Long id;
    private Price currentPrice;
}
