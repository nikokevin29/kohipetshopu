package com.p3l.kohipetshopu.Fragment_CS.TransaksiProduk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.Fragment_CS.Customer_RUDS.CreateCustomer;
import com.p3l.kohipetshopu.Fragment_CS.Customer_RUDS.CustomerDAO;
import com.p3l.kohipetshopu.Fragment_CS.Hewan_RUDS.CreateHewan;
import com.p3l.kohipetshopu.Fragment_CS.Hewan_RUDS.HewanDAO;
import com.p3l.kohipetshopu.Fragment_CS.TransaksiProduk.adapterPickProduk;
import com.p3l.kohipetshopu.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTransaksiProduk extends AppCompatActivity {

    TextView subtotalAll;
    Button createCustomer,createHewan,tambahProduk,prosesTransaksi;
    Spinner spinCustomer,spinHewan;
    String selectedHewan,selectedCustomer;
    List<HewanDAO> ListHewan;
    List<CustomerDAO> ListCustomer;
    String default0 = "0";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_transaksi_produk);

        subtotalAll = findViewById(R.id.subtotaldisplayAll);
        initCustomer(); initHewan(); //init Spinner Cust dan animal
        spinHewan = findViewById(R.id.spinnerTransaksiHewan);
        spinHewan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHewan = parent.getItemAtPosition(position).toString();
                for(int count=0;count<ListHewan.size();count++){
                    if(selectedHewan.equalsIgnoreCase(ListHewan.get(count).getNama())){
                        selectedHewan = ListHewan.get(count).getIdhewan();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinCustomer = findViewById(R.id.spinnerTransaksiCustomer);
        spinCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCustomer = parent.getItemAtPosition(position).toString();
                for(int count=0;count<ListCustomer.size();count++){
                    if(selectedCustomer.equalsIgnoreCase(ListCustomer.get(count).getNama())){
                        selectedCustomer = ListCustomer.get(count).getIdcustomer();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        createCustomer = findViewById(R.id.btnCreateCustomer);
        createCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(ViewTransaksiProduk.this, CreateCustomer.class);
                startActivity(i);
                finish();
            }
        });
        createHewan = findViewById(R.id.btnCreateHewan);
        createHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewTransaksiProduk.this, CreateHewan.class);
                startActivity(i);
                finish();
            }
        });
        tambahProduk = findViewById(R.id.addProdukTransaksi);
        tambahProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewTransaksiProduk.this, ViewPickProduk.class);
                startActivity(i);
            }
        });
        prosesTransaksi = findViewById(R.id.proses_Transaksi_penjualan);
        prosesTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buatTransaksi();
                Toast.makeText(ViewTransaksiProduk.this, "Transaksi DiProses", Toast.LENGTH_SHORT).show();
            }
        });


        if(adapterPickProduk.tempProduk == null){
            adapterPickProduk.tempProduk = new ArrayList<>();
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycle_tampilan_penjualan_barang);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new adapterViewTransaksi(ViewTransaksiProduk.this,adapterPickProduk.tempProduk);
        recyclerView.setAdapter(mAdapter);
    }

    private void buatTransaksi(){
        SharedPreferences mSettings = getSharedPreferences("LoginCS", Context.MODE_PRIVATE);// SharedPreferences Ambil dari Login
        String idpegawai = mSettings.getString("id","1");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransaksiPenjualanDAO> callDAO = apiService.createPenjualan(
                                                idpegawai,
                                                selectedHewan,
                                                selectedCustomer,
                                                default0,//diskon
                                                default0);//total
        callDAO.enqueue(new Callback<TransaksiPenjualanDAO>() {
            @Override
            public void onResponse(Call<TransaksiPenjualanDAO> call, Response<TransaksiPenjualanDAO> response) {
                Toast.makeText(ViewTransaksiProduk.this, "Gagal Transaksi", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<TransaksiPenjualanDAO> call, Throwable t) {
                System.out.println("BNN"+t.getMessage());
                Toast.makeText(ViewTransaksiProduk.this, "KOP Struk generated", Toast.LENGTH_SHORT).show();
            }
        });

        //detil


//        Call<DetilPenjualanDAO> callDAOdetil = apiService.createDetilPenjualan(
//
//        );


    }

    private void initCustomer(){
        spinCustomer = findViewById(R.id.spinnerTransaksiCustomer);
        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Fetching data ke Spinner");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<CustomerDAO>> DAOCall = apiService.getAllCustomer();
        DAOCall.enqueue(new Callback<List<CustomerDAO>>() {
            @Override
            public void onResponse(Call<List<CustomerDAO>> call, Response<List<CustomerDAO>> response) {
                if(response.isSuccessful()){
                    progress.dismiss();
                    ListCustomer = response.body();
                    List<String> listSpinner = new ArrayList<>();
                    for(int i=0;i<ListCustomer.size();i++){
                        listSpinner.add(ListCustomer.get(i).getNama());
                    }
                    //masukin hasil ke spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewTransaksiProduk.this, android.R.layout.simple_spinner_item,listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinCustomer.setAdapter(adapter);
                }else{
                    progress.dismiss();
                    Toast.makeText(ViewTransaksiProduk.this, "Gagal get Customer ke Spinner", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<CustomerDAO>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ViewTransaksiProduk.this, "masalah koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initHewan(){
        spinHewan = findViewById(R.id.spinnerTransaksiHewan);
        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Fetching data ke Spinner");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<HewanDAO>> DAOCall = apiService.getAllHewan();
        DAOCall.enqueue(new Callback<List<HewanDAO>>() {
            @Override
            public void onResponse(Call<List<HewanDAO>> call, Response<List<HewanDAO>> response) {
                if(response.isSuccessful()){
                    progress.dismiss();
                    ListHewan = response.body();
                    List<String> listSpinner = new ArrayList<>();
                    for(int i=0;i<ListHewan.size();i++){
                        listSpinner.add(ListHewan.get(i).getNama());
                    }
                    //masukin hasil ke spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewTransaksiProduk.this, android.R.layout.simple_spinner_item,listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinHewan.setAdapter(adapter);
                }else{
                    progress.dismiss();
                    Toast.makeText(ViewTransaksiProduk.this, "Gagal get Hewan ke Spinner", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<HewanDAO>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ViewTransaksiProduk.this, "masalah koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
