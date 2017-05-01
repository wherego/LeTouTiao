package com.tfn.letoutiao.net;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.tfn.letoutiao.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tf on 2017/4/29.
 */

public class QClient {

    private static QClient mQClient;

    private OkHttpClient.Builder mBuilder;

    private QClient(){
        initSettings();
    }

    public static QClient getInstance() {
        if (mQClient == null) {
            synchronized (QClient.class) {
                if (mQClient == null) {
                    mQClient = new QClient();
                }
            }
        }
        return mQClient;
    }

    public <T> T create(Class<T> service, String baseUrl){
        checkNotNull(service, "service is null");
        checkNotNull(baseUrl, "baseUrl is null");

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(service);
    }

    private <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;

    }


    private void initSettings() {
        //初始化okhttp
        mBuilder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);
        //判断是否为调试
        if(BuildConfig.DEBUG){
            //如果是，添加http拦截器
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mBuilder.addInterceptor(interceptor);
        }


    }

}
