package net.gringrid.siso.network.restapi;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by choijiho on 16. 9. 9..
 */
public interface AddrAPI {

    static final String TAG = "jiho";

    public static class AddrInputs{
        @SerializedName("confmKey")
        public String confmKey;

        @SerializedName("currentPage")
        public int currentPage;

        @SerializedName("countPerPage")
        public int countPerPage;

        @SerializedName("keyword")
        public String keyword;
    }

    @Root
    public static class AddrInput{
        @Element(name="confmKey")
        public String confmKey;

        @Element(name="currentPage")
        public int currentPage;

        @Element(name="countPerPage")
        public int countPerPage;

        @Element(name="keyword")
        public String keyword;
    }

    @Root(name="results")

    public class AddrOutput{
        @Element(name="common")
        public Common common;

        @ElementList(required = false, inline = true)
        public List<Juso> juso;

        @Override
        public String toString() {
            String result="";
            if(common!=null){
                result += "[Common] : "+common.toString();
            }
            if(juso!=null){
                Log.d(TAG, "toString: JUSO SIZE : "+juso.size());
                for(Juso jusoObj : juso){
                    result += "[Juso] : "+jusoObj.toString();
                }
            }
            return result;
        }
    }

    @Root(name="common")
    public class Common{
        @Element(name="totalCount")
        public String totalCount;

        @Element(name="currentPage")
        public int currentPage;

        @Element(name="countPerPage")
        public int countPerPage;

        @Element(name="errorCode")
        public String errorCode;

        @Element(name="errorMessage")
        public String errorMessage;

        @Override
        public String toString() {
            return
            "totalCount : "+totalCount+
            ", currentPage : "+currentPage+
            ", countPerPage : "+countPerPage+
            ", errorCode : "+errorCode+
            ", errorMessage : "+errorMessage;
        }
    }

    @Root
    public class Juso{
        @Element(required = false, name="roadAddr")
        public String roadAddr;

        @Element(required = false, name="roadAddrPart1")
        public String roadAddrPart1;

        @Element(required = false, name="roadAddrPart2")
        public String roadAddrPart2;

        @Element(required = false, name="jibunAddr")
        public String jibunAddr;

        @Element(required = false, name="engAddr")
        public String engAddr;

        @Element(required = false, name="zipNo")
        public String zipNo;

        @Element(required = false, name="admCd")
        public String admCd;

        @Element(required = false, name="rnMgtSn")
        public String rnMgtSn;

        @Element(required = false, name="bdMgtSn")
        public String bdMgtSn;

        @Element(required = false, name="detBdNmList")
        public String depBdNmList;

        @Override
        public String toString() {
            return
                "roadAddr : "+roadAddr+
                ", roadAddrPart1 : "+roadAddrPart1+
                ", roadAddrPart2 : "+roadAddrPart2+
                ", jibunAddr : "+jibunAddr+
                ", engAddr : "+engAddr+
                ", zipNo : "+zipNo+
                ", admCd : "+admCd+
                ", rnMgtSn : "+rnMgtSn+
                ", bdMgtSn : "+bdMgtSn+
                ", depBdNmList : "+depBdNmList
                ;
        }
    }

    @GET("addrlink/addrLinkApi.do")
    Call<AddrOutput> getAddr(
            @Query("currentPage") int currentPage,
            @Query("countPerPage") int countPerPage,
            @Query("keyword") String keyword,
            @Query("confmKey") String confmKey);


    @POST("addrlink/addrLinkApi.do")
    Call<AddrOutput> getAddr(@Body AddrInput addrInput);
}
