package top.zhouy.basiczuul.config;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitKeyGenerator;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitUtils;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitType;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @description: 自定义限流
 * @author: zhouy
 * @create: 2020-11-18 15:54:00
 */
@Component
public class KeyGenerator implements RateLimitKeyGenerator {

    private final RateLimitProperties properties;

    private final RateLimitUtils rateLimitUtils;

    public KeyGenerator(RateLimitProperties properties, RateLimitUtils rateLimitUtils) {
        this.properties = properties;
        this.rateLimitUtils = rateLimitUtils;
    }

    /**
     * 限流逻辑
     *
     * @param request
     * @param route
     * @param policy
     * @return
     */
    @Override
    public String key(HttpServletRequest request, Route route, RateLimitProperties.Policy policy) {

        final List<RateLimitType> types = policy.getType().stream().map(RateLimitProperties.Policy.MatchType::getType).collect(Collectors.toList());
        //增加的matchers判断
        final List<String> matchers = policy.getType().stream().map(RateLimitProperties.Policy.MatchType::getMatcher).collect(Collectors.toList());

        final StringJoiner joiner = new StringJoiner(":");
        joiner.add(properties.getKeyPrefix());
        if (route != null) {
            joiner.add(route.getId());
        }
        if (!types.isEmpty()) {
            if (types.contains(RateLimitType.URL) && route != null) {
                joiner.add(route.getPath());
            }
            if (types.contains(RateLimitType.ORIGIN)) {
                joiner.add(rateLimitUtils.getRemoteAddress(request));
            }
            if (types.contains(RateLimitType.USER)) {
                joiner.add(rateLimitUtils.getUser(request));
            }
        }
        // 增加的matchers判断
        if (!matchers.isEmpty()) {
            // 判断matchers里是否包含当前访问的url，如果包含，则追加当前的path
            if (matchers.contains(route.getPath()) && route != null) {
                joiner.add(route.getPath());
            }
            if (matchers.contains(getIPAddress(request))) {
                joiner.add(getIPAddress(request));
            }
            if (matchers.contains(rateLimitUtils.getUser(request))) {
                joiner.add(rateLimitUtils.getUser(request));
            }
        }
        return joiner.toString();
    }

    /**
     * 获取ip
     * @param request
     * @return
     */
    public static String getIPAddress(HttpServletRequest request) {
        String ip = null;
        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }
        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }
        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

}
