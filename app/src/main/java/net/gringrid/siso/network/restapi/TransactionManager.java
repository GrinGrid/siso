package net.gringrid.siso.network.restapi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by choijiho on 16. 9. 12..
 */
public class TransactionManager {


    private static TransactionManager instance;

    private TransactionManager() {
    }

    public static TransactionManager getInstance() {
        if ( instance == null ) {
            instance = new TransactionManager();
        }
        return instance;
    }

    public void exetueTR(Call<?> call, Callback<?> callback){
    }
}
