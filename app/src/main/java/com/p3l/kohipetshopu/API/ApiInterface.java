package com.p3l.kohipetshopu.API;

import com.p3l.kohipetshopu.JenisHewan.JenisHewanDAO;
import com.p3l.kohipetshopu.UkuranHewan.UkuranHewanDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiInterface {
    //JenisHewan
    @GET("/api/jenis_hewan")
    Call<List<JenisHewanDAO>> getAllJenis();

//    @GET("/api/jenis_hewan/search/{nama}")
//    Call<JenisHewanDAO> searchNama(@Path("nama") String nama);

    @POST("/api/jenis_hewan")
    @FormUrlEncoded
    Call<JenisHewanDAO> createJenis (@Field("nama")String nama);

    @PUT("/api/jenis_hewan/{idjenis}")
    @FormUrlEncoded
    Call<JenisHewanDAO> editJenis(@Path("idjenis") String idjenis,
                                  @Field("nama") String nama);

    @DELETE("/api/jenis_hewan/{idjenis}")
    Call<Void> deleteJenis(@Path("idjenis") String idjenis);

    //UkuranHewan
    @GET("/api/ukuran_hewan")
    Call<List<UkuranHewanDAO>> getAllUkuran();

//    @GET("/api/ukuran_hewan/search/{nama}")
//    Call<UkuranHewanDAO> searchNama(@Path("nama") String nama);

    @POST("/api/ukuran_hewan")
    @FormUrlEncoded
    Call<UkuranHewanDAO> createUkuran (@Field("nama")String nama);

    @PUT("/api/ukuran_hewan/{idukuran}")
    @FormUrlEncoded
    Call<UkuranHewanDAO> editUkuran(@Path("idukuran") String idukuran,
                                  @Field("nama") String nama);

    @DELETE("/api/ukuran_hewan/{idukuran}")
    Call<Void> deleteUkuran(@Path("idukuran") String idukuran);
}
