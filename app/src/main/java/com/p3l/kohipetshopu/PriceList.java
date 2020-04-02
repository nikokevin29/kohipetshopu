package com.p3l.kohipetshopu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.Produk.AdapterProduk;
import com.p3l.kohipetshopu.Produk.ProdukDAO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriceList extends AppCompatActivity  implements AdapterPriceList.ProdukPriceAdapterListener{

    private List<ProdukDAO> ListProduk, ListProdukTemp;
    AdapterPriceList adapterProduk;
    private RecyclerView recyclerProduk;
    private FloatingActionButton sort_produk, refresh_data_produk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_price_list);
        recyclerProduk = findViewById(R.id.recycler_price_list);

        ListProduk = new ArrayList<>();
        ListProdukTemp = new ArrayList();
        adapterProduk = new AdapterPriceList(this, ListProduk, this);
        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        recyclerProduk.setLayoutManager(mLayoutmanager);
        recyclerProduk.setItemAnimator(new DefaultItemAnimator());
        recyclerProduk.setAdapter(adapterProduk);

        initFloatingButton();
        loadData();

    }//End Of On Create

    public void initFloatingButton() {
        sort_produk = findViewById(R.id.sort_produk_price_list);
        sort_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PriceList.this, "Fungsi Belum dibuat", Toast.LENGTH_SHORT).show();
            }
        });
        refresh_data_produk = findViewById(R.id.refresh_data_produk_price_list);
        refresh_data_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }//End of  initFloatingButton()

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
                adapterProduk.notifyDataSetChanged();
                progress.dismiss();
                Toast.makeText(PriceList.this, "Tekan yang Lama untuk Melakukan Aksi", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ProdukDAO>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(PriceList.this, "Internet Anda Ampas", Toast.LENGTH_SHORT).show();
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
                adapterProduk.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }// end of  onCreateOptionsMenu(Menu menu) ;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
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
