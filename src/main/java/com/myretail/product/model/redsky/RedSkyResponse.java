package com.myretail.product.model.redsky;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RedSkyResponse {

    @JsonProperty("product")
    private ProductItem productItem;
}
