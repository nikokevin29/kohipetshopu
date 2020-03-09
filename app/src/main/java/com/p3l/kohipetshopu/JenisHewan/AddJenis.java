package com.p3l.kohipetshopu.JenisHewan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.MainView;
import com.p3l.kohipetshopu.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddJenis extends AppCompatActivity {
    EditText etNamajenis;
    Button btn_Submit_add_jenis;
    String nama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jenis);
        etNamajenis=(EditText) findViewById(R.id.etNamaJenis);
        btn_Submit_add_jenis= (Button) findViewById(R.id.btn_Submit_add_jenis);


        btn_Submit_add_jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<List<JenisHewanDAO>> jenisDAOCall = apiService.getAllJenis();
                jenisDAOCall.enqueue(new Callback<List<JenisHewanDAO>>() {
                    @Override
                    public void onResponse(Call<List<JenisHewanDAO>> call, Response<List<JenisHewanDAO>> response) {
                        for (int i=0; i<response.body().size(); i++){
                            String idjenis = response.body().get(i).getIdjenis();
                            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                            Call<JenisHewanDAO> JenisHewanDAO = apiService.createJenis(etNamajenis.getText().toString());
                            JenisHewanDAO.enqueue(new Callback<JenisHewanDAO>() {
                                @Override
                                public void onResponse(Call<JenisHewanDAO> call, Response<JenisHewanDAO> response) {
                                    Toast.makeText(AddJenis.this, "Sukses Tambah", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(AddJenis.this, ViewJenisHewan.class);
                                    startActivity(i);
                                }
                                @Override
                                public void onFailure(Call<JenisHewanDAO> call, Throwable t) {
                                    Toast.makeText(AddJenis.this, "Failed disini", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    }
                    @Override
                    public void onFailure(Call<List<JenisHewanDAO>> call, Throwable t) {
                        System.out.println("gagal");
                    }
                });

            }
        });
    }


}
