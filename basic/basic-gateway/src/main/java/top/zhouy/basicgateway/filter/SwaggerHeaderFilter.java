package top.zhouy.basicgateway.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import top.zhouy.basicgateway.config.GatewaySwaggerResourcesProvider;

/**
 * @description: Swagger /v2/api/docs 404处理
 * @author: zhouy
 * @create: 2020-11-25 15:45:00
 */
@Component
public class SwaggerHeaderFilter extends AbstractGatewayFilterFactory {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            if (!StringUtils.endsWithIgnoreCase(path, GatewaySwaggerResourcesProvider.SWAGGER2URL)) {
                return chain.filter(exchange);
            }
            ServerHttpRequest newRequest = request.mutate().build();
            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
            return chain.filter(newExchange);
        };
    }
}
