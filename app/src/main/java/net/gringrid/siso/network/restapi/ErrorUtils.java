package net.gringrid.siso.network.restapi;


import net.gringrid.siso.models.Personal;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by choijiho on 16. 9. 1..
 */
public class ErrorUtils {
    /*
    ERR000	해당하는 데이터가 존재하지 않습니다.
    ERR010	해당 이메일 계정이 존재하지 않습니다.
    ERR011	해당 이메일 계정이 이미 존재합니다.
    ERR012	계정 비밀번호 오류입니다.
    ERR020	로그인이 필요한 서비스입니다.
    ERR021	로그인 세션이 만료되었습니다.
    ERR201	시스템 오류가 발생하였습니다.
    ERR999	정의되지 않은 에러입니다.
    */
    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter = ServiceGenerator.retrofit().responseBodyConverter(APIError.class, new Annotation[0]);


//        SisoClient client = ServiceGenerator.createService(SisoClient.class);
        Personal personal = new Personal();



        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError();
        }

        if ( error.msgCode().equals("ERR021")){
            personal.email = "nisclan10@hotmail.com";
            personal.passwd = "tjswndqkqh";
//            Call<Member> call = client.login(member);
//            call.enqueue(new Callback<Member>() {
//
//                @Override
//                public void onResponse(Call<Member> call, Response<Member> response) {
//                }
//
//                @Override
//                public void onFailure(Call<Member> call, Throwable t) {
//
//                }
//            });
        }

        return error;
    }
}
