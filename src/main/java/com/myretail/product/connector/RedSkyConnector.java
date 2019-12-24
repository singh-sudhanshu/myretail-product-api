package com.myretail.product.connector;

import com.myretail.product.exception.RecordNotFoundException;
import com.myretail.product.model.redsky.RedSkyResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RedSkyConnector {

    private final WebClient webClient;

    public RedSkyConnector(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<RedSkyResponse> getProductById(Long id) {
        return webClient
                .get()
                .uri("/v2/pdp/tcin/" + id)
                .retrieve()
                .onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                        clientResponse -> Mono.error(new RecordNotFoundException("No record found for given Id")))//
                .bodyToMono(RedSkyResponse.class);
    }
}
