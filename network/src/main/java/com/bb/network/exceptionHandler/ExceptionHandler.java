package com.bb.network.exceptionHandler;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/15
 * Time: 19:13
 */
public class ExceptionHandler {
    public static final int UNAUTHORIZED = 401;//通过身份验证的用户无权访问处理请求所需的资源。
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int REQUEST_TIMEOUT = 408;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int BAD_GATEWAY = 502;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIMEOUT = 504;

    /**
     * 转化成最后app可以统一处理的exception
     * @param e
     * @return
     */
    public static ResponseThrowable handleException(Throwable e) {
        ResponseThrowable ex;
        if (e instanceof ResponseThrowable) {
            ex = (ResponseThrowable) e;
        }else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            String message = "网络错误";
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    message = "网络错误";
                    break;
            }
            ex = new ResponseThrowable(Error.HTTP_ERROR,message,e);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponseThrowable(Error.PARSE_ERROR,"解析错误", e);
        } else if (e instanceof ConnectException) {
            ex = new ResponseThrowable(Error.NETWORK_ERROR,"连接失败", e);
        } else if (e instanceof SSLHandshakeException) {
            ex = new ResponseThrowable(Error.SSL_ERROR,"证书验证失败", e);
        } else if (e instanceof ConnectTimeoutException
                || e instanceof SocketTimeoutException) {
            ex = new ResponseThrowable(Error.TIMEOUT_ERROR,"连接超时", e);
        } else{
            ex = new ResponseThrowable(Error.UNKNOWN,"未知错误:" + e.getMessage(), e);
        }
        return ex;
    }

    /**
     * 约定异常
     */
    public static class Error {
        public static final int HTTP_ERROR = 1000;//协议错误
        public static final int PARSE_ERROR = 1001;//解析错误
        public static final int NETWORK_ERROR = 1002;//网络错误
        public static final int SSL_ERROR = 1003;//证书错误
        public static final int TIMEOUT_ERROR = 1004;//连接超时
        public static final int LOCAL_CACHE_ERROR = 1005;//本地缓存错误
        public static final int UNKNOWN = 1006;//未知错误
    }

    public static class ResponseThrowable extends Exception {
        public int code;

        public static ResponseThrowable create(int code,String message,Throwable throwable){
            return new ResponseThrowable(code,message,throwable);
        }

        private ResponseThrowable(int code,String message,Throwable throwable) {
            super(message,throwable);
            this.code = code;
        }

        @Override
        public String toString() {
            return "ResponseThrowable{" +
                    "code=" + code +
                    " " + super.toString() +
                    '}';
        }
    }
}
