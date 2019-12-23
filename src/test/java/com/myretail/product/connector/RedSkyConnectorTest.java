package com.myretail.product.connector;

import com.myretail.product.model.redsky.Item;
import com.myretail.product.model.redsky.ProductDescription;
import com.myretail.product.model.redsky.ProductItem;
import com.myretail.product.model.redsky.RedSkyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

class RedSkyConnectorTest {

    private RedSkyConnector redSkyConnector;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    private WebClient.UriSpec uriSpecMock;

    @Mock
    private WebClient.RequestHeadersSpec requestHeaderSpecMock;

    @Mock
    private WebClient.ResponseSpec responseSpecMock;

    @Mock
    private WebClient.ResponseSpec responseMock;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        redSkyConnector = new RedSkyConnector(webClient);
    }

    @Test
    void getProductById() {

        Mono<RedSkyResponse> response = Mono.just(RedSkyResponse.builder()
                .productItem(ProductItem.builder()
                        .item(Item.builder()
                                .productDescription(ProductDescription.builder()
                                        .title("My Product")
                                        .build())
                                .build())
                        .build())
                .build());

        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(uriSpecMock.uri("/api/v1/products/", 123456L)).thenReturn(requestHeaderSpecMock);
        when(requestHeaderSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseMock.bodyToMono(RedSkyResponse.class)).thenReturn(response);

        Mono<RedSkyResponse> actualResponse = redSkyConnector.getProductById(123456L);

        StepVerifier.create(actualResponse)
                .expectNextMatches(res -> res.getProductItem()
                        .getItem().getProductDescription()
                        .getTitle().equals("My Product")).verifyComplete();
    }
}