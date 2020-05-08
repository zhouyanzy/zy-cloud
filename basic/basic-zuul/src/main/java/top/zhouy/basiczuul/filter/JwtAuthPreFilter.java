package top.zhouy.basiczuul.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import top.zhouy.commonresponse.bean.model.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * @author zhouYan
 * @date 2020/4/27 15:21
 */
@Component
public class JwtAuthPreFilter extends ZuulFilter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ObjectMapper objectMapper;

    private static AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        //路径与配置的相匹配，则执行过滤
        RequestContext ctx = RequestContext.getCurrentContext();
        String uri = ctx.getRequest().getRequestURI();
        log.info("uri=" + uri);
        if (!matcher.match("/api-auth/**", uri)) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            ctx.setSendZuulResponse(true);
            return null;
        }
        Claims claims;
        try {
            //解析没有异常则表示token验证通过，如有必要可根据自身需求增加验证逻辑
            claims = Jwts.parser().setSigningKey("test_key".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
            log.info("token : {} 验证通过", token);
            //对请求进行路由
            ctx.setSendZuulResponse(true);
            //请求头加入userId，传给业务服务
            ctx.addZuulRequestHeader("userId", claims.get("userId").toString());
        } catch (ExpiredJwtException expiredJwtEx) {
            log.error("token : {} 过期", token );
            //不对请求进行路由
            ctx.setSendZuulResponse(false);
            responseError(ctx, -402, "token expired");
        } catch (Exception ex) {
            log.error("token : {} 验证失败" , token );
            //不对请求进行路由
            ctx.setSendZuulResponse(false);
            responseError(ctx, -401, "invalid token");
        }
        return null;
    }

    /**
     * 将异常信息响应给前端
     */
    private void responseError(RequestContext ctx, int code, String message) {
        HttpServletResponse response = ctx.getResponse();
        ctx.setResponseBody(toJsonString(R.fail(code, message)));
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json;charset=utf-8");
    }

    private String toJsonString(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("json序列化失败", e);
            return null;
        }
    }
}
