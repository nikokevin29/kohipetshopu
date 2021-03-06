package com.p3l.kohipetshopu.Fragment_CS.TransaksiProduk;

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
import android.widget.TextView;
import android.widget.Toast;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.Produk.ProdukDAO;
import com.p3l.kohipetshopu.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.*;

public class ViewPickProduk extends AppCompatActivity  {

    private List<ProdukDAO> ListProduk, ListProdukTemp;
    adapterPickProduk adapterPickProduk;
    private RecyclerView recyclerProduk;
    TextView subtotal_display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pick_produk);
        recyclerProduk = findViewById(R.id.recycler_view_pick_produk);
        subtotal_display = findViewById(R.id.subtotaldisplay);
        update_subtotalPicker();
        ListProduk = new ArrayList<>();
        ListProdukTemp = new ArrayList();
        adapterPickProduk = new adapterPickProduk(this, ListProduk);
        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        recyclerProduk.setLayoutManager(mLayoutmanager);
        recyclerProduk.setItemAnimator(new DefaultItemAnimator());
        recyclerProduk.setAdapter(adapterPickProduk);
        loadData();
    }//End Of On Create

    public void update_subtotalPicker(){
        double tempsubtotal = 0;
        for(int i = 0; i< com.p3l.kohipetshopu.Fragment_CS.TransaksiProduk.adapterPickProduk.tempProduk.size(); i++){
            tempsubtotal = tempsubtotal + Double.parseDouble(com.p3l.kohipetshopu.Fragment_CS.TransaksiProduk.adapterPickProduk.tempProduk.get(i).subtotal);
        }
        subtotal_display.setText(String.valueOf(tempsubtotal));
        System.out.println("update_subtotalPicker : "+tempsubtotal);
    }

    public void loadData() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProdukDAO>> DAOCall = apiService.getAllProduk();

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Fetching data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

        DAOCall.enqueue(new Callback<List<ProdukDAO>>() {
            @Override
            public void onResponse(Call<List<ProdukDAO>> call, Response<List<ProdukDAO>> response) {
                for(int i=0;i<response.body().size();i++){
                    if(Integer.parseInt(response.body().get(i).getStok()) > 0){// Perulangan untuk mendisplay stock yang > 0
                        ListProduk.add(response.body().get(i));
                        ListProdukTemp.add(response.body().get(i));
                        adapterPickProduk.notifyDataSetChanged();
                    }else{
                        Toast.makeText(ViewPickProduk.this, "Tidak Ada Produk Tersedia", Toast.LENGTH_SHORT).show();//Di list Kosong
                    }
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<List<ProdukDAO>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ViewPickProduk.this, "Ulangi lagi, koneksi bermasalah", Toast.LENGTH_SHORT).show();
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
                adapterPickProduk.getFilter().filter(newText);
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
