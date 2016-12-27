package net.gringrid.siso.network.restapi;

import net.gringrid.siso.models.PushListItem;
import net.gringrid.siso.models.SitterDetail;
import net.gringrid.siso.models.SitterList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by choijiho on 16. 9. 9..
 */
public interface PushAPI {

    @GET("push/list/{email}")
    Call<List<PushListItem>> getList(@Path("email") String email);

}
