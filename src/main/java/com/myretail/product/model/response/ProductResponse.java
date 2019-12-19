package com.myretail.product.model.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.myretail.product.model.Product;
import com.myretail.product.model.ReturnDetails;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    private Product product;
    private ReturnDetails returnDetails;
}
