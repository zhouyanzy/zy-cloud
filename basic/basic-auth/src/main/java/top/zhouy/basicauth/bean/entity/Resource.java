package top.zhouy.basicauth.bean.entity;

import lombok.Data;
import top.zhouy.basicauth.bean.type.ModelType;
import top.zhouy.basicauth.bean.type.ResourceType;
import top.zhouy.commonresponse.bean.entity.BaseEntity;

/**
 * @description: 系统资源
 * @author: zhouy
 * @create: 2020-10-28 11:10:00
 */
@Data
public class Resource extends BaseEntity {

    private ModelType model;

    private ResourceType type;

    private String url;

    private String method;

    private String description;

}
