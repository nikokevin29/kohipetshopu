package com.p3l.kohipetshopu.Supplier;

import com.google.gson.annotations.SerializedName;

public class SupplierDAO {

    @SerializedName("idsupplier")
    String idsupplier;
    @SerializedName("nama")
    String nama;
    @SerializedName("alamat")
    String alamat;
    @SerializedName("notelp")
    String notelp;
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

    public SupplierDAO(){}

    public SupplierDAO(String idsupplier, String nama, String alamat, String notelp, String created_at, String updated_at, String deleted_at, String aktor, String aksi) {
        this.idsupplier = idsupplier;
        this.nama = nama;
        this.alamat = alamat;
        this.notelp = notelp;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
        this.aktor = aktor;
        this.aksi = aksi;
    }

    public String getIdsupplier() {
        return idsupplier;
    }

    public void setIdsupplier(String idsupplier) {
        this.idsupplier = idsupplier;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
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
}
