package com.vea.atoms.mvp.http;

public class onlyHttpResponse<T> {

    //token失效
    public static final int TOKEN_LOSE = 112;

    private boolean hasError;
    private int code;
    private String message;
    private T data;

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getMessage() {
        if (code == TOKEN_LOSE) {
            // TODO
//               UIUtils.startLoginActivity(BaseApplication.getInstance());
        }
        if (message == null) {
            message = "";
        }
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}