package top.zhouy.bloghome.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zhouy.bloghome.bean.entity.BlogMessage;
import top.zhouy.bloghome.bean.type.EventType;
import top.zhouy.bloghome.bean.type.MessageType;
import top.zhouy.bloghome.event.BlogEvent;
import top.zhouy.bloghome.event.publisher.EventPublisher;
import top.zhouy.bloghome.service.BlogMessageService;
import top.zhouy.commonauthclient.entity.UserUtils;
import top.zhouy.commonauthclient.entity.UserVO;
import top.zhouy.commonresponse.bean.model.R;

/**
 * @description: 留言Controller
 * @author: zhouy
 * @create: 2020-11-09 10:31:00
 */
@RestController
@RequestMapping("/message")
@Api(description = "留言Controller")
public class BlogMessageController {

    @Autowired
    private BlogMessageService blogMessageService;

    /**
     * 添加评论
     * @return
     */
    @PostMapping("/addMessage")
    @ApiOperation(value = "添加评论")
    public R addMessage(BlogMessage blogMessage){
        if (!MessageType.ALL.equals(blogMessage.getType())) {
            UserVO userVO = UserUtils.LOCAL_USER.get();
            if (userVO != null) {
                blogMessage.setUserId(userVO.getId());
                blogMessage.setName(userVO.getName());
                blogMessage.setAvatar(userVO.getAvatar());
                blogMessage.setPhone(userVO.getPhone());
                blogMessage.setEmail(userVO.getEmail());
            }
        }
        return R.okData(blogMessageService.save(blogMessage));
    }

    /**
     * 喜爱
     * @return
     */
    @PostMapping("/likeMessage")
    @ApiOperation(value = "喜爱")
    public R likeMessage(Long id){
        EventPublisher.publishEvent(new BlogEvent(this, EventType.LIKE_MESSAGE, id, null));
        return R.ok();
    }

}
