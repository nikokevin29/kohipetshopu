package com.p3l.kohipetshopu.Produk;

import com.google.gson.annotations.SerializedName;

public class ProdukDAO {

    @SerializedName("idproduk")
    String idproduk;
    @SerializedName("nama")
    String nama;
    @SerializedName("harga")
    String harga;
    @SerializedName("stok")
    String stok;
    @SerializedName("stokminimum")
    String stokminimum;
    @SerializedName("gambar")
    String gambar;
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
    @SerializedName("idsupplier")
    String idsupplier;

    public ProdukDAO(){}

    public ProdukDAO(String idproduk, String nama, String harga, String stok, String stokminimum, String gambar, String created_at, String updated_at, String deleted_at, String aktor, String aksi, String idsupplier) {
        this.idproduk = idproduk;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
        this.stokminimum = stokminimum;
        this.gambar = gambar;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
        this.aktor = aktor;
        this.aksi = aksi;
        this.idsupplier = idsupplier;
    }

    public String getIdproduk() {
        return idproduk;
    }

    public void setIdproduk(String idproduk) {
        this.idproduk = idproduk;
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

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getStokminimum() {
        return stokminimum;
    }

    public void setStokminimum(String stokminimum) {
        this.stokminimum = stokminimum;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
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

    public String getIdsupplier() {
        return idsupplier;
    }

    public void setIdsupplier(String idsupplier) {
        this.idsupplier = idsupplier;
    }
}
