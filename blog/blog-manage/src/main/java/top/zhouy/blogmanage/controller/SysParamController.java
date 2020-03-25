package top.zhouy.blogmanage.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhouy.blogmanage.bean.entity.SysParam;
import top.zhouy.blogmanage.service.SysParamService;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.util.utils.PageUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统参数 前端控制器
 * </p>
 */
@RestController
@Slf4j
@RequestMapping("/admin/sys/param")
public class SysParamController extends BaseController{

    @Autowired
    private SysParamService paramService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = paramService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 获取所有参数
     */
    @GetMapping("/all")
    public R listAll(){
        List<SysParam> sysParamList = paramService.list(null);
        return R.ok().put("sysParamList",sysParamList);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        SysParam param = paramService.getById(id);
        return R.ok().put("param", param);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody SysParam param){
        paramService.save(param);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody SysParam param){
        paramService.updateById(param);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody String[] ids){
        paramService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}
