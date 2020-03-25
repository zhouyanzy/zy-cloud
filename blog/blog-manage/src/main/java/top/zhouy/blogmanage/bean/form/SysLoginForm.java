package top.zhouy.blogmanage.bean.form;

import lombok.Data;

/**
 * SysLoginForm
 */
@Data
public class SysLoginForm {

    private String username;

    private String password;

    private String captcha;

    private String uuid;
}
