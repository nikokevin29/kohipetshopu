package com.p3l.kohipetshopu.Fragment_Owner.Pemesanan;

import com.google.gson.annotations.SerializedName;

public class DetilPemesananDAO {

    @SerializedName("iddetilpemesanan")
    String iddetilpemesanan;
    @SerializedName("idproduk")
    String idproduk;
    @SerializedName("jumlah")
    String jumlah;
    @SerializedName("satuan")
    String satuan;
    @SerializedName("idpemesanan")
    String idpemesanan;

    //public DetilPemesananDAO(){}

    public DetilPemesananDAO(String iddetilpemesanan, String idproduk, String jumlah, String satuan, String idpemesanan) {
        this.iddetilpemesanan = iddetilpemesanan;
        this.idproduk = idproduk;
        this.jumlah = jumlah;
        this.satuan = satuan;
        this.idpemesanan = idpemesanan;
    }

    public String getIddetilpemesanan() {
        return iddetilpemesanan;
    }

    public void setIddetilpemesanan(String iddetilpemesanan) {
        this.iddetilpemesanan = iddetilpemesanan;
    }

    public String getIdproduk() {
        return idproduk;
    }

    public void setIdproduk(String idproduk) {
        this.idproduk = idproduk;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getIdpemesanan() {
        return idpemesanan;
    }

    public void setIdpemesanan(String idpemesanan) {
        this.idpemesanan = idpemesanan;
    }
}
