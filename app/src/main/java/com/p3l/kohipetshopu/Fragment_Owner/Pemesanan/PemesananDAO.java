package com.p3l.kohipetshopu.Fragment_Owner.Pemesanan;

import com.google.gson.annotations.SerializedName;

public class PemesananDAO {


    @SerializedName("idpemesanan")
    String idpemesanan;
    @SerializedName("noPO")
    String noPO;
    @SerializedName("idpegawai")
    String idpegawai;
    @SerializedName("tglpesan")
    String tglpesan;
    @SerializedName("notelp")
    String notelp;
    @SerializedName("tglcetak")
    String tglcetak;
    @SerializedName("status")
    String status;

    public PemesananDAO(){}

    public PemesananDAO(String idpemesanan, String noPO, String idpegawai, String tglpesan, String notelp, String tglcetak, String status) {
        this.idpemesanan = idpemesanan;
        this.noPO = noPO;
        this.idpegawai = idpegawai;
        this.tglpesan = tglpesan;
        this.notelp = notelp;
        this.tglcetak = tglcetak;
        this.status = status;
    }

    public String getIdpemesanan() {
        return idpemesanan;
    }

    public void setIdpemesanan(String idpemesanan) {
        this.idpemesanan = idpemesanan;
    }

    public String getNoPO() {
        return noPO;
    }

    public void setNoPO(String noPO) {
        this.noPO = noPO;
    }

    public String getIdpegawai() {
        return idpegawai;
    }

    public void setIdpegawai(String idpegawai) {
        this.idpegawai = idpegawai;
    }

    public String getTglpesan() {
        return tglpesan;
    }

    public void setTglpesan(String tglpesan) {
        this.tglpesan = tglpesan;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getTglcetak() {
        return tglcetak;
    }

    public void setTglcetak(String tglcetak) {
        this.tglcetak = tglcetak;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
