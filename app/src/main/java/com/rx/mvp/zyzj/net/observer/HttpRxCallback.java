package com.rx.mvp.zyzj.net.observer;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.rx.mvp.zyzj.common.Constants;
import com.rx.mvp.zyzj.net.exception.ApiException;
import com.rx.mvp.zyzj.net.exception.ExceptionEngine;
import com.rx.mvp.zyzj.net.retrofit.HttpRequestListener;
import com.rx.mvp.zyzj.net.retrofit.RxActionManagerImpl;
import com.rx.mvp.zyzj.utils.LogUtils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * 适用Retrofit网络请求Observer(监听者)
 * 备注:
 * 1.重写onSubscribe，添加请求标识
 * 2.重写onError，封装错误/异常处理，移除请求
 * 3.重写onNext，移除请求
 * 4.重写cancel，取消请求
 *
 * @author ZhongDaFeng
 */
public abstract class HttpRxCallback<T> implements Observer<T>, HttpRequestListener {

    private String mTag;//请求标识
//    private ParseHelper parseHelper;//数据解析
    private Class<?> responseClass;

    public HttpRxCallback(Class<?> responseClass) {
        this.mTag = String.valueOf(System.currentTimeMillis());
        this.responseClass = responseClass;
    }

    public HttpRxCallback(String tag, Class<?> responseClass) {
        this.mTag = tag;
        this.responseClass = responseClass;
    }

    @Override
    public void onError(Throwable e) {
        RxActionManagerImpl.getInstance().remove(mTag);
        if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;
            int code = exception.getCode();
            String msg = exception.getMsg();
            if (code == 1001) { //系统公告(示例)
                //此处在UI主线程
                onError(code, msg);
            } else if (code == Constants.TOKEN_OUT) {//token失效
                //处理对应的逻辑
                onError(code, msg);
            } else {//其他错误回调
                onError(code, msg);
            }
        } else {
            onError(ExceptionEngine.UN_KNOWN_ERROR, "未知错误");
        }

    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onNext(@NonNull T value) {
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.getInstance().remove(mTag);
        }

        try {
            String jsonStr = (String) value;
            if(jsonStr.length() > 0 && jsonStr.startsWith("[")){
                //返回的是数组添加 “list” key
                jsonStr = "{\"list\":" + jsonStr + "}";
            }
            JsonElement jsonElement = new JsonParser().parse(jsonStr);
            onSuccess(new Gson().fromJson(jsonElement, responseClass));
        } catch (JsonSyntaxException jsonException) {
            LogUtils.e("JsonSyntaxException:" + jsonException.getMessage());
            onError(ExceptionEngine.ANALYTIC_SERVER_DATA_ERROR, "解析错误");
        }

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.getInstance().add(mTag, d);
        }
    }

    /**
     * 手动取消请求
     */
    @Override
    public void cancel() {
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.getInstance().cancel(mTag);
        }
    }


    /**
     * 请求被取消回调
     */
    @Override
    public void onCanceled() {
        onCancel();
    }

    /**
     * 是否已经处理
     *
     * @author ZhongDaFeng
     */
    public boolean isDisposed() {
        if (TextUtils.isEmpty(mTag)) {
            return true;
        }
        return RxActionManagerImpl.getInstance().isDisposed(mTag);
    }

    /**
     * 设置解析回调
     *
     * @param parseHelper
     */
//    public void setParseHelper(ParseHelper parseHelper) {
//        this.parseHelper = parseHelper;
//    }

    /**
     * 成功回调
     *
     * @param resp
     */
    public abstract void onSuccess(Object resp);

    /**
     * 失败回调
     *
     * @param code
     * @param desc
     */
    public abstract void onError(int code, String desc);

    /**
     * 取消回调
     */
    public abstract void onCancel();

}
