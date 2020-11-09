package top.zhouy.bloghome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.zhouy.bloghome.bean.entity.BlogMessage;
import top.zhouy.bloghome.bean.type.MessageType;
import top.zhouy.bloghome.bean.vo.BlogMessageVO;

import java.util.List;

/**
 * @description: 评论接口
 * @author: zhouy
 * @create: 2020-11-09 10:57:00
 */
public interface BlogMessageService extends IService<BlogMessage> {

    /**
     * 查找评论
     * @param type
     * @param typeId
     * @return
     */
    List<BlogMessageVO> listComments(MessageType type, Long typeId);
}
