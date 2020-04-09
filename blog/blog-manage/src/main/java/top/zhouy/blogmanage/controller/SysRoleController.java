package top.zhouy.blogmanage.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhouy.blogmanage.bean.entity.SysRole;
import top.zhouy.blogmanage.service.SysRoleMenuService;
import top.zhouy.blogmanage.service.SysRoleService;
import top.zhouy.commonresponse.bean.constant.SysConstants;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.util.utils.PageUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统角色Controller
 * @author zhouYan
 * @date 2020/3/17 14:21
 */
@RestController
@RequestMapping("/admin/sys/role")
public class SysRoleController extends BaseController{

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 分页查询角色列表
     * @param params
     * @return
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        //如果不是超级管理员，则只查询自己创建的角色列表
        if(!SysConstants.SUPER_ADMIN.equals(getUserId())){
            params.put("createUserId", getUserId());
        }
        PageUtils page = sysRoleService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 角色列表
     */
    @GetMapping("/select")
    public R select(){
        Map<String, Object> map = new HashMap<>();
        //如果不是超级管理员，则只查询自己所拥有的角色列表
        if(!SysConstants.SUPER_ADMIN.equals(getUserId())){
            map.put("createUserId", getUserId());
        }
        Collection<SysRole> list = sysRoleService.listByMap(map);
        return R.ok().put("list", list);
    }

    /**
     * 保存角色信息
     * @param role
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody SysRole role){
        role.setCreateUserId(getUserId());
        sysRoleService.save(role);
        return R.ok();
    }

    /**
     * 更新角色信息
     * @param role
     * @return
     */
    @PutMapping("/update")
    public R update(@RequestBody SysRole role){
        role.setCreateUserId(getUserId());
        sysRoleService.updateById(role);
        return R.ok();
    }

    /**
     * 获取角色信息
     * @param roleId
     * @return
     */
    @GetMapping("/info/{roleId}")
    public R info(@PathVariable Integer roleId){
        SysRole role = sysRoleService.getById(roleId);
        List<Integer> menuIdList=sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);
        return R.ok().put("role",role);
    }

    /**
     * 删除角色信息
     * @param roleIds
     * @return
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Integer[] roleIds){
        sysRoleService.deleteBatch(roleIds);
        return R.ok();
    }
}
