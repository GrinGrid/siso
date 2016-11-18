package net.gringrid.siso.network.restapi;

import net.gringrid.siso.models.SitterList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by choijiho on 16. 9. 9..
 */
public interface  SitterAPI {

    public static class SitterCount{
        public int count;
    }

    @GET("sitter/count/{email}")
    Call<SitterCount> getCountInCircleBoundary(@Path("email") String email);

    @GET("sitter/list/{email}")
    Call<SitterList> getListSiso(@Path("email") String email);
}
