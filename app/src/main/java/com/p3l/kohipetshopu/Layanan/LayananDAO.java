package com.p3l.kohipetshopu.Layanan;

import com.google.gson.annotations.SerializedName;

public class LayananDAO {
    @SerializedName("idlayanan")
    String idlayanan;
    @SerializedName("nama")
    String nama;
    @SerializedName("harga")
    String harga;
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

    public LayananDAO(){}

    public LayananDAO(String idlayanan, String nama,String harga, String created_at, String updated_at, String deleted_at,  String aksi,String aktor){
        this.idlayanan = idlayanan;
        this.nama = nama;
        this.harga = harga;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
        this.aksi = aksi;
        this.aktor = aktor;
    }
    public String getIdlayanan() {
        return idlayanan;
    }

    public void setIdlayanan(String idlayanan) {
        this.idlayanan = idlayanan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
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
