package com.rx.mvp.zyzj.net.retrofit;

import com.rx.mvp.zyzj.common.HttpUrls;
import com.rx.mvp.zyzj.utils.LogUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * RetrofitUtils工具类
 *
 * @author ZhongDaFeng
 */
public class RetrofitUtils {
    /**
     * 接口地址
     */
//    public static final String BASE_API = "http://apicloud.mob.com/";
//    public static final String BASE_API = "http://39.108.7.224:3000/mock/15/auth-web/";

    public static final int CONNECT_TIME_OUT = 30;//连接超时时长x秒
    public static final int READ_TIME_OUT = 30;//读数据超时时长x秒
    public static final int WRITE_TIME_OUT = 30;//写数据接超时时长x秒
    private static RetrofitUtils mInstance = null;

    private RetrofitUtils() {
    }

    public static RetrofitUtils get() {
        if (mInstance == null) {
            synchronized (RetrofitUtils.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 设置okHttp
     *
     * @author ZhongDaFeng
     */
    private static OkHttpClient okHttpClient() {
        //开启Log
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.e("okHttp:" + message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
        return client;
    }

    /**
     * 获取Retrofit
     *
     * @author ZhongDaFeng
     */
    public Retrofit retrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient())
                .baseUrl(HttpUrls.BASE_API)
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

}
