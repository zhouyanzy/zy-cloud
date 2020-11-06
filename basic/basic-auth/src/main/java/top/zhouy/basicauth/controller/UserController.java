package top.zhouy.basicauth.controller;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zhouy.basicauth.bean.entity.User;
import top.zhouy.basicauth.service.impl.UserServiceImpl;
import top.zhouy.commonresponse.bean.model.R;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

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
    public Object getCurrentUser(Authentication authentication, HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = StrUtil.subAfter(header, "bearer ", false);
        return Jwts.parser().setSigningKey("test_key".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
    }

}
