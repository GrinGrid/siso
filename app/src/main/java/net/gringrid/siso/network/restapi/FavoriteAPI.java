package net.gringrid.siso.network.restapi;

import net.gringrid.siso.models.Contact;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 관심등록/삭제
 */
public interface FavoriteAPI {

    @FormUrlEncoded
    @POST("favorite")
    Call<Contact> addFavorite(@Field("email") String email,
                              @Field("fav_email") String favorite_email);


    @DELETE("favorite/{email}/{fav_email}")
    Call<Contact> delFavorite(@Path("email") String email,
                              @Path("fav_email") String favorite_email);
}
