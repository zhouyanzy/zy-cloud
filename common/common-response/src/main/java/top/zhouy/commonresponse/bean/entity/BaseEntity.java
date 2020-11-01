package top.zhouy.commonresponse.bean.entity;

import lombok.Data;

import java.util.Date;

/**
 * @description: 公共基础类
 * @author: zhouy
 * @create: 2020-10-28 11:28:00
 */
@Data
public class BaseEntity {

    private Long id;

    private Date createdAt;

    private Date updatedAt;

    private char archive;
}
