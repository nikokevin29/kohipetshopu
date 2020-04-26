package com.p3l.kohipetshopu.Fragment_CS.Hewan_CRUDS;

import com.google.gson.annotations.SerializedName;

public class HewanDAO {

    @SerializedName("idhewan")
    String idhewan;
    @SerializedName("nama")
    String nama;
    @SerializedName("tgllahir")
    String tgllahir;
    @SerializedName("idjenis")
    String idjenis;
    @SerializedName("idukuran")
    String idukuran;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("updated_at")
    String updated_at;
    @SerializedName("deleted_at")
    String deleted_at;
    @SerializedName("aktor")
    String aktor;
    @SerializedName("aksi")
    String aksi;
    @SerializedName("idcustomer")
    String idcustomer;

    public HewanDAO(String idhewan, String nama, String tgllahir, String idjenis, String idukuran, String created_at, String updated_at, String deleted_at, String aktor, String aksi, String idcustomer) {
        this.idhewan = idhewan;
        this.nama = nama;
        this.tgllahir = tgllahir;
        this.idjenis = idjenis;
        this.idukuran = idukuran;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
        this.aktor = aktor;
        this.aksi = aksi;
        this.idcustomer = idcustomer;
    }

    public String getIdhewan() {
        return idhewan;
    }

    public void setIdhewan(String idhewan) {
        this.idhewan = idhewan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTgllahir() {
        return tgllahir;
    }

    public void setTgllahir(String tgllahir) {
        this.tgllahir = tgllahir;
    }

    public String getIdjenis() {
        return idjenis;
    }

    public void setIdjenis(String idjenis) {
        this.idjenis = idjenis;
    }

    public String getIdukuran() {
        return idukuran;
    }

    public void setIdukuran(String idukuran) {
        this.idukuran = idukuran;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getAktor() {
        return aktor;
    }

    public void setAktor(String aktor) {
        this.aktor = aktor;
    }

    public String getAksi() {
        return aksi;
    }

    public void setAksi(String aksi) {
        this.aksi = aksi;
    }

    public String getIdcustomer() {
        return idcustomer;
    }

    public void setIdcustomer(String idcustomer) {
        this.idcustomer = idcustomer;
    }
}
