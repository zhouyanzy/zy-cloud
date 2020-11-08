package top.zhouy.basicauth.bean.entity;

import lombok.Data;
import top.zhouy.commonresponse.bean.entity.BaseEntity;

/**
 * @description: 用户
 * @author: zhouy
 * @create: 2020-10-28 11:20:00
 */
@Data
public class User extends BaseEntity {

    private String name;

    private String password;

    private String avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
