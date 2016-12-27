package net.gringrid.siso.network.restapi;

import net.gringrid.siso.models.SitterDetail;
import net.gringrid.siso.models.SitterList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by choijiho on 16. 9. 9..
 */
public interface ConfigAPI {

    public static class Config{
        public String versionCode;
        public String msg;
    }

    @GET("config")
    Call<Config> getConfig();
}
