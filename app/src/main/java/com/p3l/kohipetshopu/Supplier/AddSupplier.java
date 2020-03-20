package com.p3l.kohipetshopu.Supplier;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.R;
import com.p3l.kohipetshopu.UkuranHewan.AddUkuran;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSupplier extends AppCompatActivity {

    EditText etNamaSupplier,etAlamatSupplier,etNotelpSupplier;
    Button btn_Submit_add_supplier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supplier);
        etNamaSupplier = (EditText) findViewById(R.id.etNamaSupplier);
        etAlamatSupplier = (EditText) findViewById(R.id.etAlamatSupplier);
        etNotelpSupplier = (EditText) findViewById(R.id.etNotelpSupplier);
        btn_Submit_add_supplier = (Button) findViewById(R.id.btn_Submit_add_supplier);

        ProgressDialog progress = new ProgressDialog(this);

        btn_Submit_add_supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNamaSupplier.getText().length() == 0 || etNotelpSupplier.getText().length() == 0 || etNotelpSupplier.getText().length() == 0){
                    Toast.makeText(AddSupplier.this, "Masih Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<List<SupplierDAO>> supplierDAOCall = apiService.getAllSupplier();

                    progress.setMessage("Memproses data . . . ");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setCancelable(false);
                    progress.show();

                    supplierDAOCall.enqueue(new Callback<List<SupplierDAO>>() {
                        @Override
                        public void onResponse(Call<List<SupplierDAO>> call, Response<List<SupplierDAO>> response) {
                            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                            Call<SupplierDAO> SupplierDAO = apiService.createSupplier(etNamaSupplier.getText().toString(),
                                                                                      etAlamatSupplier.getText().toString(),
                                                                                      etNotelpSupplier.getText().toString());
                            SupplierDAO.enqueue(new Callback<SupplierDAO>() {
                                @Override
                                public void onResponse(Call<SupplierDAO> call, Response<SupplierDAO> response) {
                                    Toast.makeText(AddSupplier.this, "Sukses Tambah", Toast.LENGTH_SHORT).show();
                                    progress.dismiss();
                                }
                                @Override
                                public void onFailure(Call<SupplierDAO> call, Throwable t) {
                                    Toast.makeText(AddSupplier.this, "Sukses Tambah.", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(AddSupplier.this, ViewSupplier.class);
                                    i.putExtra("from","supplier");
                                    System.out.println(t.getMessage());
                                    progress.dismiss();
                                    startActivity(i);
                                    finish();
                                }
                            });
                        }
                        @Override
                        public void onFailure(Call<List<SupplierDAO>> call, Throwable t) {
                            System.out.println("gagal");
                        }
                    });
                }


            }
        });
    }
}
