package com.myretail.product.util;

import com.myretail.product.config.CurrencyCodeConfig;
import com.myretail.product.model.request.ProductRequest;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    private final CurrencyCodeConfig codeConfig;

    public Validator(CurrencyCodeConfig codeConfig) {
        this.codeConfig = codeConfig;
    }

    public void validate(ProductRequest request, Long id) {

        if (request == null || id == null || !request.getId().equals(id))
            throw new IllegalArgumentException("Id in request and payload does not match");

        if (!codeConfig.getCurrencyCodes().contains(request.getCurrentPrice().getCurrencyCode().trim().toUpperCase()))
            throw new IllegalArgumentException("Provided currency code is not valid");

        if (request.getCurrentPrice().getValue() <= 0)
            throw new IllegalArgumentException("Product price can't be zero or negative");
    }
}
