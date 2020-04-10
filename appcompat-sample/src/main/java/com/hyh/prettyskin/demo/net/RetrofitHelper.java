package com.hyh.prettyskin.demo.net;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static Retrofit sInstance;

    public static <T> T create(Context context, @NonNull Class<T> serverApi) {
        return getRetrofit(context).create(serverApi);
    }

    private static Retrofit getRetrofit(Context context) {
        if (sInstance != null) return sInstance;
        synchronized (RetrofitHelper.class) {
            if (sInstance == null) {
                sInstance = new Retrofit
                        .Builder()
                        .baseUrl("https://www.wanandroid.com/")
                        .addConverterFactory(new StringConverterFactory())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(createClient(context))//设置http客户端
                        .build();
            }
        }
        return sInstance;
    }


    private static OkHttpClient createClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(new Cache(context.getCacheDir(), 10 * 1024 * 1024))//缓存目录
                .retryOnConnectionFailure(true) //失败重连
                .readTimeout(30, TimeUnit.SECONDS)//读超时设置
                .connectTimeout(30, TimeUnit.SECONDS);//连接超时设置

        /*反射获取stetho网络拦截器*/
        Interceptor interceptor = StethoHelper.createStethoInterceptor();
        /*添加stetho的网络拦截器*/
        if (interceptor != null) {
            builder.addNetworkInterceptor(interceptor);
        }

        return builder.build();
    }
}