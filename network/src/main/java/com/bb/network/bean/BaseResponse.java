package com.bb.network.bean;

/**
 * 服务器返回数据
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/15
 * Time: 16:57
 */
public class BaseResponse<T> {
    public static final int RESPONSE_OK = 0;//需根据具体协议定义请求成功返回值

    private T data;
    private String message;
    private int code = -1;
    private boolean ok;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", ok=" + ok +
                '}';
    }
}
