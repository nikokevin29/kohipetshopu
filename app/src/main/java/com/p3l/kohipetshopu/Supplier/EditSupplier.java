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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSupplier extends AppCompatActivity {

    EditText etNamaSupplier_update,etAlamatSupplier_update,etNotelpSupplier_update;
    Button btn_Submit_update_supplier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_supplier);
        etNamaSupplier_update = findViewById(R.id.etNamaSupplier_edit);
        etAlamatSupplier_update = findViewById(R.id.etAlamatSupplier_edit);
        etNotelpSupplier_update = findViewById(R.id.etNotelpSupplier_edit);
        btn_Submit_update_supplier = findViewById(R.id.btn_Submit_edit_supplier);
        setField();

        ProgressDialog progress = new ProgressDialog(this);

        btn_Submit_update_supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNamaSupplier_update.getText().length() == 0 || etAlamatSupplier_update.getText().length() == 0 || etNotelpSupplier_update.getText().length() == 0){
                    Toast.makeText(EditSupplier.this, "Masih Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<SupplierDAO> supplierDAOCall = apiService.editSupplier(getIntent().getStringExtra("idsupplier"),
                                                                                            etNamaSupplier_update.getText().toString(),
                                                                                            etAlamatSupplier_update.getText().toString(),
                                                                                            etNotelpSupplier_update.getText().toString());

                    System.out.println(getIntent().getStringExtra("idsupplier")+" "+etNamaSupplier_update.getText().toString());

                    progress.setMessage("Memproses data . . . ");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setCancelable(false);
                    progress.show();

                    supplierDAOCall.enqueue(new Callback<SupplierDAO>() {
                        @Override
                        public void onResponse(Call<SupplierDAO> call, Response<SupplierDAO> response) {
                            Toast.makeText(EditSupplier.this, "Edit Success", Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                            startIntent();
                            finish();
                        }
                        @Override
                        public void onFailure(Call<SupplierDAO> call, Throwable t) {
                            Toast.makeText(EditSupplier.this, "Edit Success.", Toast.LENGTH_SHORT).show();
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
        etNamaSupplier_update.setText(getIntent().getStringExtra("nama"));
        etAlamatSupplier_update.setText(getIntent().getStringExtra("alamat"));
        etNotelpSupplier_update.setText(getIntent().getStringExtra("notelp"));
    }
    public void startIntent(){
        Intent acc = new Intent(EditSupplier.this, ViewSupplier.class);
        acc.putExtra("from", "supplier");
        startActivity(acc);
        finish();
    }
}
