package top.zhouy.basiczuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import top.zhouy.commonauthclient.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 鉴权
 * @author zhouYan
 * @date 2020/4/27 14:30
 */
@Component
public class AuthFilter extends ZuulFilter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthService authService;

    /**
     * filterType：过滤器类型
     * <p>
     * pre：路由之前
     * routing：路由之时
     * post： 路由之后
     * error：发送错误调用
     *
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * filterOrder：过滤的顺序 序号配置可参照 https://blog.csdn.net/u010963948/article/details/100146656
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * shouldFilter：判断是否要执行过滤
     *
     * @return true表示需要过滤，将对该请求执行run方法
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext =  RequestContext.getCurrentContext();
        HttpServletRequest  request = requestContext.getRequest();
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isBlank(authorization)) {
            log.info("------游客访问------");
            return null;
        } else {
            log.info("------访问------" + authorization);
            String method = request.getMethod();
            String url = request.getRequestURI();
            // 鉴权
            if (!authService.auth(authorization, url, method)) {
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                requestContext.setResponseBody("用户没有权限");
                HttpServletResponse response = requestContext.getResponse();
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.setContentType("application/json;charset=utf-8");
            } else {
                requestContext.setSendZuulResponse(true);
                // 添加请求头，传递到业务服务
                requestContext.addZuulRequestHeader("Authorization", authorization);
                requestContext.addZuulRequestHeader("token", "bearer" + authService.getToken(authorization));
                // 添加响应头，返回给前端
                requestContext.addZuulResponseHeader("Authorization", authorization);
            }
        }
        return null;
    }

}
