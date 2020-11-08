package top.zhouy.basicauth.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zhouy.basicauth.bean.entity.User;
import top.zhouy.basicauth.bean.vo.UserVO;
import top.zhouy.basicauth.service.impl.UserServiceImpl;
import top.zhouy.commonresponse.bean.enums.ErrorCode;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.commonresponse.exception.BsException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhouYan
 * @date 2020/3/10 17:42
 */
@RestController
@RequestMapping("/user")
@Api(description = "用户接口")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public R register(User user) {
        return R.okData(userService.register(user));
    }

    @ApiOperation(value = "获取当前用户")
    @GetMapping("/getCurrentUser")
    public R getCurrentUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            String header = request.getHeader("Authorization");
            token = StrUtil.subAfter(header, "bearer ", false);
            if (StringUtils.isBlank(token)) {
                throw new BsException(ErrorCode.UNAUTHORIZED);
            }
        }
        UserVO userVO = JSONUtil.toBean(JSONUtil.parseObj(((Map)JSONUtil.parseObj(token).get("body")).get("user")), UserVO.class);
        return R.okData(userVO);
    }

}
