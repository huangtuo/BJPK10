package com.rx.mvp.zyzj.net.function;

import com.google.gson.Gson;
import com.rx.mvp.zyzj.net.exception.ExceptionEngine;
import com.rx.mvp.zyzj.net.exception.ServerException;
import com.rx.mvp.zyzj.net.retrofit.HttpResponse;
import com.rx.mvp.zyzj.utils.LogUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 服务器结果处理函数
 *
 * @author ZhongDaFeng
 */
public class ServerResultFunction implements Function<ResponseBody, Object> {
    @Override
    public Object apply(@NonNull ResponseBody responseBody) throws Exception {
        //打印服务器回传结果

        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();

        Charset charset = Charset.forName("Utf-8");
//        MediaType contentType = responseBody.contentType();
//        if(contentType != null){
//            charset = contentType.charset(Charset.forName("Utf-8"));
        //获取Response的body的字符串 并打印
        String str = buffer.clone().readString(charset);
        LogUtils.d("服务器返回报文--->" + str);
        //解密后数据
//            String ds = DES3.decode(str);
//            LogUtils.e("HttpResponse:" + ds);
        HttpResponse response;
        try {
            response = new Gson().fromJson(str, HttpResponse.class);
        } catch (Exception e) {
            throw new ServerException(ExceptionEngine.ANALYTIC_SERVER_DATA_ERROR, "解析错误");//抛出服务器错误
        }
        if (response.isSuccess()) {
            try {
                return new Gson().toJson(response.getData());
            } catch (Exception e) {
                throw new ServerException(ExceptionEngine.ANALYTIC_SERVER_DATA_ERROR, "解析错误");//抛出服务器错误
            }

        } else {
            throw new ServerException(response.getCode(), response.getMessage());//抛出服务器错误
        }

//        }
//
//        return null;
    }
}