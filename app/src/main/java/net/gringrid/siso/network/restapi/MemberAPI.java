package net.gringrid.siso.network.restapi;

import net.gringrid.siso.models.Personal;
import net.gringrid.siso.util.SharedData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by choijiho on 16. 8. 19..
 */
public interface MemberAPI {
    String SISO_AUTH_URL = "http://siso4u.net/";
    String DAUM_API_URL = "https://apis.daum.net/local/geo/";

    @GET("api/users/{email}")
    Call<Personal> getMember(
            @Path("email") String email,
            @Header(SharedData.SESSION_KEY) String session_key
    );

    @GET("api/member/checkUser/{email}")
    Call<Personal> checkUser(@Path("email") String email);

    @GET("addr2coord")
    Call<Personal> getGPS(
        @Query("apikey") String apikey,
        @Query("output") String outputType,
        @Query("q") String addr
    );

    @POST("auth/signup")
    Call<Personal> signUp(@Body Personal personal);
}
