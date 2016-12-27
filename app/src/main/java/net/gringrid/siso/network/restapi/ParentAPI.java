package net.gringrid.siso.network.restapi;

import net.gringrid.siso.models.ParentDetail;
import net.gringrid.siso.models.ParentList;
import net.gringrid.siso.models.ParentList;

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
    Call<ParentList> getListSiso(@Path("email") String email);

    @GET("parent/favList/{email}")
    Call<ParentList> getFavListSiso(@Path("email") String email);

    @GET("parent/reqList/{email}")
    Call<ParentList> getReqListSiso(@Path("email") String email);

    @GET("parent/rcvList/{email}")
    Call<ParentList> getRcvListSiso(@Path("email") String email);

    @GET("parent/detail/{email}/{trg_email}")
    Call<ParentDetail> getDetail(@Path("email") String email,
                                 @Path("trg_email") String trg_email);
}
