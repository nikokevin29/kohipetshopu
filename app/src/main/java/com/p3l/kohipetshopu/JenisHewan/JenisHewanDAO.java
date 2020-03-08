package com.p3l.kohipetshopu.JenisHewan;

public class JenisHewanDAO {
    String nama,created_at,updated_at,deleted_at,aksi;
    int idjenis,aktor;

    public JenisHewanDAO(){}

    public JenisHewanDAO(int idjenis,String nama,String created_at,String updated_at, String deleted_at,int aktor,String aksi){
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

    public int getIdjenis() {
        return idjenis;
    }

    public void setIdjenis(int idjenis) {
        this.idjenis = idjenis;
    }

    public int getAktor() {
        return aktor;
    }

    public void setAktor(int aktor) {
        this.aktor = aktor;
    }
}
