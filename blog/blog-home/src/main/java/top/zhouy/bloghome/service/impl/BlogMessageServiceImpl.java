package top.zhouy.bloghome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhouy.bloghome.bean.entity.BlogMessage;
import top.zhouy.bloghome.bean.type.MessageType;
import top.zhouy.bloghome.bean.vo.BlogMessageVO;
import top.zhouy.bloghome.mapper.BlogMessageMapper;
import top.zhouy.bloghome.service.BlogMessageService;

import java.util.List;

/**
 * @description: 评论实现类
 * @author: zhouy
 * @create: 2020-11-09 10:57:00
 */
@Service
public class BlogMessageServiceImpl extends ServiceImpl<BlogMessageMapper, BlogMessage> implements BlogMessageService {

    @Autowired
    private BlogMessageMapper blogMessageMapper;

    @Override
    public List<BlogMessageVO> listComments(MessageType type, Long typeId) {
        List<BlogMessageVO> listComments = blogMessageMapper.listComments(type, typeId);
        for (BlogMessageVO blogMessageVO: listComments) {
            blogMessageVO.setMessages(blogMessageMapper.listComments(MessageType.MESSAGE, blogMessageVO.getId()));
            for (BlogMessageVO blogMessage: blogMessageVO.getMessages()) {
                blogMessage.setMessages(blogMessageMapper.listComments(MessageType.MESSAGE, blogMessage.getId()));
            }
        }
        return listComments;
    }
}
