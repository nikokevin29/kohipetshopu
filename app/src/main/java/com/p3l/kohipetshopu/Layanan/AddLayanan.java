package com.p3l.kohipetshopu.Layanan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLayanan extends AppCompatActivity {

    EditText etNamalayanan,etHarga;
    Button btn_Submit_add_layanan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_layanan);
        etNamalayanan = (EditText) findViewById(R.id.etNamaLayanan);
        etHarga = findViewById(R.id.etHargaLayanan);
        btn_Submit_add_layanan = (Button) findViewById(R.id.btn_Submit_add_layanan);
        ProgressDialog progress = new ProgressDialog(this);
        btn_Submit_add_layanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNamalayanan.getText().length() == 0){
                    Toast.makeText(AddLayanan.this, "Masih Kosong", Toast.LENGTH_SHORT).show();
                }else{

                    progress.setMessage("Memproses data . . . ");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setCancelable(false);
                    progress.show();

                    SharedPreferences mSettings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                    String aktor = mSettings.getString("id","2");

                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<LayananDAO> LayananDAO = apiService.createLayanan(etNamalayanan.getText().toString(),etHarga.getText().toString(),aktor);
                    LayananDAO.enqueue(new Callback<LayananDAO>() {
                        @Override
                        public void onResponse(Call<LayananDAO> call, Response<LayananDAO> response) {
                            Toast.makeText(AddLayanan.this, "Sukses Tambah", Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }
                        @Override
                        public void onFailure(Call<LayananDAO> call, Throwable t) {
                            Toast.makeText(AddLayanan.this, "Sukses Tambah.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(AddLayanan.this, ViewLayanan.class);
                            i.putExtra("from","layanan");
                            System.out.println(t.getMessage());
                            progress.dismiss();
                            startActivity(i);
                            finish();
                        }
                    });
                }
            }
        });
    }
}
