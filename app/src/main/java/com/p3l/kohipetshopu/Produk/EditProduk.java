package com.p3l.kohipetshopu.Produk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.R;
import com.p3l.kohipetshopu.Supplier.SupplierDAO;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.pusher.pushnotifications.PushNotifications;

public class EditProduk extends AppCompatActivity {

    Button submitProduk_edit;
    EditText nama,stok,stokmin,harga;

    String selectedSupplier;
    List<SupplierDAO> ListSupplier;
    Spinner spinnerSupplier;
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

        spinnerSupplier = findViewById(R.id.spinner_edit_supplier_produk);
        spinnerSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//test toast kalo selected
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSupplier = parent.getItemAtPosition(position).toString();
                System.out.println("SUPPLIER  "+selectedSupplier);
                for(int count=0;count<ListSupplier.size();count++){
                    if(selectedSupplier.equalsIgnoreCase(ListSupplier.get(count).getNama())){
                        selectedSupplier = ListSupplier.get(count).getIdsupplier();
                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        initSupplier();
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

                    SharedPreferences mSettings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                    String aktor = mSettings.getString("id","missing");

                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<ProdukDAO> produkDAOCall = apiService.editProduk(getIntent().getStringExtra("idproduk"),
                            nama.getText().toString(),
                            harga.getText().toString(),
                            stok.getText().toString(),
                            stokmin.getText().toString(),
                            selectedSupplier,
                            aktor);

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

                            //pushNotification();
                        }
                    });
                }

            }
        });
    }

    public void pushNotification(){

    }
    public void setField(){
        nama.setText(getIntent().getStringExtra("nama"));
        harga.setText(getIntent().getStringExtra("harga"));
        stok.setText(getIntent().getStringExtra("stok"));
        stokmin.setText(getIntent().getStringExtra("stokmin"));
    }
    public void startIntent(){
        Intent acc = new Intent(EditProduk.this, ViewProduk.class);
        acc.putExtra("from", "produk");
        startActivity(acc);
        finish();
    }
    private void initSupplier(){
        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Fetching Supplier ke Spinner");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<SupplierDAO>> supplierDAOCall = apiService.getAllSupplier();
        supplierDAOCall.enqueue(new Callback<List<SupplierDAO>>() {
            @Override
            public void onResponse(Call<List<SupplierDAO>> call, Response<List<SupplierDAO>> response) {
                if(response.isSuccessful()){
                    progress.dismiss();
                    ListSupplier = response.body();
                    List<String> listSpinner = new ArrayList<>();
                    for(int i=0;i<ListSupplier.size();i++){
                        listSpinner.add(ListSupplier.get(i).getNama());
                    }
                    //masukin hasil ke spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(EditProduk.this, android.R.layout.simple_spinner_item,listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSupplier.setAdapter(adapter);
                }else{
                    progress.dismiss();
                    Toast.makeText(EditProduk.this, "Gagal get Supplier ke Spinner", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<SupplierDAO>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(EditProduk.this, "Koneksi Anda benar-benar Ampas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
