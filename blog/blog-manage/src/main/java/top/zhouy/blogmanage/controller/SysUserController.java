package top.zhouy.blogmanage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhouy.blogmanage.bean.entity.SysUser;
import top.zhouy.blogmanage.bean.form.PasswordForm;
import top.zhouy.blogmanage.bean.form.SysLoginForm;
import top.zhouy.blogmanage.service.SysUserRoleService;
import top.zhouy.blogmanage.service.SysUserService;
import top.zhouy.commonresponse.bean.constant.SysConstants;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.util.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
@RequestMapping("/admin/sys")
public class SysUserController extends BaseController{

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    //12小时后过期
    private final static int EXPIRE = 3600 * 12;

    @PostMapping("/login")
    public R login(@RequestBody SysLoginForm form) {
        //生成token，并保存到redis
        return R.ok().put("token", "1").put("expire", EXPIRE);
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/user/info")
    public R info(){
        return R.ok().put("user", getUser());
    }

    /**
     * 所有用户列表
     */
    @GetMapping("/user/list")
    public R list(@RequestParam Map<String, Object> params){
        //只有超级管理员，才能查看所有管理员列表
        if(SysConstants.SUPER_ADMIN.equals(getUserId())){
            params.put("createUserId", getUserId());
        }
        PageUtils page = sysUserService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 修改密码
     * @param passwordForm
     * @return
     */
    @PutMapping("/user/password")
    public R password(@RequestBody PasswordForm passwordForm){
        if(StringUtils.isEmpty(passwordForm.getNewPassword())) {
            return R.fail("新密码不能为空");
        }
        //sha256加密
        String password = new Sha256Hash(passwordForm.getPassword(), getUser().getSalt()).toHex();
        //sha256加密
        String newPassword = new Sha256Hash(passwordForm.getNewPassword(), getUser().getSalt()).toHex();
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if(!flag){
            return R.fail("原密码不正确");
        }
        return R.ok();
    }

    /**
     * 保存用户
     */
    @PostMapping("/user/save")
    public R save(@RequestBody SysUser user){
        user.setCreateUserId(getUserId());
        sysUserService.save(user);
        return R.ok();
    }

    /**
     * 修改用户
     */
    @PostMapping("/user/update")
    public R update(@RequestBody SysUser user){
        user.setCreateUserId(getUserId());
        sysUserService.updateById(user);
        return R.ok();
    }

    /**
     * 删除用户
     */
    @PostMapping("/user/delete")
    public R delete(@RequestBody Integer[] userIds){
        if(ArrayUtils.contains(userIds, SysConstants.SUPER_ADMIN)){
            return R.fail("系统管理员不能删除");
        }
        if(ArrayUtils.contains(userIds, getUserId())){
            return R.fail("当前用户不能删除");
        }
        sysUserService.deleteBatch(userIds);
        return R.ok();
    }

    /**
     * 用户信息
     */
    @GetMapping("/user/info/{userId}")
    public R info(@PathVariable("userId") Integer userId){
        SysUser user = sysUserService.getById(userId);
        //获取用户所属的角色列表
        List<Integer> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);
        return R.ok().put("user", user);
    }

}
