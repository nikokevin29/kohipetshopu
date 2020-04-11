package com.p3l.kohipetshopu.JenisHewan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jenis);
        etNamajenis = (EditText) findViewById(R.id.etNamaJenis);
        btn_Submit_add_jenis = (Button) findViewById(R.id.btn_Submit_add_jenis);
        ProgressDialog progress = new ProgressDialog(this);
        btn_Submit_add_jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNamajenis.getText().length() == 0){
                    Toast.makeText(AddJenis.this, "Masih Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<List<JenisHewanDAO>> jenisDAOCall = apiService.getAllJenis();

                    progress.setMessage("Memproses data . . . ");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setCancelable(false);
                    progress.show();

                    jenisDAOCall.enqueue(new Callback<List<JenisHewanDAO>>() {
                        @Override
                        public void onResponse(Call<List<JenisHewanDAO>> call, Response<List<JenisHewanDAO>> response) {

                            SharedPreferences mSettings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                            String aktor = mSettings.getString("id","2");

                            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                            Call<JenisHewanDAO> JenisHewanDAO = apiService.createJenis(etNamajenis.getText().toString(),aktor);
                            JenisHewanDAO.enqueue(new Callback<JenisHewanDAO>() {
                                @Override
                                public void onResponse(Call<JenisHewanDAO> call, Response<JenisHewanDAO> response) {
                                    Toast.makeText(AddJenis.this, "Sukses Tambah", Toast.LENGTH_SHORT).show();
                                    progress.dismiss();
                                }
                                @Override
                                public void onFailure(Call<JenisHewanDAO> call, Throwable t) {
                                    Toast.makeText(AddJenis.this, "Sukses Tambah.", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(AddJenis.this, ViewJenisHewan.class);
                                    i.putExtra("from","jenis");
                                    System.out.println(t.getMessage());
                                    progress.dismiss();
                                    startActivity(i);
                                    finish();
                                }
                            });
                        }
                        @Override
                        public void onFailure(Call<List<JenisHewanDAO>> call, Throwable t) {
                            System.out.println("gagal");
                        }
                    });
                }
            }
        });
    }


}
