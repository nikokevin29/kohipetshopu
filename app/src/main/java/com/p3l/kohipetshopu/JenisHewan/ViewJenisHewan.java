package com.p3l.kohipetshopu.JenisHewan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class ViewJenisHewan extends AppCompatActivity {
    private TextView idjenis, nama,created_at,deleted_at,updated_at,aksi,aktor;
    Bundle data;
    private List<JenisHewanDAO> ListJenis;
    AdapterJenisHewan adapterJenisHewan;
    private RecyclerView recyclerJenis;
    private RecyclerView.LayoutManager mLayoutmanager;
    private FloatingActionButton add_jenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_jenis_hewan);
        //init();
        recyclerJenis = findViewById(R.id.recycler_view_jenis);
        add_jenis = findViewById(R.id.add_jenis);

        ListJenis = new ArrayList<>();
        adapterJenisHewan = new AdapterJenisHewan(this,ListJenis);
        mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        recyclerJenis.setLayoutManager(mLayoutmanager);
        recyclerJenis.setItemAnimator(new DefaultItemAnimator());
        recyclerJenis.setAdapter(adapterJenisHewan);
        setRecycleJenis();

        add_jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(ViewJenisHewan.this,AddJenis.class);
                i.putExtra("from","AddJenis");
                startActivity(i);
            }
        });
    }
    public void setRecycleJenis(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<JenisHewanDAO>> jenisDAOCall = apiService.getAllJenis();
        jenisDAOCall.enqueue(new Callback<List<JenisHewanDAO>>() {
            @Override
            public void onResponse(Call<List<JenisHewanDAO>> call,Response<List<JenisHewanDAO>> response) {
                //System.out.println(response.body().get(0).getNama());
                ListJenis.addAll(response.body());
                adapterJenisHewan.notifyDataSetChanged();
                Toast.makeText(ViewJenisHewan.this, "Tekan yang Lama untuk Melakukan Aksi", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<JenisHewanDAO>> call, Throwable t) {
                Toast.makeText(ViewJenisHewan.this, "Internet Anda Ampas",Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });

    }
    private void init(){
        nama = (TextView) findViewById(R.id.tvNamaJenisHewan);
        created_at = (TextView) findViewById(R.id.tvCreated_at);
        updated_at = (TextView) findViewById(R.id.tvUpdated_at);
        deleted_at = (TextView) findViewById(R.id.tvDeleted_at);
        aksi = (TextView) findViewById(R.id.tvAksi);
        aktor = (TextView) findViewById(R.id.tvAktor);
    }


}
