package com.myretail.product.controller;

import com.myretail.product.model.response.ProductResponse;
import com.myretail.product.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
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
    @GetMapping(value = "/products/{id}")
    public Mono<ProductResponse> getProduct(@PathVariable Long id) {
        return productService.service(id);
    }
}
