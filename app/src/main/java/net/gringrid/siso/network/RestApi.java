package net.gringrid.siso.network;


import net.gringrid.siso.models.Personal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by choijiho on 16. 8. 19..
 */
public class RestApi {
    private static RestApi instance;

    public interface MemberService{
       @GET("member/join")
        Call<List<Personal>> listRepos(@Path("user") String user);
    }

    public static RestApi getInstance(){
        if (instance == null){
            instance = new RestApi();
        }
        return instance;
    }

    private RestApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://siso4u.net/api")
                .build();
        MemberService service = retrofit.create(MemberService.class);
        Call<List<Personal>> repos = service.listRepos("");
    }
}
