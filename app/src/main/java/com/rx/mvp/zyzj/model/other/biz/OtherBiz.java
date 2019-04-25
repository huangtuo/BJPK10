package com.rx.mvp.zyzj.model.other.biz;

import com.rx.mvp.zyzj.base.BaseBiz;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

/**
 * 其他业务类
 *
 * @author ZhongDaFeng
 */
public class OtherBiz extends BaseBiz {

    /**
     * 号码归属地查询API
     */
    private final String API_PHONE_QUERY = "v1/mobile/address/query";

    public void phoneQuery(String phone, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("phone", phone);

        /**
         * 解析数据
         */
//        callback.setParseHelper(new ParseHelper() {
//            @Override
//            public Object[] parse(JsonElement jsonElement) {
//                AddressBean bean = new Gson().fromJson(jsonElement, AddressBean.class);
//                Object[] obj = new Object[1];
//                obj[0] = bean;
//                return obj;
//            }
//        });

        /**
         * 发送请求
         */
//        getRequest().request(API_PHONE_QUERY, HttpRequest.Method.GET, request, lifecycle, callback);

    }



}
