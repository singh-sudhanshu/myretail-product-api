package com.myretail.product.exception;

import com.myretail.product.model.ReturnDetails;
import com.myretail.product.model.response.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestControllerAdvise {

    private final String messageSource;

    public RestControllerAdvise(
            @Value("${spring.application.name}") String messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProductResponse productNotFoundException(RecordNotFoundException ex) {
        return getErrorResponse(ex, 404, "");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProductResponse illegalArgumentException(IllegalArgumentException ex) {
        return getErrorResponse(ex, 400, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProductResponse exception(Exception ex) {
        return getErrorResponse(ex, 500, "");
    }

    private ProductResponse getErrorResponse(Exception ex, Integer code, String message) {
        ReturnDetails returnDetails = ReturnDetails.builder()
                .code(code)
                .message("".equals(message) ? ex.getMessage() : message)
                .source(messageSource)
                .build();
        return ProductResponse.builder()
                .returnDetails(returnDetails)
                .build();

    }
}
