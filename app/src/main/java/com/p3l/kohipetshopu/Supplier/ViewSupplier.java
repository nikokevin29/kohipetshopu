package com.p3l.kohipetshopu.Supplier;

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

public class ViewSupplier extends AppCompatActivity implements AdapterSupplier.SupplierAdapterListener {

    private List<SupplierDAO> ListSupplier, ListSupplierTemp;
    AdapterSupplier adapterSupplier;
    private RecyclerView recyclerSupplier;
    private FloatingActionButton add_supplier,sort_supplier,refresh_data_supplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_supplier);
        recyclerSupplier = findViewById(R.id.recycler_view_supplier);

        ListSupplier = new ArrayList<>();
        ListSupplierTemp = new ArrayList();
        adapterSupplier = new AdapterSupplier(this, ListSupplier,this);
        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        recyclerSupplier.setLayoutManager(mLayoutmanager);
        recyclerSupplier.setItemAnimator(new DefaultItemAnimator());
        recyclerSupplier.setAdapter(adapterSupplier);

        initFloatingButton();
        loadData();

    }//End Of On Create

    public void initFloatingButton(){
        add_supplier = findViewById(R.id.add_supplier);
        add_supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(ViewSupplier.this, AddSupplier.class);
                i.putExtra("from","AddSupplier");
                startActivity(i);
            }
        });
        sort_supplier = findViewById(R.id.sort_supplier);
        sort_supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewSupplier.this, "Fungsi Belum dibuat", Toast.LENGTH_SHORT).show();
            }
        });
        refresh_data_supplier = findViewById(R.id.refresh_data_supplier);
        refresh_data_supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                ListSupplier.clear();
                ListSupplierTemp.clear();
            }
        });
    }//End of  initFloatingButton()

    public void loadData(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<SupplierDAO>> supplierDAOCall = apiService.getAllSupplier();

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Fetching data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

        supplierDAOCall.enqueue(new Callback<List<SupplierDAO>>() {
            @Override
            public void onResponse(Call<List<SupplierDAO>> call, Response<List<SupplierDAO>> response) {
                ListSupplier.addAll(response.body());
                ListSupplierTemp.addAll(response.body());
                adapterSupplier.notifyDataSetChanged();
                progress.dismiss();
                Toast.makeText(ViewSupplier.this, "Tekan yang Lama untuk Melakukan Aksi", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<SupplierDAO>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ViewSupplier.this, "Internet Anda Ampas",Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }// end of  loadData()

    @Override
    public void onSupplierSelected(SupplierDAO supplierDAO) {
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
                ListSupplier.clear();
                ListSupplier.addAll(ListSupplierTemp);
                adapterSupplier.getFilter().filter(newText);
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
