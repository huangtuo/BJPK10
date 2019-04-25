package com.rx.mvp.zyzj.base;

import com.rx.mvp.zyzj.net.retrofit.HttpRequest;
import com.rx.mvp.zyzj.utils.EncryptUtil;

import java.util.TreeMap;

/**
 * 基础业务类
 *
 * @author ZhongDaFeng
 */
public class BaseBiz {


    protected HttpRequest mHttpRequest;

    public BaseBiz() {
        mHttpRequest = new HttpRequest();
    }

    protected HttpRequest getRequest() {
        if (mHttpRequest == null) {
            mHttpRequest = new HttpRequest();
        }
        return mHttpRequest;
    }

    protected static TreeMap<String, Object> getPostHeadMap() {
        TreeMap<String, Object> map = new TreeMap<String, Object>();
//        if (!TextUtils.isEmpty(TApplication.getToken())) {
//            try {
//                //	map.put(ServerConfig.SERVER_METHOD_KEY, DES3.encode(TApplication.getToken()+"\\u00260295061099E98368525EB51912ADA832\\u0026c2b_android_phone"));
//                //	map.put(ServerConfig.SERVER_METHOD_KEY, getToken(TApplication.class, BuildConfig.SERVER_METHOD_KEY));
//                if (BuildConfig.IS_SERVER) {
//                    map.put(ServerConfig.SERVER_METHOD_KEY, TApplication.getServerToken());
//                }else{
//                    map.put(ServerConfig.SERVER_METHOD_KEY, TApplication.getToken());
//                }
//
//                //LogUtil.out("MD5="+TApplication.token+TApplication.getAppMd5());
//                //map.put(ServerConfig.SERVER_METHOD_KEY, TApplication.getToken());
//            } catch (Exception e) {
//                e.printStackTrace();
//                //	map.put(ServerConfig.SERVER_METHOD_KEY, TApplication.getToken());
//            }
//            UserInfoUtil.setLoginStatus(true);
//        } else {
//            UserInfoUtil.setLoginStatus(false);
//        }
        map.put("ver", "5.6.1");
        map.put("key",
                EncryptUtil.md5Encrypt("testb2c"));// app_key 10
        // map.put(ServerConfig.SERVER_METHOD_KEY, method);
        // map.put(ServerConfig.SERVER_VESRTION_KEY, ServerConfig.SERVER_VESRTION_VAULE);
        // map.put(ServerConfig.SERVER_UPDATE_KEY, ServerConfig.SERVER_UPDATE_VAULE);
        map.put("plat","c2b_android");
        return map;

    }

}
