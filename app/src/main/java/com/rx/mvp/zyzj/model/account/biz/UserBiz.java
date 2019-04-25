package com.rx.mvp.zyzj.model.account.biz;

import com.rx.mvp.zyzj.base.BaseBiz;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.rx.mvp.zyzj.net.retrofit.HttpRequest;
import com.rx.mvp.zyzj.entity.req.TopNewReq;
import com.rx.mvp.zyzj.utils.EncryptUtil;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

/**
 * 用户相关业务
 *
 * @author ZhongDaFeng
 */
public class UserBiz extends BaseBiz {

    /**
     * 登录API
     */
    private final String API_LOGIN = "user/login";

    /**
     * 用户登录
     *
     * @param userName
     * @param password
     * @param lifecycle
     * @param callback
     */
    public void login(String userName, String password, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建请求参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("username", userName);
        request.put("password", password);
//        request.put(HttpRequest.API_URL, API_LOGIN);

        /**
         * 解析数据
         */
//        callback.setParseHelper(new ParseHelper() {
//            @Override
//            public Object[] parse(BaseResp resp) {
//                UserBean bean = new Gson().fromJson(jsonElement, UserBean.class);
//                Object[] obj = new Object[1];
//                obj[0] = bean;
//                return obj;
//            }
//        });

        /**
         * 发送请求
         */
//        getRequest().request(API_LOGIN, HttpRequest.Method.POST, request, lifecycle, callback);

    }
    public void getTopNew(LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建请求参数
         */
        TreeMap<String, Object> request = BaseBiz.getPostHeadMap();
//        request.put("username", userName);
//        request.put("password", password);

        /**
         * 解析数据
         */
//        callback.setParseHelper(new ParseHelper() {
//            @Override
//            public Object[] parse(JsonElement jsonElement) {
//                TopNewsInfoBean bean = new Gson().fromJson(jsonElement, TopNewsInfoBean.class);
//                Object[] obj = new Object[1];
//                obj[0] = bean;
//                return obj;
//            }
//        });

        TopNewReq req = new TopNewReq();
        req.setVer("5.6.1");
        req.setKey(EncryptUtil.md5Encrypt("testb2c"));
        req.setPlat("c2b_android");


        /**
         * 发送请求
         */
        getRequest().request("https://demoapi.brightoilonline.com/c2bappapi/v5.0/cardList/topnews", HttpRequest.Method.POST, req, lifecycle, callback);

    }

}
