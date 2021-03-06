package com.p3l.kohipetshopu.Fragment_CS.TransaksiProduk.TampilAll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.Fragment_CS.TransaksiProduk.TransaksiPenjualanDAO;
import com.p3l.kohipetshopu.R;
import com.p3l.kohipetshopu.UkuranHewan.ViewUkuranHewan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTampilTransaksiProduk extends AppCompatActivity {
    List<TransaksiPenjualanDAO> List,ListTemp;
    adapterTampilAllTransaksiProduk adapter;
    RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_tampil_transaksi_produk);
        recycle = findViewById(R.id.recycle_view_penjualan_all);
        List = new ArrayList<>();
        ListTemp = new ArrayList<>();
        adapter = new adapterTampilAllTransaksiProduk(this,List);
        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        recycle.setLayoutManager(mLayoutmanager);
        recycle.setItemAnimator(new DefaultItemAnimator());
        recycle.setAdapter(adapter);
        load();
    }
    public void load(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Fetching data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

        Call<List<TransaksiPenjualanDAO>> callDAO = apiService.getAllPenjualan();
        callDAO.enqueue(new Callback<List<TransaksiPenjualanDAO>>() {
            @Override
            public void onResponse(Call<List<TransaksiPenjualanDAO>> call, Response<List<TransaksiPenjualanDAO>> response) {
//                JSONObject object = new JSONObject((Map) response.body());
//                for(int i=0;i<response.body().size();i++){
//                    try {
//                        TransaksiPenjualanDAO trans = (TransaksiPenjualanDAO) response.body();
//                         String data = new ArrayList<>(Arrays.asList(trans.getIdcustomer()));
//                        JSONObject idcust = object.getJSONObject(String.valueOf(i));
//                        idcust.getString("idcustomer");
//                        TransaksiPenjualanDAO dao =  new TransaksiPenjualanDAO(response.body().get(i).getIdtransaksipenjualan(),response.body().get(i).getNoPR(),response.body().get(i).getIdpegawai(),
//                                response.body().get(i).getIdhewan(), ,response.body().get(i).getDiskon(),response.body().get(i).getTotal(),response.body().get(i).getTanggalTransaksi());
//                        List.add(dao);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
                List.addAll(response.body());
                ListTemp.addAll(response.body());
                adapter.notifyDataSetChanged();
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<List<TransaksiPenjualanDAO>> call, Throwable t) {
                Toast.makeText(ViewTampilTransaksiProduk.this, "Internet Anda Ampas",Toast.LENGTH_SHORT).show();
                System.out.println("SAMSUNG  ::::     "+t.getMessage());
                progress.dismiss();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                List.clear();
                List.addAll(ListTemp);
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }// end of  onCreateOptionsMenu(Menu menu) ;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
