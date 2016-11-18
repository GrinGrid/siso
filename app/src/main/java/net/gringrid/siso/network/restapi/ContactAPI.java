package net.gringrid.siso.network.restapi;

import net.gringrid.siso.models.Contact;
import net.gringrid.siso.models.SitterList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 연락처 요청/수락/거절/취소/삭제
 */
public interface ContactAPI {

    @POST("contact")
    Call<Contact> request(@Body Contact contact);

    @POST("contact/accept")
    Call<Contact> accept(@Body Contact contact);

    @POST("contact/reject")
    Call<Contact> reject(@Body Contact contact);

    @DELETE("contact")
    Call<Contact> delete(@Body Contact contact);
}
