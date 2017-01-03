package net.gringrid.siso.network.restapi;

import net.gringrid.siso.models.Contact;
import net.gringrid.siso.models.Status;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 사용자 상태 변경
 *  변경종류
 * 00 : C 구인구직정보입력완료
 * 01 : A 자료불충분 거부
 * 02 : A 승인
 * 03 : S 시터풀 유효시간 만료
 * 04 : C 매칭완료로 정지요청
 * 05 : C 개인사정으로 정지요청
 * 06 : C 활성화요청
 */
public interface StatusAPI {

    @POST("user/status")
    Call<Status> setStatus(@Body Status status);
}
