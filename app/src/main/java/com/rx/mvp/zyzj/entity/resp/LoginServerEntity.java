package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;

/**
 * Created by fengyh on 16/5/11.
 */
public class LoginServerEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    private String Token ;


    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    @Override
    public String toString() {
        return "LoginServerEntity{" +
                "Token='" + Token + '\'' +
                '}';
    }
}
