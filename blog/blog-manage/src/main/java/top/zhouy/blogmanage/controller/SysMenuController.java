package top.zhouy.blogmanage.controller;



import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhouy.blogmanage.bean.entity.SysMenu;
import top.zhouy.blogmanage.bean.type.MenuTypeEnum;
import top.zhouy.blogmanage.service.SysMenuService;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.commonresponse.exception.BsException;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 */

@RestController
@RequestMapping("/admin/sys/menu")
public class SysMenuController extends BaseController{

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/nav")
    public R nav(){
        List<SysMenu> menuList = sysMenuService.listUserMenu(getUserId());
        //Set<String> permissions = shiroService.getUserPermissions(getUserId());
        return R.ok().put("menuList", menuList).put("permissions", null);
    }

    /**
     * 所有菜单列表
     */
    @GetMapping("/list")
    public List<SysMenu> list(){
        List<SysMenu> menuList = sysMenuService.list(null);
        menuList.forEach(sysMenu -> {
            SysMenu parentMenu = sysMenuService.getById(sysMenu.getParentId());
            if(parentMenu != null){
                sysMenu.setParentName(parentMenu.getName());
            }
        });
        return menuList;
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    public R select(){
        //查询列表数据
        List<SysMenu> menuList = sysMenuService.queryNotButtonList();
        //添加顶级菜单
        SysMenu root = new SysMenu();
        root.setMenuId(0);
        root.setName("一级菜单");
        root.setParentId(-1);
        root.setOpen(true);
        menuList.add(root);
        return R.ok().put("menuList", menuList);
    }

    /**
     * 获取单个菜单信息
     * @param menuId
     * @return
     */
    @GetMapping("/info/{menuId}")
    public R update(@PathVariable Integer menuId){
        SysMenu menu = sysMenuService.getById(menuId);
        return R.ok().put("menu",menu);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody SysMenu menu){
        //数据校验
        verifyForm(menu);
        sysMenuService.save(menu);
        return R.ok();
    }

    /**
     * 更新
     * @param menu
     * @return
     */
    @PutMapping("/update")
    public R update(@RequestBody SysMenu menu){
        //数据校验
        verifyForm(menu);
        sysMenuService.updateById(menu);
        return R.ok();
    }

    /**
     * 删除
     * @param menuId
     * @return
     */
    @DeleteMapping("/delete/{menuId}")
    public R delete(@PathVariable Integer menuId){
        if(menuId <= 29){
            return R.fail("系统菜单，不能删除");
        }
        //判断是否有子菜单或按钮
        List<SysMenu> menuList = sysMenuService.queryListParentId(menuId);
        if(menuList.size() > 0){
            return R.fail("请先删除子菜单或按钮");
        }
        sysMenuService.delete(menuId);
        return R.ok();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenu menu) {
        if (StringUtils.isBlank(menu.getName())) {
            throw new BsException("菜单名称不能为空");
        }
        if (menu.getParentId() == null) {
            throw new BsException("上级菜单不能为空");
        }
        //菜单
        if (menu.getType() == MenuTypeEnum.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new BsException("菜单URL不能为空");
            }
        }
        //上级菜单类型
        int parentType = MenuTypeEnum.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenu parentMenu = sysMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }
        //目录、菜单
        if (menu.getType() == MenuTypeEnum.CATALOG.getValue() ||
                menu.getType() == MenuTypeEnum.MENU.getValue()) {
            if (parentType != MenuTypeEnum.CATALOG.getValue()) {
                throw new BsException("上级菜单只能为目录类型");
            }
        }
        //按钮
        if (menu.getType() == MenuTypeEnum.BUTTON.getValue()) {
            if (parentType != MenuTypeEnum.MENU.getValue()) {
                throw new BsException("上级菜单只能为菜单类型");
            }
        }
    }
}
