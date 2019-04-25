package com.rx.mvp.zyzj.net.retrofit;

import com.google.gson.Gson;
import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.rx.mvp.zyzj.net.observer.HttpRxObservable;
import com.rx.mvp.zyzj.entity.req.BaseReq;
import com.rx.mvp.zyzj.utils.DES3;
import com.rx.mvp.zyzj.utils.LogUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.TreeMap;
import com.rx.mvp.zyzj.net.Api.Api;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Http请求类
 *
 * @author ZhongDaFeng
 */
public class HttpRequest {

    private final String appKey = "1889b37351288";
    private final String k_key = "key";

    public enum Method {
        GET,
        POST
    }


    /**
     * 发送请求
     * 备注:不管理生命周期
     *
     * @param method   请求方式
     * @param reqParam    参数集合
     * @param callback 回调
     */
    public void request(String url, Method method, BaseReq reqParam, HttpRxCallback callback) {

        Observable<ResponseBody> apiObservable = handleRequest(url, method, reqParam);

        HttpRxObservable.getObservable(apiObservable, callback).subscribe(callback);

    }


    /**
     * 发送请求
     * 备注:自动管理生命周期
     *
     * @param method    请求方式
     * @param lifecycle 实现RxActivity/RxFragment 参数为空不管理生命周期
     * @param prams     参数集合
     * @param callback  回调
     */
    public void request(String url, Method method, TreeMap<String, Object> prams, LifecycleProvider lifecycle, HttpRxCallback callback) {
        Observable<ResponseBody> apiObservable = handleRequest2(url, method, prams);

        HttpRxObservable.getObservable(apiObservable, lifecycle, callback).subscribe(callback);
    }

    /**
     * 发送请求
     * 备注:自动管理生命周期
     *
     * @param method    请求方式
     * @param lifecycle 实现RxActivity/RxFragment 参数为空不管理生命周期
     * @param reqParam     参数集合
     * @param callback  回调
     */
    public void request(String url, Method method, BaseReq reqParam, LifecycleProvider lifecycle, HttpRxCallback callback) {
        Observable<ResponseBody> apiObservable = handleRequest(url, method, reqParam);

        HttpRxObservable.getObservable(apiObservable, lifecycle, callback).subscribe(callback);
    }


    /**
     * 发送请求
     * 备注:手动指定生命周期-Activity
     *
     * @param method    请求方式
     * @param lifecycle 实现RxActivity
     * @param event     指定生命周期
     * @param reqParam     参数集合
     * @param callback  回调
     */
    public void request(String url, Method method, BaseReq reqParam, LifecycleProvider<ActivityEvent> lifecycle, ActivityEvent event, HttpRxCallback callback) {
        Observable<ResponseBody> apiObservable = handleRequest(url, method, reqParam);

        HttpRxObservable.getObservable(apiObservable, lifecycle, event, callback).subscribe(callback);
    }


    /**
     * 发送请求
     * 备注:手动指定生命周期-Fragment
     *
     * @param method    请求方式
     * @param lifecycle 实现RxFragment
     * @param event     指定生命周期
     * @param reqParam     参数集合
     * @param callback  回调
     */
    public void request(String url, Method method, BaseReq reqParam, LifecycleProvider<FragmentEvent> lifecycle, FragmentEvent event, HttpRxCallback callback) {
        Observable<ResponseBody> apiObservable = handleRequest(url, method, reqParam);

        HttpRxObservable.getObservable(apiObservable, lifecycle, event, callback).subscribe(callback);
    }


    /**
     * 预处理请求
     *
     * @param method 请求方法
     * @param reqParam  参数集合
     * @return
     */
    private Observable<ResponseBody> handleRequest(String url, Method method, BaseReq reqParam) {
        String token;
        if(ZyzjApplication.application.getServiceModel() != null){
            token = ZyzjApplication.application.getServiceModel().getToken();
        }else{
            token = "";
        }

        //获取基础参数
        TreeMap<String, Object> request = getBaseRequest();
        //添加业务参数
        String json = new Gson().toJson(reqParam);
        request.put("params", json);

//        String encode;
//        try{
//            encode = DES3.encode(new Gson().toJson(reqParam));// 请求的数据字符串进行MD5加密
//        }catch (Exception e){
//            encode = "";
//        }
//        request.clear();
//        request.put("params", encode);
        LogUtils.d(json);

        Observable<ResponseBody> apiObservable;
        switch (method) {
            case GET:
                apiObservable = RetrofitUtils.get().retrofit().create(Api.class).get(url, token, request);
                break;
            case POST:
                apiObservable = RetrofitUtils.get().retrofit().create(Api.class).post(url, token, request);
                break;
            default:
                apiObservable = RetrofitUtils.get().retrofit().create(Api.class).post(url, token, request);
                break;
        }
        return apiObservable;
    }

    private Observable<ResponseBody> handleRequest2(String url, Method method, TreeMap<String, Object> prams) {
        String token;
        if(ZyzjApplication.application.getServiceModel() != null){
            token = ZyzjApplication.application.getServiceModel().getToken();
        }else{
            token = "";
        }

        //获取基础参数
//        TreeMap<String, Object> request = getBaseRequest();
//        //添加业务参数
//        request.putAll(prams);

//        Gson gs = new Gson();
//        String encode;
//        try{
//            encode = DES3.encode(gs.toJson(prams));// 请求的数据字符串进行MD5加密
//        }catch (Exception e){
//            encode = "";
//        }
//        request.clear();
//        request.put("params", encode);

        LogUtils.d(new Gson().toJson(prams));

        Observable<ResponseBody> apiObservable;
        switch (method) {
            case GET:
                apiObservable = RetrofitUtils.get().retrofit().create(Api.class).get(url, token, prams);
                break;
            case POST:
                apiObservable = RetrofitUtils.get().retrofit().create(Api.class).post(url, token, prams);
                break;
            default:
                apiObservable = RetrofitUtils.get().retrofit().create(Api.class).post(url, token, prams);
                break;
        }
        return apiObservable;
    }

    /**
     * 获取基础request参数
     */
    private TreeMap<String, Object> getBaseRequest() {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put(k_key, appKey);
        return map;
    }


}
