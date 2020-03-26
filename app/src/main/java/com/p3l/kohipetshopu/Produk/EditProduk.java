package com.p3l.kohipetshopu.Produk;

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

public class EditProduk extends AppCompatActivity {

    Button submitProduk_edit;
    EditText nama,stok,stokmin,harga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_produk);
        nama = findViewById(R.id.etNamaProduk_edit);
        stok = findViewById(R.id.etStokProduk_edit);
        stokmin = findViewById(R.id.etStokMinimumProduk_edit);
        harga = findViewById(R.id.etHargaProduk_edit);
        submitProduk_edit = findViewById(R.id.btn_Submit_edit_produk);
        setField();
        ProgressDialog progress = new ProgressDialog(this);

        submitProduk_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int cekstok = Integer.parseInt(stok.getText().toString());
                int cekstokmin = Integer.parseInt(stokmin.getText().toString());
                if(nama.getText().length() == 0 || stok.getText().length() == 0 || stokmin.getText().length() == 0|| harga.getText().length() == 0){
                    Toast.makeText(EditProduk.this, "Masih Kosong", Toast.LENGTH_SHORT).show();
                }else if(cekstokmin > cekstok ){
                    System.out.println("stokmin :"+cekstokmin);
                    System.out.println("stok :"+cekstok);
                    Toast.makeText(EditProduk.this, "Stok Minimum Tidak Boleh lebih kecil dari jumlah stok", Toast.LENGTH_SHORT).show();
                }
                else{
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<ProdukDAO> produkDAOCall = apiService.editProduk(getIntent().getStringExtra("idproduk"),
                            nama.getText().toString(),
                            harga.getText().toString(),
                            stok.getText().toString(),
                            stokmin.getText().toString());

                    System.out.println(getIntent().getStringExtra("idproduk")+" "+nama.getText().toString());


                    progress.setMessage("Memproses data . . . ");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setCancelable(false);
                    progress.show();

                    produkDAOCall.enqueue(new Callback<ProdukDAO>() {
                        @Override
                        public void onResponse(Call<ProdukDAO> call, Response<ProdukDAO> response) {
                            Toast.makeText(EditProduk.this, "Edit Success tapi boong", Toast.LENGTH_SHORT).show();
                            System.out.println("PISANG"+response.body());
                            progress.dismiss();
                            startIntent();
                        }
                        @Override
                        public void onFailure(Call<ProdukDAO> call, Throwable t) {
                            Toast.makeText(EditProduk.this, "Edit Success.", Toast.LENGTH_SHORT).show();
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
        nama.setText(getIntent().getStringExtra("nama"));
        harga.setText(getIntent().getStringExtra("harga"));
        stok.setText(getIntent().getStringExtra("stok"));//dia gamau kedit lah ya
        stokmin.setText(getIntent().getStringExtra("stokmin"));
    }
    public void startIntent(){
        Intent acc = new Intent(EditProduk.this, ViewProduk.class);
        acc.putExtra("from", "produk");
        startActivity(acc);
        finish();
    }
}
