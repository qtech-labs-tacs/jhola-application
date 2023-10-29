package com.jhola.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.net.HttpHeaders;

import reactor.core.publisher.Mono;

@Component
public class GlobalAuthenticationHeaderFilter
        extends AbstractGatewayFilterFactory<GlobalAuthenticationHeaderFilter.Config> {

    @Autowired
    Environment env;

    // @Autowired
    // private RestTemplate reqRestTemplate;

    public GlobalAuthenticationHeaderFilter() {
        super(Config.class);
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            String accessTokenWithOutBearer = null;

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            accessTokenWithOutBearer = authorizationHeader.replace("Bearer ", "");

            if (!isJwtValid(accessTokenWithOutBearer)) {
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return response.setComplete();
    }

	private boolean isJwtValid(String jwt) {
		/*
		 * boolean returnValue = false;
		 * 
		 * Object requestObject = null;
		 * 
		 * org.springframework.http.HttpHeaders reqHeaders = new
		 * org.springframework.http.HttpHeaders();
		 * 
		 * Map<String, String> reqHeaderMap = new HashMap<String, String>();
		 * reqHeaderMap.put("Authorization", jwt); reqHeaderMap.put("Content-Type",
		 * "application/json"); reqHeaders.setAll(reqHeaderMap);
		 * 
		 * HttpEntity<?> reqEntity = new HttpEntity(requestObject, reqHeaders);
		 * 
		 * ResponseEntity<Boolean> responseEntity =
		 * reqRestTemplate.exchange("http://security:8005/security/validatetoken",
		 * HttpMethod.GET, reqEntity, Boolean.class);
		 * 
		 * if (responseEntity != null) { returnValue = responseEntity.getBody(); }
		 */
		return true;
    }
	
	
}
