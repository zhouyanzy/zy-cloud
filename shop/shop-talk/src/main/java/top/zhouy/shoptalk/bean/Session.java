package top.zhouy.shoptalk.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: 登录状态
 * @author: zhouy
 * @create: 2020-12-25 14:34:00
 */
@Data
@AllArgsConstructor
public class Session {

    private String userId;

    private String userName;

}
