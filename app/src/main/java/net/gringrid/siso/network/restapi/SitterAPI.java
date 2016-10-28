package net.gringrid.siso.network.restapi;

import net.gringrid.siso.models.SitterList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by choijiho on 16. 9. 9..
 */
public interface  SitterAPI {

    public static class Sitter{
        public int count;
    }

    @GET("sitter/count/{email}")
    Call<Sitter> getCountInCircleBoundary(@Path("email") String email);

    @GET("sitter/list/{email}")
    Call<SitterList> getListSiso(@Path("email") String email);
}
