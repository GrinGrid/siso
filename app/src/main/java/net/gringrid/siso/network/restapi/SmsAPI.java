package net.gringrid.siso.network.restapi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by choijiho on 16. 9. 9..
 */
public interface  SmsAPI {

    public static class SMS{
        public String phone;
        public String hashKey;
        public String authNum;
    }

    @POST("sms")
    Call<SMS> requestAuthSms(@Body SMS sms);

    @POST("sms/confirm")
    Call<SMS> confirmAuthNum(@Body SMS sms);
}
