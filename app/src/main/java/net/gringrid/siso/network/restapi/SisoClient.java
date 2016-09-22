package net.gringrid.siso.network.restapi;

import net.gringrid.siso.models.Personal;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by choijiho on 16. 9. 1..
 */
public interface SisoClient {

    /** USER **/
    @GET("user/checkUser/{email}")
    Call<User> checkUser(@Path("email") String email);

    @GET("user/{email}")
    Call<User> getUser(@Path("email") String email);

    @POST("user")
    Call<User> signUp(@Body User user);

    @POST("user/login")
    Call<Personal> login(@Body Personal personal);

    @POST("user/findEmail")
    Call<Personal> findEmail(@Body Personal personal);

    @POST("user/findPassword")
    Call<Personal> findPassword(@Body Personal personal);
}
