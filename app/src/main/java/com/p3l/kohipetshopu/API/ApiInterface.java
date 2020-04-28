package com.p3l.kohipetshopu.API;

import com.p3l.kohipetshopu.Fragment_CS.Customer_CRUDS.CustomerDAO;
import com.p3l.kohipetshopu.Fragment_CS.Hewan_CRUDS.HewanDAO;
import com.p3l.kohipetshopu.Fragment_CS.TransaksiLayanan.DetilPelayananDAO;
import com.p3l.kohipetshopu.Fragment_CS.TransaksiLayanan.TransaksiPelayananDAO;
import com.p3l.kohipetshopu.Fragment_CS.TransaksiProduk.DetilPenjualanDAO;
import com.p3l.kohipetshopu.Fragment_CS.TransaksiProduk.TransaksiPenjualanDAO;
import com.p3l.kohipetshopu.Fragment_Owner.Pemesanan.DetilPemesananDAO;
import com.p3l.kohipetshopu.Fragment_Owner.Pemesanan.PemesananDAO;
import com.p3l.kohipetshopu.JenisHewan.JenisHewanDAO;
import com.p3l.kohipetshopu.Layanan.LayananDAO;
import com.p3l.kohipetshopu.PegawaiDAO;
import com.p3l.kohipetshopu.Produk.ProdukDAO;
import com.p3l.kohipetshopu.Supplier.SupplierDAO;
import com.p3l.kohipetshopu.UkuranHewan.UkuranHewanDAO;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    Call<JenisHewanDAO> createJenis (@Field("nama")String nama,
                                     @Field("aktor")String aktor);

    @PUT("/api/jenis_hewan/{idjenis}")
    @FormUrlEncoded
    Call<JenisHewanDAO> editJenis(@Path("idjenis") String idjenis,
                                  @Field("nama") String nama,
                                  @Field("aktor")String aktor);

    @DELETE("/api/jenis_hewan/{idjenis}")
    Call<Void> deleteJenis(@Path("idjenis") String idjenis);

    //UkuranHewan
    @GET("/api/ukuran_hewan")
    Call<List<UkuranHewanDAO>> getAllUkuran();

