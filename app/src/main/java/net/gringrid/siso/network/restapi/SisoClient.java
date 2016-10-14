package net.gringrid.siso.network.restapi;

import net.gringrid.siso.models.Image;
import net.gringrid.siso.models.Personal;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @PUT("user")
    Call<User> modify(@Body User user);

    @POST("user/login")
    Call<User> login(@Body Personal personal);

    @POST("user/findEmail")
    Call<Personal> findEmail(@Body Personal personal);

    @FormUrlEncoded
    @POST("user/findPassword")
    Call<ResponseBody> findPassword(@Field("email") String email);

    @Multipart
    @POST("img")
    Call<Image> uploadImg(
            @Part("description")RequestBody description,
            @Part("email")RequestBody email,
            @Part("gubun")RequestBody gubun,
            @Part MultipartBody.Part file);

}
