package com.myretail.product.util;

import com.myretail.product.model.request.ProductRequest;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    public void validate(ProductRequest request, Long id) {

        if (request == null || id ==null || !request.getId().equals(id))
            throw new IllegalArgumentException("Id in request and payload does not match");
    }
}
