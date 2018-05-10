package com.narancommunity.app.net;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @version V1.0
 * @description: ${TODO}(用一句话描述该文件做什么)
 */
public class ServiceFactory {

    //    public static final String API_BASE_URL = "http://118.178.122.9:8082";//正式的
    public static final String API_BASE_URL_HTML = "http://h5.naranc.com";//html host（正式）

    /**
     * 这里的Url已经没用了，因为所有的链接地址都是有端口的，在详细的{@link NRConfig}里进行配置
     */
    public static final String API_BASE_URL = "http://47.98.218.205";//测试的
//    public static final String API_BASE_URL_RECORD = "http://47.98.218.205:8083";//html host（测试）

    private static final OkHttpClient sHttpClient = new OkHttpClient.Builder()
            .addInterceptor(getInterceptor())
            .connectTimeout(AppConstants.TIME_KEEP, TimeUnit.SECONDS)
            .writeTimeout(AppConstants.TIME_KEEP, TimeUnit.SECONDS)
            .readTimeout(AppConstants.TIME_KEEP, TimeUnit.SECONDS)
            .build();

    private static HttpLoggingInterceptor getInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("OkHttp", "retrofitResponse = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

//    OkHttpClient getInterceptor() {
//        return new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                //获得请求信息，此处如有需要可以添加headers信息
//                Request request = chain.request();
//                //打印请求信息
//                syso("url:" + request.url());
//                syso("method:" + request.method());
//                syso("request-body:" + request.body());
//
//                //记录请求耗时
//                long startNs = System.nanoTime();
//                okhttp3.Response response;
//                try {
//                    //发送请求，获得相应，
//                    response = chain.proceed(request);
//                } catch (Exception e) {
//                    throw e;
//                }
//                long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
//                //打印请求耗时
//                syso("耗时:" + tookMs + "ms");
//                //使用response获得headers(),可以更新本地Cookie。
//                syso("headers==========");
//                Headers headers = response.headers();
//                syso(headers.toString());
//
//                //获得返回的body，注意此处不要使用responseBody.string()获取返回数据，原因在于这个方法会消耗返回结果的数据(buffer)
//                ResponseBody responseBody = response.body();
//
//                //为了不消耗buffer，我们这里使用source先获得buffer对象，然后clone()后使用
//                BufferedSource source = responseBody.source();
//                source.request(Long.MAX_VALUE); // Buffer the entire body.
//                //获得返回的数据
//                Buffer buffer = source.buffer();
//                //使用前clone()下，避免直接消耗
//                syso("response:" + buffer.clone().readString(Charset.forName("UTF-8")));
//                return response;
//            }
//        };
//    }

//    private static void syso(String msg) {
//        Log.w("OkHttp", msg);
//    }

    public static <S> S createNewService(Class<S> serviceClass) {
//        if (!NRApplication.isRelease) {
        return createOauthService(API_BASE_URL, serviceClass);
//        } else
//            return createOauthService(API_BASE_URL, serviceClass);
    }

    public static <S> S createOauthService(String baseUrl, Class<S> serviceClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(sHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

}
