package com.p3l.kohipetshopu.UkuranHewan;

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

public class EditUkuran extends AppCompatActivity {

    EditText etNamaUkuran_update;
    Button btn_Submit_update_ukuran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ukuran);
        etNamaUkuran_update = findViewById(R.id.etNamaUkuran_update);
        btn_Submit_update_ukuran = findViewById(R.id.btn_Submit_update_Ukuran);
        setField();

        ProgressDialog progress = new ProgressDialog(this);

        btn_Submit_update_ukuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNamaUkuran_update.getText().length() == 0){
                    Toast.makeText(EditUkuran.this, "Masih Kosong", Toast.LENGTH_SHORT).show();
                }else{


                    SharedPreferences mSettings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                    String aktor = mSettings.getString("id","2");

                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<UkuranHewanDAO> tipsDAOCall = apiService.editUkuran(getIntent().getStringExtra("idukuran"),etNamaUkuran_update.getText().toString(),aktor);

                    System.out.println(getIntent().getStringExtra("idukuran")+" "+etNamaUkuran_update.getText().toString());

                    progress.setMessage("Memproses data . . . ");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setCancelable(false);
                    progress.show();


                    tipsDAOCall.enqueue(new Callback<UkuranHewanDAO>() {
                        @Override
                        public void onResponse(Call<UkuranHewanDAO> call, Response<UkuranHewanDAO> response) {
                            Toast.makeText(EditUkuran.this, "Edit Success", Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                            startIntent();
                            finish();
                        }
                        @Override
                        public void onFailure(Call<UkuranHewanDAO> call, Throwable t) {
                            Toast.makeText(EditUkuran.this, "Edit Success.", Toast.LENGTH_SHORT).show();
                            System.out.println(t.getMessage());
                            progress.dismiss();
                            startIntent();
                            finish();
                        }
                    });
                }

            }
        });
    }
    public void setField(){
        etNamaUkuran_update.setText(getIntent().getStringExtra("nama"));
    }
    public void startIntent(){
        Intent acc = new Intent(EditUkuran.this, ViewUkuranHewan.class);
        acc.putExtra("from", "ukuran");
        startActivity(acc);
        finish();
    }
}
