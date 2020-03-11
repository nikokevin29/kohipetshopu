package com.p3l.kohipetshopu.UkuranHewan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.*;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.R;

import java.util.List;

public class AddUkuran extends AppCompatActivity {
    EditText etNamaukuran;
    Button btn_Submit_add_ukuran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ukuran);
        etNamaukuran = (EditText) findViewById(R.id.etNamaUkuran);
        btn_Submit_add_ukuran = (Button) findViewById(R.id.btn_Submit_add_ukuran);
        ProgressDialog progress = new ProgressDialog(this);
        btn_Submit_add_ukuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNamaukuran.getText().length() == 0){
                    Toast.makeText(AddUkuran.this, "Masih Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<List<UkuranHewanDAO>> ukuranDAOCall = apiService.getAllUkuran();

                    progress.setMessage("Memproses data . . . ");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setCancelable(false);
                    progress.show();

                    ukuranDAOCall.enqueue(new Callback<List<UkuranHewanDAO>>() {
                        @Override
                        public void onResponse(Call<List<UkuranHewanDAO>> call, Response<List<UkuranHewanDAO>> response) {
                            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                            Call<UkuranHewanDAO> UkuranHewanDAO = apiService.createUkuran(etNamaukuran.getText().toString());
                            UkuranHewanDAO.enqueue(new Callback<UkuranHewanDAO>() {
                                @Override
                                public void onResponse(Call<UkuranHewanDAO> call, Response<UkuranHewanDAO> response) {
                                    Toast.makeText(AddUkuran.this, "Sukses Tambah", Toast.LENGTH_SHORT).show();
                                    progress.dismiss();
                                }
                                @Override
                                public void onFailure(Call<UkuranHewanDAO> call, Throwable t) {
                                    Toast.makeText(AddUkuran.this, "Sukses Tambah.", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(AddUkuran.this, ViewUkuranHewan.class);
                                    i.putExtra("from","ukuran");
                                    System.out.println(t.getMessage());
                                    progress.dismiss();
                                    startActivity(i);
                                    finish();
                                }
                            });
                        }
                        @Override
                        public void onFailure(Call<List<UkuranHewanDAO>> call, Throwable t) {
                            System.out.println("gagal");
                        }
                    });
                }


            }
        });
    }
}
