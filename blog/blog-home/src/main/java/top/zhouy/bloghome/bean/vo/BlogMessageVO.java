package top.zhouy.bloghome.bean.vo;

import lombok.Data;
import top.zhouy.bloghome.bean.entity.BlogMessage;

import java.util.List;

/**
 * @description: 博客视图
 * @author: zhouy
 * @create: 2020-11-09 11:18:00
 */
@Data
public class BlogMessageVO extends BlogMessage {

    private String isAuthor;

    private List<BlogMessageVO> messages;

}
