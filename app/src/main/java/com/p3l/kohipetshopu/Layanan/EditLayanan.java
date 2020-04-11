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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditLayanan extends AppCompatActivity {

    EditText etNama,etHarga;
    Button btn_Submit_update_layanan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_layanan);
        etNama = findViewById(R.id.etNamaLayanan_edit);
        etHarga = findViewById(R.id.etHargaLayanan_edit);
        btn_Submit_update_layanan = findViewById(R.id.btn_Submit_edit_layanan);
        setField();

        ProgressDialog progress = new ProgressDialog(this);

        btn_Submit_update_layanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNama.getText().length() == 0  || etHarga.getText().length() == 0){
                    Toast.makeText(EditLayanan.this, "Masih Kosong", Toast.LENGTH_SHORT).show();
                }else{

                    SharedPreferences mSettings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                    String aktor = mSettings.getString("id","2");

                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<LayananDAO> layananDAOCall = apiService.editLayanan(getIntent().getStringExtra("idlayanan"),etNama.getText().toString(),etHarga.getText().toString(),aktor);

                   // System.out.println(getIntent().getStringExtra("idlayanan")+" "+etNama.getText().toString());

                    progress.setMessage("Memproses data . . . ");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setCancelable(false);
                    progress.show();

                    layananDAOCall.enqueue(new Callback<LayananDAO>() {
                        @Override
                        public void onResponse(Call<LayananDAO> call, Response<LayananDAO> response) {
                            Toast.makeText(EditLayanan.this, "Edit Success", Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                            startIntent();
                            finish();
                        }
                        @Override
                        public void onFailure(Call<LayananDAO> call, Throwable t) {
                            Toast.makeText(EditLayanan.this, "Edit Success.", Toast.LENGTH_SHORT).show();
                            System.out.println(t.getMessage());
                            progress.dismiss();
                            startIntent();
                        }
                    });
                }

            }
        });
    }
    public void setField(){
        etNama.setText(getIntent().getStringExtra("nama"));
        etHarga.setText(getIntent().getStringExtra("harga"));
    }
    public void startIntent(){
        Intent acc = new Intent(EditLayanan.this, ViewLayanan.class);
        acc.putExtra("from", "layanan");
        startActivity(acc);
        finish();
    }
}
