package com.rx.mvp.zyzj.net.retrofit;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * http响应参数实体类
 * 通过Gson解析属性名称需要与服务器返回字段对应,或者使用注解@SerializedName
 * 备注:这里与服务器约定返回格式
 *
 * @author ZhongDaFeng
 */
public class HttpResponse {

    /**
     * 描述信息
     */
    @SerializedName("message") //info
    private String message;

    /**
     * 状态码
     */
    @SerializedName("code") //status
    private int code;

    /**
     * 数据对象[成功返回对象,失败返回错误说明]
     */
    @SerializedName("data") //data
    private JsonElement data;

    /**
     * 是否成功(这里约定200)
     *
     * @return 1000
     */
    public boolean isSuccess() {
        return code == 200 ? true : false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
