package com.myretail.product.controller;

import com.myretail.product.model.response.ProductResponse;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @ApiResponses({
            @ApiResponse(code=200, message = "retrieve successful"),
            @ApiResponse(code=400, message = "bad request"),
            @ApiResponse(code=404, message = "Product Name does not exist in redsky"),
            @ApiResponse(code=404, message = "Product Not found for the given productId"),

    })
    @GetMapping(value = "/products/{id}")
    public ProductResponse getProduct(@PathVariable Long id){
        return null;
    }
}
