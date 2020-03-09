package com.p3l.kohipetshopu.JenisHewan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_jenis_hewan);
        //init();
        recyclerJenis = findViewById(R.id.recycler_view_jenis);


        ListJenis = new ArrayList<>();
        adapterJenisHewan = new AdapterJenisHewan(this,ListJenis);
        mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        recyclerJenis.setLayoutManager(mLayoutmanager);
        recyclerJenis.setItemAnimator(new DefaultItemAnimator());
        recyclerJenis.setAdapter(adapterJenisHewan);
        setRecycleJenis();
    }
    public void setRecycleJenis(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<JenisHewanDAO>> jenisDAOCall = apiService.getAllJenis();
        jenisDAOCall.enqueue(new Callback<List<JenisHewanDAO>>() {
            @Override
            public void onResponse(Call<List<JenisHewanDAO>> call,Response<List<JenisHewanDAO>> response) {
                System.out.println(response.body().get(0).getNama());
                ListJenis.addAll(response.body());
                adapterJenisHewan.notifyDataSetChanged();
                Toast.makeText(ViewJenisHewan.this, "Welcome", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<JenisHewanDAO>> call, Throwable t) {
                Toast.makeText(ViewJenisHewan.this, "Kesalahan Jaringan",Toast.LENGTH_SHORT).show();
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

//    private void setField(){
//
//
//        nama.setText(data.getString("nama"));
//        created_at.setText(data.getString("created_at"));
//        updated_at.setText(data.getString("updated_at"));
//        deleted_at.setText(data.getString("deleted_at"));
//        aksi.setText(data.getString("aksi"));
//        aktor.setText(data.getString("aktor"));
//    }
}
