package com.p3l.kohipetshopu.UkuranHewan;

import com.google.gson.annotations.SerializedName;

public class UkuranHewanDAO {

    @SerializedName("idukuran")
    String idukuran;
    @SerializedName("nama")
    String nama;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("updated_at")
    String updated_at;
    @SerializedName("deleted_at")
    String deleted_at;
    @SerializedName("aksi")
    String aksi;
    @SerializedName("aktor")
    String aktor;

    public UkuranHewanDAO(){}

    public UkuranHewanDAO(String idukuran, String nama, String created_at, String updated_at, String deleted_at, String aktor, String aksi){
        this.idukuran = idukuran;
        this.nama = nama;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
        this.aktor = aktor;
        this.aksi = aksi;
    }

    public String getIdukuran() {
        return idukuran;
    }

    public void setIdukuran(String idukuran) {
        this.idukuran = idukuran;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public String getAksi() {
        return aksi;
    }

    public void setAksi(String aksi) {
        this.aksi = aksi;
    }

    public String getAktor() {
        return aktor;
    }

    public void setAktor(String aktor) {
        this.aktor = aktor;
    }

}
