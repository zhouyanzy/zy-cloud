package top.zhouy.util.utils;

import cn.hutool.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.zhouy.commonresponse.bean.enums.ErrorCode;
import top.zhouy.commonresponse.exception.BsException;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouYan
 * @date 2020/4/16 11:54
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private Executor asyncExecutor;

    private static RedisUtils redisUtils;

    @PostConstruct
    public void initialize() {
        //@Component will call construct
        redisUtils = this;
        redisUtils.redisTemplate = this.redisTemplate;
        redisUtils.asyncExecutor = this.asyncExecutor;
    }

    /**
     * redis分布式锁
     * @param key 锁对象
     * @param time 锁住时间，秒
     */
    public static void redisLock(String key, Long time){
        if (redisUtils.redisTemplate.hasKey(key)) {
            redisUtils.redisTemplate.expire(key, time, TimeUnit.SECONDS);
            throw new BsException(ErrorCode.UNKNOWN, "请勿重复提交");
        }
        if (! redisUtils.redisTemplate.opsForValue().setIfAbsent(key, 1, time, TimeUnit.SECONDS)){
            throw new BsException(ErrorCode.UNKNOWN, "请勿重复提交");
        }
    }

    /**
     * redis延迟队列
     * @param message 消息
     * @param time 延迟时间，单位秒
     */
    public static void delayQueue(String message, Long time){
        Long currentTime = LocalDateTime.now().getLong(ChronoField.SECOND_OF_DAY);
        redisUtils.redisTemplate.opsForZSet().add("queue::delay::message", message, currentTime + time);
        redisUtils.asyncExecutor.execute(() -> {
            while (!Thread.interrupted()){
                // 只取一条
                Set<Object> values = redisUtils.redisTemplate.opsForZSet().rangeByScore("queue::delay::message", 0, LocalDateTime.now().getLong(ChronoField.SECOND_OF_DAY), 0, 1);
                if (values.isEmpty()) {
                    try {
                        // 每5秒执行一次
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        break;
                    }
                    continue;
                }
                String targeMessage = String.valueOf(values.iterator().next());
                // 利用redis单线程处理重复消费问题
                if (redisUtils.redisTemplate.opsForZSet().remove("queue::delay::message", targeMessage) > 0) {
                    System.out.println(targeMessage);
                }
            }
        });
    }

}
