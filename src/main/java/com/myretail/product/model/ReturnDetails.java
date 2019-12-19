package com.myretail.product.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnDetails {
    private Integer code;
    private String message;
    private String source;
}
