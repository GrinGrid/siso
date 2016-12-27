package net.gringrid.siso.network.restapi;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by choijiho on 16. 9. 9..
 */
public interface SessionAPI {

    public static final String RESULT_NONE = "NONE";
    public static final String RESULT_RENEWAL = "RENEWAL";
    public static final String RESULT_ERROR = "ERROR";

    public static class Session{
        public String result;
    }

    @GET("session")
    Call<Session> isVaildSession();
}
