package top.zhouy.blogmanage.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhouy.blogmanage.bean.entity.SysMenu;
import top.zhouy.blogmanage.bean.type.MenuTypeEnum;
import top.zhouy.blogmanage.mapper.SysMenuMapper;
import top.zhouy.blogmanage.service.SysMenuService;
import top.zhouy.blogmanage.service.SysRoleMenuService;
import top.zhouy.blogmanage.service.SysUserService;
import top.zhouy.commonresponse.bean.constant.SysConstants;
import top.zhouy.util.utils.MapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 获取用户的所有菜单
     * @param userId
     * @return
     */
    @Override
    public List<SysMenu> listUserMenu(Integer userId) {
        //系统管理员，拥有最高权限
        if(SysConstants.SUPER_ADMIN.equals(userId)){
            return getAllMenuList(null);
        }
        //用户菜单列表
        List<Integer> menuIdList = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    private List<SysMenu> getAllMenuList(List<Integer> menuIdList) {
        //查询用户所属所有目录
        List<SysMenu> menuList = queryListParentId(0, menuIdList);
        //递归生成特定格式的菜单列表
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }
    /**
     * 递归
     */
    private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<Integer> menuIdList){
        List<SysMenu> subMenuList = new ArrayList<>();

        for(SysMenu entity : menuList){
            //目录
            if(entity.getType() == MenuTypeEnum.CATALOG.getValue()){
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }

    /**
     * 根据父菜单，查询子菜单(用于鉴权)
     * @param parentId   父菜单ID
     * @param menuIdList 用户菜单ID
     */
    @Override
    public List<SysMenu> queryListParentId(Integer parentId, List<Integer> menuIdList) {
        List<SysMenu> menuList = queryListParentId(parentId);
        if(menuIdList == null){
            return menuList;
        }

        List<SysMenu> userMenuList = new ArrayList<>();
        for(SysMenu menu : menuList){
            if(menuIdList.contains(menu.getMenuId())){
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    @Override
    public List<SysMenu> queryListParentId(Integer parentId) {
        return baseMapper.queryListParentId(parentId);
    }

    /**
     * 获取不包含按钮的菜单列表
     */
    @Override
    public List<SysMenu> queryNotButtonList() {
        return baseMapper.queryNotButtonList();
    }

    /**
     * 获取用户菜单列表
     * @param userId
     */
    @Override
    public List<SysMenu> getUserMenuList(Integer userId) {
        //系统管理员，拥有最高权限
        if(SysConstants.SUPER_ADMIN.equals(userId)){
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Integer> menuIdList = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    /**
     * 删除
     * @param menuId
     */
    @Override
    public void delete(Integer menuId) {
        //删除菜单
        baseMapper.deleteById(menuId);
        //删除菜单与角色关联
        sysRoleMenuService.removeByMap(new MapUtils().put("menu_id",menuId));
    }
}
