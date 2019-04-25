package com.rx.mvp.zyzj.net.Api;

import java.util.TreeMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Api接口
 *
 * @author ZhongDaFeng
 */
public interface Api {


    /**
     * GET请求
     *
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @GET
    Observable<ResponseBody> get(@Url String url, @Header("token") String token, @QueryMap TreeMap<String, Object> request);

    /**
     * POST请求
     *
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> post(@Url String url,@Header("token") String token, @FieldMap TreeMap<String, Object> request);
}
