package top.zhouy.bloghome.event.lisenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.zhouy.bloghome.event.BlogEvent;
import top.zhouy.bloghome.mapper.BlogArticleMapper;
import top.zhouy.bloghome.mapper.BlogMessageMapper;

/**
 * @description: 博客事件监听
 * @author: zhouy
 * @create: 2020-11-09 11:49:00
 */
@Component
public class BlogEventListener {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlogArticleMapper blogArticleMapper;

    @Autowired
    private BlogMessageMapper blogMessageMapper;

    @Async
    @EventListener( condition= "#event.eventType != null ")
    public void onApplicationEvent(BlogEvent event) {
        switch (event.getEventType()){
            case LIKE:
                blogArticleMapper.like(event.getId(), 1);
                break;
            case VIEW:
                blogArticleMapper.view(event.getId(), 1);
                break;
            case LIKE_MESSAGE:
                blogMessageMapper.like(event.getId(), 1);
                break;
        }
        log.info("事件开始");
    }
}
