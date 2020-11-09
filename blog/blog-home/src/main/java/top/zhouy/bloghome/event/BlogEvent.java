package top.zhouy.bloghome.event;

import org.springframework.context.ApplicationEvent;
import top.zhouy.bloghome.bean.type.EventType;

/**
 * @description: 博客事件，点赞，浏览
 * @author: zhouy
 * @create: 2020-11-09 11:44:00
 */
public class BlogEvent extends ApplicationEvent {

    private final EventType eventType;

    private final Long id;

    /**
     * 事件交互信息
     */
    private final String message;

    public BlogEvent(Object source, EventType eventType, Long id, String message) {
        super(source);
        this.eventType = eventType;
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public EventType getEventType(){
        return eventType;
    }
}
