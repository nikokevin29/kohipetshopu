package com.p3l.kohipetshopu.Fragment_CS.TransaksiLayanan.TampilAll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.Fragment_CS.TransaksiLayanan.TransaksiPelayananDAO;
import com.p3l.kohipetshopu.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTampilTransaksiLayanan extends AppCompatActivity {

    List<TransaksiPelayananDAO> List;
    adapterTampilAllTransaksiLayanan adapter;
    RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_tampil_transaksi_layanan);
        recycle = findViewById(R.id.recycle_view_pelayanan_all);
        List = new ArrayList<>();
        adapter = new adapterTampilAllTransaksiLayanan(this,List);
        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        recycle.setLayoutManager(mLayoutmanager);
        recycle.setItemAnimator(new DefaultItemAnimator());
        recycle.setAdapter(adapter);
        load();
    }
    public void load(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Fetching data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

        Call<List<TransaksiPelayananDAO>> callDAO = apiService.getAllPelayanan();
        callDAO.enqueue(new Callback<List<TransaksiPelayananDAO>>() {
            @Override
            public void onResponse(Call<List<TransaksiPelayananDAO>> call, Response<List<TransaksiPelayananDAO>> response) {
                List.addAll(response.body());
                adapter.notifyDataSetChanged();
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<List<TransaksiPelayananDAO>> call, Throwable t) {
                Toast.makeText(ViewTampilTransaksiLayanan.this, "Internet Anda Ampas", Toast.LENGTH_SHORT).show();
                System.out.println("SAMSUNG  ::::     "+t.getMessage());
                progress.dismiss();
            }
        });
    }
}
