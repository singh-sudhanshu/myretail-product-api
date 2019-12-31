package com.myretail.product.controller;

import com.myretail.product.model.request.ProductRequest;
import com.myretail.product.model.response.ProductResponse;
import com.myretail.product.model.response.ProductUpdateResponse;
import com.myretail.product.service.ProductService;
import com.myretail.product.util.Validator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1")
public class ProductController {

    private final ProductService productService;
    private final Validator validator;

    public ProductController(ProductService productService, Validator validator) {
        this.productService = productService;
        this.validator = validator;
    }

    @ApiOperation(
            value = "Get Product and Current Price",
            notes = "This service will accept a Product Id in request param.\n\n" +
                    "It will call RedSky Rest service to retrieve the product information based on product Id.\n\n" +
                    "Then it will retrieve the product's current price from No-SQL database and send back aggregated product response"
    )

    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully sent Product Response"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Product is not available for given productId"),
            @ApiResponse(code = 500, message = "Internal Server Error. Unable to serve request at this time. Please try again later.")

    })
    @GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductResponse> getProduct(@PathVariable Long id) {
        return productService.service(id);
    }

    @ApiOperation(
            value = "Update Product's current price",
            notes = "The service will accept price in request body and product id in path.\n\n" +
                    "Then it will update current price in data store\n\n" +
                    "Service sends success response to user"
    )

    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully sent Product Response"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Product is not available for given productId"),
            @ApiResponse(code = 500, message = "Internal Server Error. Unable to serve request at this time. Please try again later.")

    })
    @PutMapping(value = "/products/{id}")
    public Mono<ProductUpdateResponse> updatePrice(@RequestBody ProductRequest request, @PathVariable Long id) {
        validator.validate(request, id);
        return productService.updatePrice(request.getCurrentPrice(), id);
    }
}
