package com.p3l.kohipetshopu.Fragment_CS.TransaksiLayanan;

import com.google.gson.annotations.SerializedName;

public class TransaksiPelayananDAO {
    @SerializedName("idtransaksipelayanan")
    String idtransaksipelayanan;
    @SerializedName("noLY")
    String noLY;
    @SerializedName("idpegawai")
    String idpegawai;
    @SerializedName("idhewan")
    String idhewan;
    @SerializedName("idcustomer")
    String idcustomer;
    @SerializedName("status")
    String status;
    @SerializedName("diskon")
    String diskon;
    @SerializedName("total")
    String total;
    @SerializedName("tanggaltransaksi")
    String tanggaltransaksi;

    public TransaksiPelayananDAO(String idtransaksipelayanan, String noLY, String idpegawai, String idhewan,String idcustomer, String status, String diskon, String total,String tanggaltransaksi) {
        this.idtransaksipelayanan = idtransaksipelayanan;
        this.noLY = noLY;
        this.idpegawai = idpegawai;
        this.idhewan = idhewan;
        this.idcustomer = idcustomer;
        this.status = status;
        this.diskon = diskon;
        this.total = total;
        this.tanggaltransaksi = tanggaltransaksi;
    }

    public String getTanggaltransaksi() {
        return tanggaltransaksi;
    }

    public void setTanggaltransaksi(String tanggaltransaksi) {
        this.tanggaltransaksi = tanggaltransaksi;
    }

    public String getIdcustomer() {
        return idcustomer;
    }

    public void setIdcustomer(String idcustomer) {
        this.idcustomer = idcustomer;
    }

    public String getIdtransaksipelayanan() {
        return idtransaksipelayanan;
    }

    public void setIdtransaksipelayanan(String idtransaksipelayanan) {
        this.idtransaksipelayanan = idtransaksipelayanan;
    }

    public String getNoLY() {
        return noLY;
    }

    public void setNoLY(String noLY) {
        this.noLY = noLY;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
