package com.p3l.kohipetshopu.JenisHewan;

import com.google.gson.annotations.SerializedName;

public class JenisHewanDAO{
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
    @SerializedName("idjenis")
    String idjenis;
    @SerializedName("aktor")
    String aktor;

    public JenisHewanDAO(){}

    public JenisHewanDAO(String idjenis,String nama,String created_at,String updated_at, String deleted_at,String aktor,String aksi){
        this.idjenis = idjenis;
        this.nama = nama;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
        this.aktor = aktor;
        this.aksi = aksi;
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

    public String getIdjenis() {
        return idjenis;
    }

    public void setIdjenis(String idjenis) {
        this.idjenis = idjenis;
    }

    public String getAktor() {
        return aktor;
    }

    public void setAktor(String aktor) {
        this.aktor = aktor;
    }
}
