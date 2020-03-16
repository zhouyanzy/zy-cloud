package top.zhouy.commonresponse.bean.model;


import top.zhouy.commonresponse.exception.GlobalErrorCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 统一返回对象
 * @author zhouYan
 * @date 2020/3/13 13:46
 */
public class R<T> implements Serializable{

    private Integer code;

    private String message;

    private T data;

    private Boolean success;

    private Long timestamp;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public static R success(){
        R r = new R();
        r.setCode(2000);
        r.setSuccess(true);
        r.setTimestamp(new Date().getTime());
        return r;
    }

    public static R success(Object data){
        R r = new R();
        r.setCode(2000);
        r.setData(data);
        r.setSuccess(true);
        r.setTimestamp(new Date().getTime());
        return r;
    }

    public static R fail(String message){
        R r = new R();
        r.setCode(4000);
        r.setMessage(message);
        r.setSuccess(false);
        r.setTimestamp(new Date().getTime());
        return r;
    }

    public static R fail(Integer code, String message){
        R r = new R();
        r.setCode(code);
        r.setMessage(message);
        r.setSuccess(false);
        r.setTimestamp(new Date().getTime());
        return r;
    }

    public static R fail(GlobalErrorCode status){
        R r = new R();
        r.setCode(status.getErrorCode());
        r.setMessage(status.getError());
        r.setSuccess(false);
        r.setTimestamp(new Date().getTime());
        return r;
    }
}
