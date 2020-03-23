package com.p3l.kohipetshopu.Layanan;

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
import com.p3l.kohipetshopu.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewLayanan extends AppCompatActivity implements AdapterLayanan.LayananAdapterListener {

    private List<LayananDAO> ListLayanan, ListLayananTemp;
    AdapterLayanan adapterLayanan;
    private RecyclerView recyclerLayanan;
    private FloatingActionButton add_layanan,sort_layanan,refresh_data_layanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_layanan);
        recyclerLayanan = findViewById(R.id.recycler_view_layanan);

        ListLayanan = new ArrayList<>();
        ListLayananTemp = new ArrayList();
        adapterLayanan = new AdapterLayanan(this, ListLayanan,this);
        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        recyclerLayanan.setLayoutManager(mLayoutmanager);
        recyclerLayanan.setItemAnimator(new DefaultItemAnimator());
        recyclerLayanan.setAdapter(adapterLayanan);

        initFloatingButton();
        loadData();

    }//End Of On Create

    public void initFloatingButton(){
        add_layanan = findViewById(R.id.add_layanan);
        add_layanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(ViewLayanan.this, AddLayanan.class);
                i.putExtra("from","AddLayanan");
                startActivity(i);
            }
        });
        sort_layanan = findViewById(R.id.sort_layanan);
        sort_layanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewLayanan.this, "Fungsi Belum dibuat", Toast.LENGTH_SHORT).show();
            }
        });
        refresh_data_layanan = findViewById(R.id.refresh_data_layanan);
        refresh_data_layanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }//End of  initFloatingButton()

    public void loadData(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<LayananDAO>> layananDAOCall = apiService.getAllLayanan();

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Fetching data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

        layananDAOCall.enqueue(new Callback<List<LayananDAO>>() {
            @Override
            public void onResponse(Call<List<LayananDAO>> call, Response<List<LayananDAO>> response) {
                ListLayanan.addAll(response.body());
                ListLayananTemp.addAll(response.body());
                adapterLayanan.notifyDataSetChanged();
                progress.dismiss();
                Toast.makeText(ViewLayanan.this, "Tekan yang Lama untuk Melakukan Aksi", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<LayananDAO>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ViewLayanan.this, "Internet Anda Ampas",Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }// end of  loadData()

    @Override
    public void onLayananSelected(LayananDAO layananDAO) {
        Toast.makeText(getApplicationContext(), "Selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);

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
                ListLayanan.clear();
                ListLayanan.addAll(ListLayananTemp);
                adapterLayanan.getFilter().filter(newText);
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
}