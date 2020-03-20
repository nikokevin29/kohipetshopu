package com.p3l.kohipetshopu.API;

import com.p3l.kohipetshopu.JenisHewan.JenisHewanDAO;
import com.p3l.kohipetshopu.Layanan.LayananDAO;
import com.p3l.kohipetshopu.PegawaiDAO;
import com.p3l.kohipetshopu.Produk.ProdukDAO;
import com.p3l.kohipetshopu.Supplier.SupplierDAO;
import com.p3l.kohipetshopu.UkuranHewan.UkuranHewanDAO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/api/pegawai/login")
    Call<PegawaiDAO> loginRequest(@Field("username") String username,
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
    Call<LayananDAO> searchNamaLayanan(@Path("nama") String nama);

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

    //Produk
    @GET("/api/produk")
    Call<List<ProdukDAO>> getAllProduk();

    @GET("/api/produk/search/{nama}")
    Call<ProdukDAO> searchNamaProduk(@Path("nama") String nama);

    @POST("/api/produk")
    @FormUrlEncoded
    Call<ProdukDAO> createProduk (@Field("nama")String nama,
                                    @Field("harga") String harga,
                                  @Field("stok")String stok,
                                  @Field("stokminimum")String stokminimum,
                                  @Field("gambar")String gambar);

    @PUT("/api/produk/{idproduk}")
    @FormUrlEncoded
    Call<ProdukDAO> editProduk(@Path("idproduk") String idproduk,
                               @Field("nama") String nama,
                               @Field("harga") String harga);

    @DELETE("/api/produk/{idproduk}")
    Call<Void> deleteProduk(@Path("idproduk") String idproduk);

    //Supplier
    @GET("/api/supplier")
    Call<List<SupplierDAO>> getAllSupplier();

    @GET("/api/supplier/search/{nama}")
    Call<SupplierDAO> searchNamaSupplier(@Path("nama") String nama);

    @POST("/api/supplier")
    @FormUrlEncoded
    Call<SupplierDAO> createSupplier (@Field("nama")String nama,
                                      @Field("alamat") String alamat,
                                      @Field("notelp")String notelp);

    @PUT("/api/supplier/{idsupplier}")
    @FormUrlEncoded
    Call<SupplierDAO> editSupplier(@Path("idsupplier") String idsupplier,
                                   @Field("nama") String nama,
                                   @Field("alamat") String alamat,
                                   @Field("notelp") String notelp);

    @DELETE("/api/supplier/{idsupplier}")
    Call<Void> deleteSupplier(@Path("idsupplier") String idsupplier);


}
