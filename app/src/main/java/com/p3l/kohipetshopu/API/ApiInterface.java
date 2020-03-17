package com.p3l.kohipetshopu.API;

import com.p3l.kohipetshopu.JenisHewan.JenisHewanDAO;
import com.p3l.kohipetshopu.Layanan.LayananDAO;
import com.p3l.kohipetshopu.UkuranHewan.UkuranHewanDAO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/api/pegawai/login")
    Call<ResponseBody> loginRequest(@Field("username") String username,
                                    @Field("password") String password);

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

    //Layanan
    @GET("/api/layanan")
    Call<List<LayananDAO>> getAllLayanan();

    @GET("/api/layanan/search/{nama}")
    Call<LayananDAO> searchNama(@Path("nama") String nama);

    @POST("/api/layanan")
    @FormUrlEncoded
    Call<LayananDAO> createLayanan (@Field("nama")String nama,
                                    @Field("harga") String harga);

    @PUT("/api/layanan/{idlayanan}")
    @FormUrlEncoded
    Call<LayananDAO> editLayanan(@Path("idlayanan") String idlayanan,
                                    @Field("nama") String nama,
                                    @Field("harga") String harga);

    @DELETE("/api/layanan/{idlayanan}")
    Call<Void> deleteLayanan(@Path("idlayanan") String idlayanan);
}
