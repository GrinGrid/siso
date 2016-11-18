package net.gringrid.siso.network.restapi;

import net.gringrid.siso.models.SitterList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by choijiho on 16. 9. 9..
 */
public interface ParentAPI {

    public static class ParentCount{
        public int count;
    }

    @GET("parent/count/{email}")
    Call<ParentCount> getCountInCircleBoundary(@Path("email") String email);

    @GET("parent/list/{email}")
    Call<SitterList> getListSiso(@Path("email") String email);
}