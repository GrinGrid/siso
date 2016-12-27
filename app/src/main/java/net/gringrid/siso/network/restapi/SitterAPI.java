package net.gringrid.siso.network.restapi;

import net.gringrid.siso.models.SitterDetail;
import net.gringrid.siso.models.SitterList;
import net.gringrid.siso.models.User;

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

    @GET("sitter/favList/{email}")
    Call<SitterList> getFavListSiso(@Path("email") String email);

    @GET("sitter/reqList/{email}")
    Call<SitterList> getReqListSiso(@Path("email") String email);

    @GET("sitter/rcvList/{email}")
    Call<SitterList> getRcvListSiso(@Path("email") String email);

    @GET("sitter/detail/{email}/{trg_email}")
    Call<SitterDetail> getDetail(@Path("email") String email,
                                 @Path("trg_email") String trg_email);
}
