package net.gringrid.siso.network.restapi;

import net.gringrid.siso.models.Member;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by choijiho on 16. 8. 19..
 */
public interface MemberAPI {
    String ENDPOINT = "http://siso4u.net/api/";

    @GET("users/{email}")
    Call<Member> getMember(@Path("email") String email);
}
