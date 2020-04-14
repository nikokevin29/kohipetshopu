package com.p3l.kohipetshopu;

import com.google.gson.annotations.SerializedName;

public class PegawaiDAO {
    @SerializedName("idpegawai")
    String idpegawai;
    @SerializedName("nama")
    String nama;
    @SerializedName("alamat")
    String alamat;
    @SerializedName("tgllahir")
    String tgllahir;
    @SerializedName("notelp")
    String noTelp;
    @SerializedName("role")
    String role;
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;

    public PegawaiDAO(){}

    public PegawaiDAO(String idpegawai, String nama, String alamat, String tgllahir, String noTelp, String role, String username, String password) {
        this.idpegawai = idpegawai;
        this.nama = nama;
        this.alamat = alamat;
        this.tgllahir = tgllahir;
        this.noTelp = noTelp;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public String getIdpegawai() {
        return idpegawai;
    }

    public void setIdpegawai(String idpegawai) {
        this.idpegawai = idpegawai;
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

    public String getTgllahir() {
        return tgllahir;
    }

    public void setTgllahir(String tgllahir) {
        this.tgllahir = tgllahir;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
