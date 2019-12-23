package com.myretail.product.model.response;

import com.myretail.product.model.ReturnDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateResponse {
    private Long id;
    private ReturnDetails returnDetails;
}
