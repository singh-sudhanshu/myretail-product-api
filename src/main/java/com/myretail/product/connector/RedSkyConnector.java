package com.myretail.product.connector;

import com.myretail.product.model.redsky.RedSkyResponse;
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
                .bodyToMono(RedSkyResponse.class);
    }
}
