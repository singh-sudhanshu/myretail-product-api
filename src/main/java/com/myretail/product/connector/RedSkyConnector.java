package com.myretail.product.connector;

import com.myretail.product.exception.RecordNotFoundException;
import com.myretail.product.model.redsky.RedSkyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
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
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    log.error("Product not found for given Id");
                    return Mono.error(new RecordNotFoundException("Product not found for given productId: " + id.toString()));
                })
                .bodyToMono(RedSkyResponse.class);
    }
}
