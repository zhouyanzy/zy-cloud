package top.zhouy.basicgateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.zhouy.commonprovider.service.AuthService;

/**
 * @description: 网关权限校验过滤器
 * @author: zhouy
 * @create: 2020-11-25 14:49:00
 */
@Slf4j
@Component
public class OauthFilter implements GlobalFilter, Ordered {

    @Autowired
    private AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authorizationHead = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.isNoneBlank(authorizationHead) && ! authorizationHead.startsWith("bearer")) {
            authorizationHead = null;
        }
        final String authorization = authorizationHead;
        if (StringUtils.isBlank(authorization)) {
            log.info("------游客访问------");
        } else {
            log.info("------登录访问------" + authorization);
            String method = exchange.getRequest().getMethod().name();
            String url = exchange.getRequest().getPath().value();
            // 鉴权
            if (! authService.auth(authorization, url, method)) {
                ServerHttpResponse serverHttpResponse = exchange.getResponse();
                serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
                serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                byte[] data = "{\"code\": 999, \"msg\": \"访问权限不足!\"}".getBytes();
                DataBuffer buffer = serverHttpResponse.bufferFactory().wrap(data);
                return serverHttpResponse.writeWith(Mono.just(buffer));
            }
        }
        ServerHttpRequest request = exchange.getRequest().mutate().headers(
                httpHeaders -> {
                    if (StringUtils.isNotBlank(authorization)) {
                        httpHeaders.add("Authorization", authorization);
                        httpHeaders.add("token", authService.getToken(authorization));
                    } else {
                        httpHeaders.add("Authorization", "");
                    }
                }
        ).build();
        ServerWebExchange build = exchange.mutate().request(request).build();
        return chain.filter(build);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
