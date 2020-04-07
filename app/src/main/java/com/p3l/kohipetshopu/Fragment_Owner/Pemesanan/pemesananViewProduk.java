package com.p3l.kohipetshopu.Fragment_Owner.Pemesanan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.Produk.ProdukDAO;
import com.p3l.kohipetshopu.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.*;

public class pemesananViewProduk extends AppCompatActivity implements adapterPickProdukPemesanan.PickProdukPemesananAdapterListener {

    private List<ProdukDAO> ListProduk, ListProdukTemp;
    adapterPickProdukPemesanan adapterPickProdukPemesanan;
    private RecyclerView recyclerProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pemesanan_view_produk);
        recyclerProduk = findViewById(R.id.recycler_view_produk_pemesanan);

        ListProduk = new ArrayList<>();
        ListProdukTemp = new ArrayList();
        adapterPickProdukPemesanan = new adapterPickProdukPemesanan(this, ListProduk, this);
        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        recyclerProduk.setLayoutManager(mLayoutmanager);
        recyclerProduk.setItemAnimator(new DefaultItemAnimator());
        recyclerProduk.setAdapter(adapterPickProdukPemesanan);


        loadData();

    }//End Of On Create


    public void loadData() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProdukDAO>> produkDAOCall = apiService.getAllProduk();

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Fetching data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

        produkDAOCall.enqueue(new Callback<List<ProdukDAO>>() {
            @Override
            public void onResponse(Call<List<ProdukDAO>> call, Response<List<ProdukDAO>> response) {
                ListProduk.addAll(response.body());
                ListProdukTemp.addAll(response.body());
                adapterPickProdukPemesanan.notifyDataSetChanged();
                progress.dismiss();
                Toast.makeText(pemesananViewProduk.this, "Tekan yang Lama untuk Melakukan Aksi", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ProdukDAO>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(pemesananViewProduk.this, "Internet Anda Ampas", Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }// end of  loadData()

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
                ListProduk.clear();
                ListProduk.addAll(ListProdukTemp);
                adapterPickProdukPemesanan.getFilter().filter(newText);
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

    @Override
    public void onProdukSelected(ProdukDAO produkDAO) {
        Toast.makeText(this, "Selected", Toast.LENGTH_SHORT).show();
    }
}
