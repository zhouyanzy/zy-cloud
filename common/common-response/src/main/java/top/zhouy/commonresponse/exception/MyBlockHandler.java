package top.zhouy.commonresponse.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import top.zhouy.commonresponse.bean.enums.ErrorCode;
import top.zhouy.commonresponse.bean.model.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 自定义限流异常
 * @author: zhouy
 * @create: 2020-12-30 14:50:00
 */
@Component
@Slf4j
public class MyBlockHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        if (e instanceof FlowException) {
            log.error("限流异常");
        } else if (e instanceof DegradeException) {
            log.error("降级异常");
        } else if (e instanceof ParamFlowException) {
            log.error("热点参数异常");
        } else if (e instanceof SystemBlockException) {
            log.error("系统异常");
        } else if (e instanceof AuthorityException) {
            log.error("授权异常");
        }
        httpServletResponse.setStatus(HttpStatus.OK.value());
        R r = R.fail(ErrorCode.SENTINEL.getCode(), e.getMessage());
        httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
        httpServletResponse.getWriter().print(JSON.toJSONString(r));
        httpServletResponse.getWriter().flush();
    }
}

