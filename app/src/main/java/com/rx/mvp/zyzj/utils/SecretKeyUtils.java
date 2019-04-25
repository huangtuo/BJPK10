package com.rx.mvp.zyzj.utils;


import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.entity.resp.LoginServerEntity;

/**
 * Created by fengyh on 16/5/11.
 */
public class SecretKeyUtils {


    public static String getToekn(){
        LoginServerEntity serviceModel = ZyzjApplication.application.getServiceModel();
        if (serviceModel != null) {
            String token = serviceModel.getToken();
            if (token != null && token.length() > 0)
                return token;
        }
        return "";
    }
}
