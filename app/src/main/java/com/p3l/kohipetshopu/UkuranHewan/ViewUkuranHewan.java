package com.p3l.kohipetshopu.UkuranHewan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.p3l.kohipetshopu.API.*;
import com.p3l.kohipetshopu.JenisHewan.AddJenis;
import com.p3l.kohipetshopu.JenisHewan.JenisHewanDAO;
import com.p3l.kohipetshopu.UkuranHewan.*;
import com.p3l.kohipetshopu.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.*;

public class ViewUkuranHewan extends AppCompatActivity implements AdapterUkuranHewan.UkuranAdapterListener {

    private List<UkuranHewanDAO> ListUkuran, ListUkuranTemp;
    AdapterUkuranHewan adapterUkuranHewan;
    private RecyclerView recyclerUkuran;
    private FloatingActionButton add_ukuran,sort_ukuran,refresh_data_ukuran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_ukuran_hewan);
        recyclerUkuran = findViewById(R.id.recycler_view_ukuran);

        ListUkuran = new ArrayList<>();
        ListUkuranTemp = new ArrayList();
        adapterUkuranHewan = new AdapterUkuranHewan(this, ListUkuran,this);
        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        recyclerUkuran.setLayoutManager(mLayoutmanager);
        recyclerUkuran.setItemAnimator(new DefaultItemAnimator());
        recyclerUkuran.setAdapter(adapterUkuranHewan);

        initFloatingButton();
        loadData();

    }//End Of On Create

    public void initFloatingButton(){
        add_ukuran = findViewById(R.id.add_ukuran);
        add_ukuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(ViewUkuranHewan.this, AddUkuran.class);
                i.putExtra("from","AddUkuran");
                startActivity(i);
            }
        });
        sort_ukuran = findViewById(R.id.sort_ukuran);
        sort_ukuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewUkuranHewan.this, "Fungsi Belum dibuat", Toast.LENGTH_SHORT).show();
            }
        });
        refresh_data_ukuran = findViewById(R.id.refresh_data_ukuran);
        refresh_data_ukuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                ListUkuran.clear();
                ListUkuranTemp.clear();
            }
        });
    }//End of  initFloatingButton()

    public void loadData(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<UkuranHewanDAO>> ukuranDAOCall = apiService.getAllUkuran();

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Fetching data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

        ukuranDAOCall.enqueue(new Callback<List<UkuranHewanDAO>>() {
            @Override
            public void onResponse(Call<List<UkuranHewanDAO>> call, Response<List<UkuranHewanDAO>> response) {
                ListUkuran.addAll(response.body());
                ListUkuranTemp.addAll(response.body());
                adapterUkuranHewan.notifyDataSetChanged();
                progress.dismiss();
                Toast.makeText(ViewUkuranHewan.this, "Tekan yang Lama untuk Melakukan Aksi", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<UkuranHewanDAO>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ViewUkuranHewan.this, "Internet Anda Ampas",Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }// end of  loadData()

    @Override
    public void onUkuranSelected(UkuranHewanDAO ukuranHewanDAO) {
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
                ListUkuran.clear();
                ListUkuran.addAll(ListUkuranTemp);
                adapterUkuranHewan.getFilter().filter(newText);
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