//    @GET("/api/ukuran_hewan/search/{nama}")
//    Call<UkuranHewanDAO> searchNama(@Path("nama") String nama);

    @POST("/api/ukuran_hewan")
    @FormUrlEncoded
    Call<UkuranHewanDAO> createUkuran (@Field("nama")String nama,
                                       @Field("aktor") String aktor);

    @PUT("/api/ukuran_hewan/{idukuran}")
    @FormUrlEncoded
    Call<UkuranHewanDAO> editUkuran(@Path("idukuran") String idukuran,
                                    @Field("nama") String nama,
                                    @Field("aktor") String aktor);

    @DELETE("/api/ukuran_hewan/{idukuran}")
    Call<Void> deleteUkuran(@Path("idukuran") String idukuran);

    //Layanan
    @GET("/api/layanan")
    Call<List<LayananDAO>> getAllLayanan();

    @GET("/api/layanan/search/{nama}")
    Call<LayananDAO> searchNamaLayanan(@Path("nama") String nama);

    @GET("/api/layanan/getbyid/{idlayanan}")
    Call<LayananDAO> getLayananbyId(@Path("idlayanan")String idlayanan);

    @POST("/api/layanan")
    @FormUrlEncoded
    Call<LayananDAO> createLayanan (@Field("nama")String nama,
                                    @Field("harga") String harga,
                                    @Field("aktor") String aktor);

    @PUT("/api/layanan/{idlayanan}")
    @FormUrlEncoded
    Call<LayananDAO> editLayanan(@Path("idlayanan") String idlayanan,
                                    @Field("nama") String nama,
                                    @Field("harga") String harga,
                                    @Field("aktor") String aktor);

    @DELETE("/api/layanan/{idlayanan}")
    Call<Void> deleteLayanan(@Path("idlayanan") String idlayanan);

    //Produk
    @GET("/api/produk")
    Call<List<ProdukDAO>> getAllProduk();
    @GET("/api/produk/getbyid/{idproduk}")
    Call<ProdukDAO> getProdukbyId(@Path("idproduk")String idproduk);
    @GET("/api/produk/search/{nama}")
    Call<ProdukDAO> searchNamaProduk(@Path("nama") String nama);

    @Headers({
            "Accept: multipart/form-data"
    })
    @Multipart
    @POST("/api/produk")
    Call<ResponseBody> createProduk (
            @Part MultipartBody.Part gambar,
            @PartMap Map<String,RequestBody> body);


    @PUT("/api/produk/{idproduk}")
    @FormUrlEncoded
    Call<ProdukDAO> editProduk(@Path("idproduk") String idproduk,
                               @Field("nama") String nama,
                               @Field("harga") String harga,
                               @Field("stok")String stok,
                               @Field("stokminimum")String stokminimum,
                               @Field("idsupplier")String idsupplier,
                               @Field("aktor") String aktor);

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
                                      @Field("notelp")String notelp,
                                      @Field("aktor") String aktor);

    @PUT("/api/supplier/{idsupplier}")
    @FormUrlEncoded
    Call<SupplierDAO> editSupplier(@Path("idsupplier") String idsupplier,
                                   @Field("nama") String nama,
                                   @Field("alamat") String alamat,
                                   @Field("notelp") String notelp,
                                   @Field("aktor") String aktor);

    @DELETE("/api/supplier/{idsupplier}")
    Call<Void> deleteSupplier(@Path("idsupplier") String idsupplier);

    //Customer
    @GET("/api/customer")
    Call<List<CustomerDAO>> getAllCustomer();
    @GET("/api/customer/search/{nama}")
    Call<CustomerDAO> searchNamaCustomer(@Path("nama") String nama);
    @GET("/api/customer/getbyid/{idcustomer}")
    Call<CustomerDAO> getcustomerbyid(@Path("idcustomer") String idcustomer);
    @POST("/api/customer")
    @FormUrlEncoded
    Call<CustomerDAO> createCustomer (@Field("nama")String nama,
                                      @Field("notelp")String notelp,
                                      @Field("alamat") String alamat,
                                      @Field("tgllahir")String tgllahir,
                                      @Field("aktor")String aktor);
    @PUT("/api/customer/{idcustomer}")
    @FormUrlEncoded
    Call<CustomerDAO> editCustomer(@Path("idcustomer") String idcustomer,
                                   @Field("nama")String nama,
                                   @Field("notelp")String notelp,
                                   @Field("alamat") String alamat,
                                   @Field("tgllahir")String tgllahir,
                                   @Field("aktor")String aktor);
    @DELETE("/api/customer/{idcustomer}")
    Call<Void> deleteCustomer(@Path("idcustomer") String idcustomer);


    //Hewan
    @GET("/api/hewan")
    Call<List<HewanDAO>> getAllHewan();
    @GET("/api/hewan/search/{nama}")
    Call<HewanDAO> searchNamaHewan(@Path("nama") String nama);
    @GET("/api/hewan/getbyid/{idhewan}")
    Call<HewanDAO> gethewanbyid(@Path("idhewan") String idhewan);
    @POST("/api/hewan")
    @FormUrlEncoded
    Call<HewanDAO> createHewan (@Field("nama")String nama,
                                @Field("tgllahir")String tgllahir,
                                @Field("idjenis")String idjenis,
                                @Field("idukuran")String idukuran,
                                @Field("idcustomer")String idcustomer,
                                @Field("aktor")String aktor);
    @PUT("/api/hewan/{idhewan}")
    @FormUrlEncoded
    Call<HewanDAO> editHewan(@Path("idhewan") String idhewan,
                             @Field("nama")String nama,
                             @Field("tgllahir")String tgllahir,
                             @Field("idjenis")String idjenis,
                             @Field("idukuran")String idukuran,
                             @Field("idcustomer")String idcustomer,
                             @Field("aktor")String aktor);
    @DELETE("/api/hewan/{idhewan}")
    Call<Void> deleteHewan(@Path("idhewan") String idhewan);

    //detil Pemesanan
    @GET("/api/detil_pemesanan")
    Call<List<DetilPemesananDAO>> getAllDetilPemesanan();
    @POST("/api/detil_pemesanan")
    @FormUrlEncoded
    Call<DetilPemesananDAO> createDetilPemesanan (@Field("idproduk")String idproduk,
                                                  @Field("jumlah")String jumlah,
                                                  @Field("satuan")String satuan,
                                                  @Field("idpemesanan")String idpemesanan);
    @PUT("/api/detil_pemesanan/{iddetilpemesanan}")
    @FormUrlEncoded
    Call<DetilPemesananDAO> editDetilPemesanan(@Part("iddetilpemesanan")String iddetilpemesanan,
                                               @Field("jumlah")String jumlah);
    @DELETE("/api/detil_pemesanan/{iddetilpemesanan}")
    Call<Void> deleteDetilPemesanan(@Path("iddetilpemesanan") String iddetilpemesanan);
    // Pemesanan Barang
    @GET("/api/pemesanan_barang")
    Call<List<PemesananDAO>> getAllPemesanan();
    @POST("/api/pemesanan_barang")
    @FormUrlEncoded
    Call<PemesananDAO> createPemesanan (@Field("idpegawai")String idpegawai,
                                        @Field("tglpesan")String tglpesan,
                                        @Field("status")String status);
    @PUT("/api/pemesanan_barang/{idpemesanan}")
    @FormUrlEncoded
    Call<List<PemesananDAO>> editPemesanan(@Path("idpemesanan") String idpemesanan,
                                           @Field("tglpesan")String tglpesan,
                                           @Field("status")String status);
    @DELETE ("/api/pemesanan_barang/{idpemesanan}")
    Call<Void> deletePemesanan(@Path("idpemesanan") String idpemesanan);

    //detil Penjualan Produk
    @GET("/api/detil_penjualan")
    Call<List<DetilPenjualanDAO>> getAllDetilPenjualan();
    @GET("/api/detil_penjualan/getlastid/")
    Call<TransaksiPenjualanDAO> getLastidPenjualan();
    @POST("/api/detil_penjualan")
    @FormUrlEncoded
    Call<DetilPenjualanDAO> createDetilPenjualan (@Field("idproduk")String idproduk,
                                                  @Field("jumlah")String jumlah,
                                                  @Field("subtotal")String subtotal,
                                                  @Field("idtransaksipenjualan")String idtransaksipenjualan);
    @PUT("/api/detil_penjualan/{iddetilpenjualan}")
    @FormUrlEncoded
    Call<DetilPenjualanDAO> editDetilPenjualan(@Part("iddetilpenjualan")String iddetilpenjualan,
                                               @Field("jumlah")String jumlah);
    @DELETE("/api/detil_penjualan/{iddetilpenjualan}")
    Call<Void> deleteDetilPenjualan(@Path("iddetilpenjualan") String iddetilpenjualan);

    // Transaksi Penjualan Produk

