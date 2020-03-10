package com.p3l.kohipetshopu.JenisHewan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.R;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewJenisHewan extends AppCompatActivity {
    Bundle data;
    private List<JenisHewanDAO> ListJenis;
    AdapterJenisHewan adapterJenisHewan;
    private RecyclerView recyclerJenis;
    private RecyclerView.LayoutManager mLayoutmanager;
    private FloatingActionButton add_jenis;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_jenis_hewan);
        //init();
        recyclerJenis = findViewById(R.id.recycler_view_jenis);
        add_jenis = findViewById(R.id.add_jenis);
        search = (EditText) findViewById(R.id.search_jenis);

        ListJenis = new ArrayList<>();
        adapterJenisHewan = new AdapterJenisHewan(this, ListJenis);
        mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        recyclerJenis.setLayoutManager(mLayoutmanager);
        recyclerJenis.setItemAnimator(new DefaultItemAnimator());
        recyclerJenis.setAdapter(adapterJenisHewan);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loadData();
        add_jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(ViewJenisHewan.this,AddJenis.class);
                i.putExtra("from","AddJenis");
                startActivity(i);
            }
        });


    }
    public void loadData(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<JenisHewanDAO>> jenisDAOCall = apiService.getAllJenis();

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Fetching data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

        jenisDAOCall.enqueue(new Callback<List<JenisHewanDAO>>() {
            @Override
            public void onResponse(Call<List<JenisHewanDAO>> call,Response<List<JenisHewanDAO>> response) {
                //System.out.println(response.body().get(0).getNama());
                ListJenis.addAll(response.body());
                adapterJenisHewan.notifyDataSetChanged();
                progress.dismiss();
                Toast.makeText(ViewJenisHewan.this, "Tekan yang Lama untuk Melakukan Aksi", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<JenisHewanDAO>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ViewJenisHewan.this, "Internet Anda Ampas",Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }



}
