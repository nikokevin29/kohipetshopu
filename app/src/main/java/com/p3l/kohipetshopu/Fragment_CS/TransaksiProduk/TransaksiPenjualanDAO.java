package com.p3l.kohipetshopu.Fragment_CS.TransaksiProduk;

import com.google.gson.annotations.SerializedName;

public class TransaksiPenjualanDAO {
    @SerializedName("idtransaksipenjualan")
    String idtransaksipenjualan;
    @SerializedName("noPR")
    String noPR;
    @SerializedName("idpegawai")
    String idpegawai;
    @SerializedName("idhewan")
    String idhewan;
    @SerializedName("diskon")
    String diskon;
    @SerializedName("total")
    String total;

    public TransaksiPenjualanDAO(String idtransaksipenjualan, String noPR, String idpegawai, String idhewan, String diskon, String total) {
        this.idtransaksipenjualan = idtransaksipenjualan;
        this.noPR = noPR;
        this.idpegawai = idpegawai;
        this.idhewan = idhewan;
        this.diskon = diskon;
        this.total = total;
    }

    public String getIdtransaksipenjualan() {
        return idtransaksipenjualan;
    }

    public void setIdtransaksipenjualan(String idtransaksipenjualan) {
        this.idtransaksipenjualan = idtransaksipenjualan;
    }

    public String getNoPR() {
        return noPR;
    }

    public void setNoPR(String noPR) {
        this.noPR = noPR;
    }

    public String getIdpegawai() {
        return idpegawai;
    }

    public void setIdpegawai(String idpegawai) {
        this.idpegawai = idpegawai;
    }

    public String getIdhewan() {
        return idhewan;
    }

    public void setIdhewan(String idhewan) {
        this.idhewan = idhewan;
    }

    public String getDiskon() {
        return diskon;
    }

    public void setDiskon(String diskon) {
        this.diskon = diskon;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
