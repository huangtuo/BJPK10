package com.rx.mvp.zyzj.biz;

import com.rx.mvp.zyzj.base.BaseBiz;
import com.rx.mvp.zyzj.common.HttpUrls;
import com.rx.mvp.zyzj.entity.req.RegisterReq;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.rx.mvp.zyzj.net.retrofit.HttpRequest;
import com.rx.mvp.zyzj.utils.EncryptUtil;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

/**
 * Created by huangtuo on 2018/8/3.
 */

public class MyBiz extends BaseBiz {
    private static MyBiz myBiz;

    private MyBiz(){

    }

    public static MyBiz getInstance(){
        if(myBiz == null){
            synchronized (MyBiz.class){
                if(myBiz == null){
                    myBiz = new MyBiz();
                }
            }
        }
        return myBiz;
    }



    /**
     * 注册
     * @param userName
     * @param password
     * @param lifecycle
     * @param callback
     */
    public void register(String userName, String password, LifecycleProvider lifecycle, HttpRxCallback callback){
//        RegisterReq req = new RegisterReq();
//        req.setUserName(userName);
//        req.setPassword(password);

        TreeMap<String, Object> request = new TreeMap<>();
        request.put("userName", userName);
        request.put("password", EncryptUtil.md5Encrypt(password));
                /**
                 * 发送请求
                 */
        getRequest().request(HttpUrls.API_Register, HttpRequest.Method.POST, request, lifecycle, callback);

    }

    /**
     * 退出登录
     * @param lifecycle
     * @param callback
     */
    public void logout(LifecycleProvider lifecycle, HttpRxCallback callback){
        getRequest().request(HttpUrls.API_Logout, HttpRequest.Method.POST, new TreeMap<>(), lifecycle, callback);
    }

    /**
     * 用户登录
     * @param userName
     * @param password
     * @param lifecycle
     * @param callback
     */
    public void login(String userName, String password, LifecycleProvider lifecycle, HttpRxCallback callback){

        TreeMap<String, Object> request = new TreeMap<>();
        request.put("userName", userName);
        request.put("password", EncryptUtil.md5Encrypt(password));
        /**
         * 发送请求
         */
        getRequest().request(HttpUrls.API_Login, HttpRequest.Method.POST, request, lifecycle, callback);
    }

    /**
     * 修改登录密码
     * @param lifecycle
     * @param callback
     */
    public void updatePsw(String oldPsw, String newPsw, LifecycleProvider lifecycle, HttpRxCallback callback){
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("oldPassword", EncryptUtil.md5Encrypt(oldPsw));
        request.put("password", EncryptUtil.md5Encrypt(newPsw));
        getRequest().request(HttpUrls.API_Update_PSW, HttpRequest.Method.POST, request, lifecycle, callback);
    }

    /**
     * 获取用户信息
     * @param lifecycle
     * @param callback
     */
    public void getUserInfo(LifecycleProvider lifecycle, HttpRxCallback callback){
        getRequest().request(HttpUrls.API_GetUserInfo, HttpRequest.Method.POST, new TreeMap<>(), lifecycle, callback);
    }

    /**
     * 版本更新
     * @param appVersion
     * @param lifecycle
     * @param callback
     */
    public void updateVersion(String appVersion, LifecycleProvider lifecycle, HttpRxCallback callback){
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("appVersion", appVersion);
        request.put("osType", "2");
        getRequest().request(HttpUrls.API_GetVersion, HttpRequest.Method.POST, request, lifecycle, callback);
    }

    /**
     * 获取我的反馈列表
     * @param lifecycle
     * @param callback
     */
    public void getMyFeedback(LifecycleProvider lifecycle, HttpRxCallback callback){
        getRequest().request(HttpUrls.API_SuggestionList, HttpRequest.Method.POST, new TreeMap<>(), lifecycle, callback);
    }

    /**
     * 提交反馈信息
     * @param topic
     * @param content
     * @param base64String
     * @param lifecycle
     * @param callback
     */
    public void submitFeedback(String topic, String content, String base64String, LifecycleProvider lifecycle, HttpRxCallback callback){
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("topic", topic);
        request.put("content", content);
        request.put("base64String", base64String);
        getRequest().request(HttpUrls.API_SubmitSuggestion, HttpRequest.Method.POST, request, lifecycle, callback);
    }

    /**
     * 获取反馈详情
     * @param id
     * @param lifecycle
     * @param callback
     */
    public void getFeedbackDetails(String id, LifecycleProvider lifecycle, HttpRxCallback callback){
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("id", id);
        getRequest().request(HttpUrls.API_SuggestionDetail, HttpRequest.Method.POST, request, lifecycle, callback);
    }

    /**获取我的认证信息
     *
     * @param lifecycle
     * @param callback
     */
    public void getMyAuthor(LifecycleProvider lifecycle, HttpRxCallback callback){
        getRequest().request(HttpUrls.API_GetBuyInfo, HttpRequest.Method.POST, new TreeMap<>(), lifecycle, callback);
    }


}
