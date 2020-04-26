package com.p3l.kohipetshopu.Fragment_CS.TransaksiLayanan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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
import com.p3l.kohipetshopu.Fragment_CS.Customer_CRUDS.CreateCustomer;
import com.p3l.kohipetshopu.Fragment_CS.Customer_CRUDS.CustomerDAO;
import com.p3l.kohipetshopu.Fragment_CS.Hewan_CRUDS.CreateHewan;
import com.p3l.kohipetshopu.Fragment_CS.Hewan_CRUDS.HewanDAO;
import com.p3l.kohipetshopu.Fragment_CS.TransaksiLayanan.TampilAll.ViewTampilTransaksiLayanan;
import com.p3l.kohipetshopu.Fragment_CS.TransaksiLayanan.adapterPickLayanan;
import com.p3l.kohipetshopu.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTransaksiLayanan extends AppCompatActivity {

    TextView subtotalAll;
    Button createCustomer,createHewan,tambahProduk,prosesTransaksi,tampilAll;
    Spinner spinCustomer,spinHewan;
    String selectedHewan,selectedCustomer;
    List<HewanDAO> ListHewan;
    List<CustomerDAO> ListCustomer;
    String default0 = "0",status="Diproses";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private boolean doubleTapParam;
    String maxid ="0";

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_transaksi_produk);
        tampilAll =findViewById(R.id.tampil_all_transaksi);
        tampilAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewTransaksiLayanan.this, ViewTampilTransaksiLayanan.class);
                startActivity(i);
            }
        });
        subtotalAll = findViewById(R.id.subtotaldisplayAll);
        initCustomer(); initHewan(); //init Spinner Customer dan Animal
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
                Intent i  = new Intent(ViewTransaksiLayanan.this, CreateCustomer.class);
                startActivity(i);
            }
        });
        createHewan = findViewById(R.id.btnCreateHewan);
        createHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewTransaksiLayanan.this, CreateHewan.class);
                startActivity(i);
            }
        });
        tambahProduk = findViewById(R.id.addProdukTransaksi);
        tambahProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewTransaksiLayanan.this, ViewPickLayanan.class);
                startActivity(i);
            }
        });
        prosesTransaksi = findViewById(R.id.proses_Transaksi_penjualan);

        progress= new ProgressDialog(this);
        prosesTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapterPickLayanan.tempLayanan.isEmpty()){
                    Toast.makeText(ViewTransaksiLayanan.this, "Transaksi Masih Kosong !!!", Toast.LENGTH_SHORT).show();
                }else{
                    progress.setMessage("Fetching data");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setCancelable(false);
                    progress.show();
                    buatTransaksi();
                }
            }
        });
        recyclerView = findViewById(R.id.recycle_tampilan_penjualan_barang);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new adapterViewTransaksiLayanan(ViewTransaksiLayanan.this,adapterPickLayanan.tempLayanan);
        recyclerView.setAdapter(mAdapter);
    }

    private void buatTransaksi(){
        SharedPreferences mSettings = getSharedPreferences("LoginCS", Context.MODE_PRIVATE);// SharedPreferences Ambil dari Login
        String idpegawai = mSettings.getString("id","1");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransaksiPelayananDAO> callDAO = apiService.createPelayanan(
                idpegawai,
                selectedHewan,
                selectedCustomer,
                status,
                default0,//diskon
                default0);//total

        callDAO.enqueue(new Callback<TransaksiPelayananDAO>() {
            @Override
            public void onResponse(Call<TransaksiPelayananDAO> call, Response<TransaksiPelayananDAO> response) {
                Toast.makeText(ViewTransaksiLayanan.this, "Gagal Transaksi", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
            @Override
            public void onFailure(Call<TransaksiPelayananDAO> call, Throwable t) {
                System.out.println("BNN"+t.getMessage());

                //get max id transaksi
                Call<TransaksiPelayananDAO> SELAnjing = apiService.getLastidPelayanan();
                SELAnjing.enqueue(new Callback<TransaksiPelayananDAO>() {
                    @Override
                    public void onResponse(Call<TransaksiPelayananDAO> call, Response<TransaksiPelayananDAO> response) {
                        maxid = response.body().getIdtransaksipelayanan();

                        System.out.println("maxid "+maxid);

                        //detil
                        for(int i = 0; i<adapterPickLayanan.tempLayanan.size(); i++){
                            Call<DetilPelayananDAO> callDAOdetil = apiService.createDetilPelayanan(
                                    adapterPickLayanan.tempLayanan.get(i).idlayanan,
                                    adapterPickLayanan.tempLayanan.get(i).jumlah,
                                    adapterPickLayanan.tempLayanan.get(i).subtotal,
                                    maxid);

                            callDAOdetil.enqueue(new Callback<DetilPelayananDAO>() {
                                @Override
                                public void onResponse(Call<DetilPelayananDAO> call, Response<DetilPelayananDAO> response) {
                                    Log.d(response.toString(),"on Failure");

                                }
                                @Override
                                public void onFailure(Call<DetilPelayananDAO> call, Throwable t) {
                                    Log.d(t.toString(),"onResponse Akal : Success");
                                    Toast.makeText(ViewTransaksiLayanan.this, "Stored to Transaction : "+maxid, Toast.LENGTH_SHORT).show();

                                    adapterPickLayanan.tempLayanan.clear();//clear isi Array detil transaksi
                                    update_updated();//Update adapter
                                    subtotalFromRecycleTransaksi();//Update subtotal TextView
                                }
                            });
                        }//end of for
                    }
                    @Override
                    public void onFailure(Call<TransaksiPelayananDAO> call, Throwable t) {
                        Toast.makeText(ViewTransaksiLayanan.this, "get max id failed", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                });
                Toast.makeText(ViewTransaksiLayanan.this, "Transaksi Berhasil", Toast.LENGTH_LONG).show();
                progress.dismiss();
            }
        });


    }

    public void delete_updated(int position,List<DetilPelayananDAO> tempProduk){
        recyclerView.removeViewAt(position);
        mAdapter.notifyItemRemoved(position);
        mAdapter.notifyItemRangeChanged(position, tempProduk.size());
    }
    public void update_updated(){
        mAdapter.notifyDataSetChanged();
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewTransaksiLayanan.this, android.R.layout.simple_spinner_item,listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinCustomer.setAdapter(adapter);
                }else{
                    progress.dismiss();
                    Toast.makeText(ViewTransaksiLayanan.this, "Gagal get Customer ke Spinner", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<CustomerDAO>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ViewTransaksiLayanan.this, "masalah koneksi", Toast.LENGTH_SHORT).show();
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewTransaksiLayanan.this, android.R.layout.simple_spinner_item,listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinHewan.setAdapter(adapter);
                }else{
                    progress.dismiss();
                    Toast.makeText(ViewTransaksiLayanan.this, "Gagal get Hewan ke Spinner", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<HewanDAO>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ViewTransaksiLayanan.this, "Masalah koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() { //2x Tap Untuk Keluar
        if (doubleTapParam) {
            super.onBackPressed();
            adapterPickLayanan.tempLayanan.clear();//waktu tap 2x tempProduk dari picker akan dihapus Arraynya jadi Detillist akan kosong disini
            return ;
        }
        this.doubleTapParam = true;
        Toast.makeText(this, "Tap sekali lagi untuk Kembali", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleTapParam = false;
            }
        }, 2000);
    }
    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        mAdapter.notifyDataSetChanged();//Refresh habis pick barang
        subtotalFromRecycleTransaksi();
    }
    public void subtotalFromRecycleTransaksi(){
        double tempsubtotal = 0;
        for(int i=0;i<adapterPickLayanan.tempLayanan.size();i++){
            tempsubtotal =  tempsubtotal + Double.parseDouble(adapterPickLayanan.tempLayanan.get(i).getSubtotal());
        }
        subtotalAll.setText(String.valueOf(tempsubtotal));
    }
}
