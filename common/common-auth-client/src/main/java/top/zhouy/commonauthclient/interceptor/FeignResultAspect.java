package top.zhouy.commonauthclient.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import top.zhouy.commonresponse.bean.enums.ErrorCode;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.commonresponse.exception.BsException;

/**
 * @description: feign异常拦截处理
 * @author: zhouy
 * @create: 2021-03-08 17:49:00
 */
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class FeignResultAspect {

    /**
     * Pointcut注解声明切点
     * 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
     * @within 对类起作用，@annotation 对方法起作用
     */
    @Pointcut("@within(org.springframework.cloud.openfeign.FeignClient)")
    public void feignClientPointCut() {}

    /**
     * 配置前置通知,使用在方法aspect()上注册的切入点
     * 同时接受JoinPoint切入点对象,可以没有该参数
     * @param proceedingJoinPoint
     * @throws ClassNotFoundException
     */
    @Around("feignClientPointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Object object = proceedingJoinPoint.proceed();
        if(object instanceof R<?>) {
            R<?> r = (R<?>)object;
            if(!ErrorCode.SUCCESS.getCode().equals(r.get("code"))) {
                throw new BsException(ErrorCode.UNKNOWN, String.valueOf(r.get("msg")));
            }
        }
        return object;
    }
}