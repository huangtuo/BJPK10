package com.rx.mvp.zyzj.biz;

import com.rx.mvp.zyzj.base.BaseBiz;
import com.rx.mvp.zyzj.common.HttpUrls;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.rx.mvp.zyzj.net.retrofit.HttpRequest;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

/**
 * Created by huangtuo on 2018/9/3.
 */

public class AnalysisBiz extends BaseBiz {
    private static AnalysisBiz analysisBiz;

    private AnalysisBiz() {

    }

    public static AnalysisBiz getInstance() {
        if (analysisBiz == null) {
            synchronized (AnalysisBiz.class) {
                if (analysisBiz == null)
                    return new AnalysisBiz();
            }
        }
        return analysisBiz;
    }

    /**
     * 遗漏分析
     * @param position
     * @param lifecycle
     * @param callback
     */
    public void analysisMiss(String position, LifecycleProvider lifecycle, HttpRxCallback callback){
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("id", position);
        /**
         * 发送请求
         */
        getRequest().request(HttpUrls.API_Analyze_Miss, HttpRequest.Method.GET, request, lifecycle, callback);
    }

    public void analysisHot(String position, String count, LifecycleProvider lifecycle, HttpRxCallback callback){
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("id", position);
        request.put("count", count);
        /**
         * 发送请求
         */
        getRequest().request(HttpUrls.API_Analyze_Hot, HttpRequest.Method.GET, request, lifecycle, callback);
    }

    /**
     * 指标遗漏分析
     * @param id
     * @param lifecycle
     * @param callback
     */
    public void targetMiss(String id, LifecycleProvider lifecycle, HttpRxCallback callback){
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("id", id);
        /**
         * 发送请求
         */
        getRequest().request(HttpUrls.API_Analyze_Target_Miss, HttpRequest.Method.GET, request, lifecycle, callback);
    }

    /**
     * 指标冷热分析
     * @param id
     * @param count
     * @param lifecycle
     * @param callback
     */
    public void targetHot(String id, String count, LifecycleProvider lifecycle, HttpRxCallback callback){
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("id", id);
        request.put("count", count);
        /**
         * 发送请求
         */
        getRequest().request(HttpUrls.API_Analyze_Target_Hot, HttpRequest.Method.GET, request, lifecycle, callback);
    }



    /**
     * 遗漏分析筛选条件
     * @param lifecycle
     * @param callback
     */
    public void getMissConfig(LifecycleProvider lifecycle, HttpRxCallback callback) {
        getRequest().request(HttpUrls.API_Miss_Config, HttpRequest.Method.GET, new TreeMap<>(), lifecycle, callback);
    }

    /**
     * 冷热分析筛选条件
     * @param lifecycle
     * @param callback
     */
    public void getHotConfig(LifecycleProvider lifecycle, HttpRxCallback callback) {
        getRequest().request(HttpUrls.API_Hot_Config, HttpRequest.Method.GET, new TreeMap<>(), lifecycle, callback);
    }

    /**
     * 指标遗漏分析筛选条件
     * @param lifecycle
     * @param callback
     */
    public void getTargetMissConfig(LifecycleProvider lifecycle, HttpRxCallback callback) {
        getRequest().request(HttpUrls.API_Target_Miss_Config, HttpRequest.Method.GET, new TreeMap<>(), lifecycle, callback);
    }

    /**
     * 指标冷热分析筛选条件
     * @param lifecycle
     * @param callback
     */
    public void getTargetHotConfig(LifecycleProvider lifecycle, HttpRxCallback callback) {
        getRequest().request(HttpUrls.API_Target_Hot_Config, HttpRequest.Method.GET, new TreeMap<>(), lifecycle, callback);
    }
}
