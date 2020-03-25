package top.zhouy.blogmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.zhouy.blogmanage.bean.entity.SysParam;
import top.zhouy.util.utils.PageUtils;

import java.util.Map;

/**
 * <p>
 * 系统参数 服务类
 * </p>
 */
public interface SysParamService extends IService<SysParam> {

    /**
     * 分页查询
     * @param params
     * @return
     */
     PageUtils queryPage(Map<String, Object> params);
}
