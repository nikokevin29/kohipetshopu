package com.p3l.kohipetshopu.UkuranHewan;

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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.JenisHewan.AddJenis;
import com.p3l.kohipetshopu.JenisHewan.JenisHewanDAO;
import com.p3l.kohipetshopu.UkuranHewan.AdapterUkuranHewan;
import com.p3l.kohipetshopu.UkuranHewan.AddUkuran;
import com.p3l.kohipetshopu.UkuranHewan.UkuranHewanDAO;
import com.p3l.kohipetshopu.UkuranHewan.ViewUkuranHewan;
import com.p3l.kohipetshopu.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewUkuranHewan extends AppCompatActivity {

    private List<UkuranHewanDAO> ListUkuran;
    AdapterUkuranHewan adapterUkuranHewan;
    private RecyclerView recyclerUkuran;
    private RecyclerView.LayoutManager mLayoutmanager;
    private FloatingActionButton add_ukuran;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_ukuran_hewan);

        recyclerUkuran = findViewById(R.id.recycler_view_ukuran);
        add_ukuran = findViewById(R.id.add_ukuran);
        search = (EditText) findViewById(R.id.search_ukuran);

        ListUkuran = new ArrayList<>();
        adapterUkuranHewan = new AdapterUkuranHewan(this, ListUkuran);
        mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        recyclerUkuran.setLayoutManager(mLayoutmanager);
        recyclerUkuran.setItemAnimator(new DefaultItemAnimator());
        recyclerUkuran.setAdapter(adapterUkuranHewan);

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
        add_ukuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(ViewUkuranHewan.this, AddUkuran.class);
                i.putExtra("from","AddUkuran");
                startActivity(i);
            }
        });
    }//End Of On Create


    public void loadData(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<UkuranHewanDAO>> ukuranDAOCall = apiService.getAllUkuran();

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Fetching data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

        ukuranDAOCall.enqueue(new Callback<List<UkuranHewanDAO>>() {
            @Override
            public void onResponse(Call<List<UkuranHewanDAO>> call, Response<List<UkuranHewanDAO>> response) {
                //System.out.println(response.body().get(0).getNama());
                ListUkuran.addAll(response.body());
                adapterUkuranHewan.notifyDataSetChanged();
                progress.dismiss();
                Toast.makeText(ViewUkuranHewan.this, "Tekan yang Lama untuk Melakukan Aksi", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<UkuranHewanDAO>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ViewUkuranHewan.this, "Internet Anda Ampas",Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }

}
