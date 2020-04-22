package com.p3l.kohipetshopu.Fragment_CS.TransaksiLayanan;

import com.google.gson.annotations.SerializedName;

public class DetilPelayananDAO {
    @SerializedName("iddetilpelayanan")
    String iddetilpelayanan;
    @SerializedName("idlayanan")
    String idlayanan;
    @SerializedName("jumlah")
    String jumlah;
    @SerializedName("subtotal")
    String subtotal;
    @SerializedName("idtransaksipelayanan")
    String idtransaksipelayanan;

    public DetilPelayananDAO(String iddetilpelayanan, String idlayanan, String jumlah, String subtotal,String idtransaksipelayanan) {
        this.iddetilpelayanan = iddetilpelayanan;
        this.idlayanan = idlayanan;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
        this.idtransaksipelayanan = idtransaksipelayanan;
    }

    public String getIddetilpelayanan() {
        return iddetilpelayanan;
    }

    public void setIddetilpelayanan(String iddetilpelayanan) {
        this.iddetilpelayanan = iddetilpelayanan;
    }

    public String getIdlayanan() {
        return idlayanan;
    }

    public void setIdlayanan(String idlayanan) {
        this.idlayanan = idlayanan;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getIdtransaksipelayanan() {
        return idtransaksipelayanan;
    }

    public void setIdtransaksipelayanan(String idtransaksipelayanan) {
        this.idtransaksipelayanan = idtransaksipelayanan;
    }
}
