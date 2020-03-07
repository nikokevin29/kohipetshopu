package com.p3l.kohipetshopu.API;

import com.p3l.kohipetshopu.UserData.JenisHewanDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    //JenisHewan

    @GET("/api/jenis_hewan")
    Call<List<JenisHewanDAO>> getAllJenis();
    @POST("/api/jenis_hewan")
    @FormUrlEncoded
    Call<JenisHewanDAO> createJenis (@Field("nama")String nama);
    @POST("/api/jenis_hewan/{idjenis}")
    @FormUrlEncoded
    Call<JenisHewanDAO> editJenis(@Field("nama") String nama);

    @POST("/api/jenis_hewan/{idjenis}")
    @FormUrlEncoded
    Call<Void> deleteJenis(@Field("idjenis") String idjenis);
}
