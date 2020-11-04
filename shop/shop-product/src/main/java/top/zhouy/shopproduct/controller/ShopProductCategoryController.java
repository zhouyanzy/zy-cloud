package top.zhouy.shopproduct.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.shopproduct.bean.entity.ShopProductCategory;
import top.zhouy.shopproduct.service.ShopProductCategoryService;

/**
 * <p>
 * 商品分类 前端控制器
 * </p>
 *
 * @author zhouYan
 * @since 2020-04-09
 */
@RestController
@RequestMapping("/productCategory")
public class ShopProductCategoryController {

    @Autowired
    private ShopProductCategoryService shopProductCategoryService;

    /**
     * 保存商品分类信息
     * @param shopProductCategory
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存")
    public R save(ShopProductCategory shopProductCategory){
        return R.okData(shopProductCategoryService.saveOrUpdate(shopProductCategory));
    }
}

