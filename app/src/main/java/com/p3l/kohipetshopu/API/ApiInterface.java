package com.p3l.kohipetshopu.API;

import com.p3l.kohipetshopu.JenisHewan.JenisHewanDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiInterface {
    //JenisHewan
    @GET("/api/jenis_hewan")
    Call<List<JenisHewanDAO>> getAllJenis();
    @POST("/api/jenis_hewan")
    @FormUrlEncoded
    Call<JenisHewanDAO> createJenis (@Field("nama")String nama);
    @PUT("/api/jenis_hewan/{idjenis}")
    @FormUrlEncoded //kau butuh @FormUrlEncoded tu kalo mau ngeattach data di body
    Call<JenisHewanDAO> editJenis(@Path("idjenis") String idjenis,
                                  @Field("nama") String nama);
    @DELETE("/api/jenis_hewan/{idjenis}")
    Call<Void> deleteJenis(@Path("idjenis") String idjenis);
}
