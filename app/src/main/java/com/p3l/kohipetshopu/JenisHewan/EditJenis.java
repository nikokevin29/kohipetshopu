package com.p3l.kohipetshopu.JenisHewan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class EditJenis extends AppCompatActivity {
    EditText etNamaJenis_update;
    Button btn_Submit_update_jenis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_jenis);
        etNamaJenis_update = findViewById(R.id.etNamaJenis_update);
        btn_Submit_update_jenis = findViewById(R.id.btn_Submit_update_jenis);
        setField();


        btn_Submit_update_jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<JenisHewanDAO> tipsDAOCall = apiService.editJenis(getIntent().getStringExtra("idjenis"),
                        etNamaJenis_update.getText().toString());
                    System.out.println(getIntent().getStringExtra("idjenis")+" "+etNamaJenis_update.getText().toString());
                tipsDAOCall.enqueue(new Callback<JenisHewanDAO>() {
                    @Override
                    public void onResponse(Call<JenisHewanDAO> call, Response<JenisHewanDAO> response) {
                        Toast.makeText(EditJenis.this, "Edit Success", Toast.LENGTH_SHORT).show();
                        startIntent();
                        finish();
                    }
                    @Override
                    public void onFailure(Call<JenisHewanDAO> call, Throwable t) {
                        Toast.makeText(EditJenis.this, "Edit Failed", Toast.LENGTH_SHORT).show();
                        System.out.println(t.getMessage());
                    }
                });
            }
        });
    }
    public void setField(){
        etNamaJenis_update.setText(getIntent().getStringExtra("nama"));
    }
    public void startIntent(){
        Intent acc = new Intent(EditJenis.this, ViewJenisHewan.class);
        acc.putExtra("from", "tips");
        startActivity(acc);
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
