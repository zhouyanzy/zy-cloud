package top.zhouy.bloghome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.zhouy.bloghome.bean.entity.BlogMessage;
import top.zhouy.bloghome.bean.type.MessageType;
import top.zhouy.bloghome.bean.vo.BlogMessageVO;

import java.util.List;

/**
 * @description: 评论接口
 * @author: zhouy
 * @create: 2020-11-09 10:48:00
 */
@Component
public interface BlogMessageMapper extends BaseMapper<BlogMessage> {

    /**
     * 查找评论
     * @param type
     * @param typeId
     * @return
     */
    List<BlogMessageVO> listComments(@Param("type") MessageType type, @Param("typeId") Long typeId);

    /**
     * 点赞
     * @param id
     * @param amount
     * @return
     */
    int like(@Param("id") Long id, @Param("amount") Integer amount);
}
