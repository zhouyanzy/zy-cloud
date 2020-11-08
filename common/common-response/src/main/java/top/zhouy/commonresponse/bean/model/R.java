package top.zhouy.commonresponse.bean.model;


import top.zhouy.commonresponse.bean.enums.ErrorCode;

import java.util.HashMap;

/**
 * 统一返回对象
 * @author zhouYan
 * @date 2020/3/13 13:46
 */
public class R<T> extends HashMap<String, Object>{

    public R() {
        put("timestamp", System.currentTimeMillis());
    }

    private static R onSuccess(){
        return new R().put("code", 200).put("ok", "true");
    }

    private static R onFail(){
        return new R().put("code", 500).put("ok", "false").put("msg", "服务器异常，请稍后再试！");
    }

    public static R ok() {
        return onSuccess();
    }

    public static R okData(Object value) {
        return onSuccess().put("data", value);
    }

    /**
     * 封装业务数据
     * @param key
     * @param value
     * @return
     */
    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        //将HashMap对象本身返回
        return this;
    }

    public static R fail() {
        return fail(ErrorCode.UNKNOWN.getCode(), ErrorCode.UNKNOWN.getMsg());
    }

    public static R fail(ErrorCode errorCode) {
        return onFail().put("code", errorCode.getCode()).put("msg", errorCode.getMsg());
    }

    public static R fail(String msg) {
        return onFail().put("code", ErrorCode.UNKNOWN.getCode()).put("msg", msg);
    }

    public static R fail(Integer code, String msg){
        return onFail().put("code", code).put("msg", msg);
    }

    public static R exception() {
        return exception(ErrorCode.UNKNOWN);
    }

    public static R exception(Exception e) {
        return onFail().put("msg", e.getMessage());
    }

    public static R exception(ErrorCode errorCode) {
        return onFail().put("code", errorCode.getCode()).put("msg", errorCode.getMsg());
    }
}
