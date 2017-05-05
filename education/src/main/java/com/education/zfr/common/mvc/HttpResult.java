package com.education.zfr.common.mvc;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by zangfr on 2017/4/24.
 */
public class HttpResult {
    private String statusCode;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 设置返回结果.
     */
    public void setResult(String resultCode, String resultMessage) {
        statusCode = resultCode;
        message = resultMessage;
    }

    /**
     * 设置为默认的系统内部未知错误.
     */
    public void setSystemError() {
        setResult("300", "系统未知运行时错误！");
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

