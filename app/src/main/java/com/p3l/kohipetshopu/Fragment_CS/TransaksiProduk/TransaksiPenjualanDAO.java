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
    @SerializedName("idcustomer")
    String idcustomer;
    @SerializedName("diskon")
    String diskon;
    @SerializedName("total")
    String total;
    @SerializedName("tanggaltransaksi")
    String tanggaltransaksi;

    public TransaksiPenjualanDAO(String idtransaksipenjualan, String noPR, String idpegawai, String idhewan,String idcustomer, String diskon, String total,String tanggaltransaksi) {
        this.idtransaksipenjualan = idtransaksipenjualan;
        this.noPR = noPR;
        this.idpegawai = idpegawai;
        this.idhewan = idhewan;
        this.idcustomer = idcustomer;
        this.diskon = diskon;
        this.total = total;
        this.tanggaltransaksi = tanggaltransaksi;
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

    public String getTanggalTransaksi() {
        return tanggaltransaksi;
    }

    public void setTanggalTransaksi(String tanggaltransaksi) {
        this.tanggaltransaksi = tanggaltransaksi;
    }

    public String getIdcustomer() {
        return idcustomer;
    }

    public void setIdcustomer(String idcustomer) {
        this.idcustomer = idcustomer;
    }
}
