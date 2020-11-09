package top.zhouy.bloghome.bean.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import top.zhouy.bloghome.bean.type.MessageType;
import top.zhouy.commonresponse.bean.entity.BaseEntity;

/**
 * @description: 博客评论
 * @author: zhouy
 * @create: 2020-11-09 10:33:00
 */
@Data
public class BlogMessage extends BaseEntity{

    private Long userId;

    private Long typeId;

    private String content;

    private String phone;

    private String name;

    private String email;

    @TableField(value = "type")
    private MessageType type;

    private String avatar;

    private Long likes;
}
