package com.myretail.product.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfig {

    private final static int DEFAULT_CONNECT_TIMEOUT = 900;
    private final static int DEFAULT_SOCKET_TIMEOUT = 900;

    @Bean
    public WebClient webClient(@Value("${redsky.uri}") String redskyUri) {

//        return WebClient.builder().clientConnector(connector())
//
//                .build();
        return WebClient.create(redskyUri);
    }

//    @Bean
//    public ReactorClientHttpConnector connector() {
//        return new ReactorClientHttpConnector(options ->
//                options.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, DEFAULT_CONNECT_TIMEOUT)
//                        .poolResources(PoolResources.fixed("httpPool", 1000))
//                        .afterNettyContextInit(ctx -> {
//                            ctx.addHandlerLast(new ReadTimeoutHandler(DEFAULT_SOCKET_TIMEOUT, TimeUnit.MILLISECONDS));
//                        }));
//    }
}
