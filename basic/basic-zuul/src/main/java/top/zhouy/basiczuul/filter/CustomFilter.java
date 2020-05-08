package top.zhouy.basiczuul.filter;

import cn.hutool.core.util.StrUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhouYan
 * @date 2020/4/27 14:30
 */
@Component
public class CustomFilter extends ZuulFilter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

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
        //token对象
        String token = request.getHeader("token");
        String authorization = request.getHeader("Authorization");
        log.info("header::token=" + token + ";header::authorization=" + authorization );
        if(StringUtils.isBlank((token))){
            token  = request.getParameter("token");
        }
        if (StringUtils.isBlank(token)) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        //获取response对象
        HttpServletResponse response = requestContext.getResponse();
        //添加请求头，传递到业务服务
        //requestContext.addZuulRequestHeader("Authorization", authorization);
        //添加响应头，返回给前端
        //ctx.addZuulResponseHeader("Authorization", header);
        return null;
    }

}