//    @GET("/api/transaksi_penjualan")//your api link
//    Call<Object> getAllPenjualanObject();

    @GET("/api/transaksi_penjualan")
    Call<List<TransaksiPenjualanDAO>> getAllPenjualan();
    @POST("/api/transaksi_penjualan")
    @FormUrlEncoded
    Call<TransaksiPenjualanDAO> createPenjualan (@Field("idpegawai")String idpegawai,
                                                 @Field("idhewan")String idhewan,
                                                 @Field("idcustomer")String idcustomer,
                                                 @Field("diskon")String diskon,
                                                 @Field("total")String total);
    @PUT("/api/transaksi_penjualan/{idtransaksipenjualan}")
    @FormUrlEncoded
    Call<List<TransaksiPenjualanDAO>> editPenjualan(@Path("idtransaksipenjualan") String idtransaksipenjualan,
                                                    @Field("tglpesan")String tglpesan,
                                                    @Field("status")String status);
    @DELETE ("/api/transaksi_penjualan/{idtransaksipenjualan}")
    Call<Void> deletePenjualan(@Path("idtransaksipenjualan") String idtransaksipenjualan);

    //Detil Pelayanan
    @GET("/api/detil_pelayanan/getlastid/")
    Call<TransaksiPelayananDAO> getLastidPelayanan();
    @POST("/api/detil_pelayanan")
    @FormUrlEncoded
    Call<DetilPelayananDAO> createDetilPelayanan (@Field("idlayanan")String idproduk,
                                                  @Field("jumlah")String jumlah,
                                                  @Field("subtotal")String subtotal,
                                                  @Field("idtransaksipelayanan")String idtransaksipelayanan);

    // Transaksi Pelayanan Hewan dan Sejenisnya
    @GET("/api/transaksi_pelayanan")
    Call<List<TransaksiPelayananDAO>> getAllPelayanan();

    @POST("/api/transaksi_pelayanan")
    @FormUrlEncoded
    Call<TransaksiPelayananDAO> createPelayanan (@Field("idpegawai")String idpegawai,
                                                 @Field("idhewan")String idhewan,
                                                 @Field("idcustomer")String idcustomer,
                                                 @Field("status")String status,
                                                 @Field("diskon")String diskon,
                                                 @Field("total")String total);
    @DELETE ("/api/transaksi_pelayanan/{idtransaksipelayanan}")
    Call<Void> deletePelayanan(@Path("idtransaksipelayanan") String idtransaksipelayanan);
}
