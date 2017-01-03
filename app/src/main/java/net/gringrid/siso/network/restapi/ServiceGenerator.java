package net.gringrid.siso.network.restapi;

import android.app.Activity;
import android.app.Service;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import net.gringrid.siso.BaseActivity;
import net.gringrid.siso.IndicatorDialog;
import net.gringrid.siso.RootActivity;
import net.gringrid.siso.util.SharedData;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by choijiho on 16. 9. 1..
 */
public class ServiceGenerator {
    private static ServiceGenerator instance;
    private static Activity mActivity;

    public static final String API_BASE_URL = "https://siso4u.net/";
    public static final String API_BASE_URL_DEV = "https://siso4u.net/dev";
    private static final String TAG = "jiho";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private ServiceGenerator(){}

    public static ServiceGenerator getInstance(Activity activity){
        mActivity = activity;
        if (instance == null){
            instance = new ServiceGenerator();
            setRequestInterceptor();
            setResponseInterceptor();
        }
        return instance;
    }

    private static void setRequestInterceptor(){
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();

                ((IndicatorDialog.UseIndicator)mActivity).showIndicator();

                Log.d(TAG, "intercept: request");
                if (original!=null){
                    Log.d(TAG, "intercept: request is not null");
                }

                if ( !TextUtils.isEmpty(SharedData.getSessionKey())){
                    requestBuilder.header(SharedData.SESSION_KEY, SharedData.getSessionKey());
                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
    }

    private static void setResponseInterceptor(){
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Log.d(TAG, "intercept: response");
                Response response = chain.proceed(chain.request());
                if (response!=null){
                    ((IndicatorDialog.UseIndicator)mActivity).hideIndicator();
                    Log.d(TAG, "intercept: response is not null");
                }
                return response;
            }
        });
    }

    public <S> S createService(Class<S> serviceClass){
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public static Retrofit retrofit(){
        return builder.build();
    }
}
