package com.p3l.kohipetshopu.Fragment_CS.TransaksiProduk;

import com.google.gson.annotations.SerializedName;

public class DetilPenjualanDAO {

    @SerializedName("iddetilpenjualan")
    String iddetilpenjualan;
    @SerializedName("idproduk")
    String idproduk;
    @SerializedName("jumlah")
    String jumlah;
    @SerializedName("subtotal")
    String subtotal;
    @SerializedName("idtransaksipenjualan")
    String idtransaksipenjualan;


    public DetilPenjualanDAO(){}
    public DetilPenjualanDAO(String iddetilpenjualan, String idproduk, String jumlah, String subtotal,String idtransaksipenjualan) {
        this.iddetilpenjualan = iddetilpenjualan;
        this.idproduk = idproduk;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
        this.idtransaksipenjualan = idtransaksipenjualan;
    }

    public String getIddetilpenjualan() {
        return iddetilpenjualan;
    }

    public void setIddetilpenjualan(String iddetilpenjualan) {
        this.iddetilpenjualan = iddetilpenjualan;
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

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getIdtransaksipenjualan() {
        return idtransaksipenjualan;
    }

    public void setIdtransaksipenjualan(String idtransaksipenjualan) {
        this.idtransaksipenjualan = idtransaksipenjualan;
    }
}
