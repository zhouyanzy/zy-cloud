package top.zhouy.blogmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import top.zhouy.blogmanage.bean.entity.SysRoleMenu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
@Service
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 保存
     * @param roleId
     * @param menuIdList
     */
    void saveOrUpdate(Integer roleId, List<Integer> menuIdList);

    /**
     * 查找
     * @param roleId
     * @return
     */
    List<Integer> queryMenuIdList(Integer roleId);

    /**
     * 删除
     * @param roleIds
     */
    void deleteBatchByRoleId(Integer[] roleIds);
}
