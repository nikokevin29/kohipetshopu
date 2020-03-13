package com.p3l.kohipetshopu.JenisHewan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.R;
import com.p3l.kohipetshopu.UkuranHewan.UkuranHewanDAO;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewJenisHewan extends AppCompatActivity implements AdapterJenisHewan.JenisAdapterListener {

    private List<JenisHewanDAO> ListJenis, ListJenisTemp;
    AdapterJenisHewan adapterJenisHewan;
    private RecyclerView recyclerJenis;
    private FloatingActionButton add_jenis,sort_jenis,refresh_data_jenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_jenis_hewan);

        recyclerJenis = findViewById(R.id.recycler_view_jenis);

        ListJenis = new ArrayList<>();
        ListJenisTemp = new ArrayList<>();
        adapterJenisHewan = new AdapterJenisHewan(this, ListJenis,this);
        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        recyclerJenis.setLayoutManager(mLayoutmanager);
        recyclerJenis.setItemAnimator(new DefaultItemAnimator());
        recyclerJenis.setAdapter(adapterJenisHewan);
        initFloatingButton();
        loadData();
    }//end of onCreate

    public void initFloatingButton(){
        add_jenis = findViewById(R.id.add_jenis);
        add_jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(ViewJenisHewan.this,AddJenis.class);
                i.putExtra("from","AddJenis");
                startActivity(i);
            }
        });
        sort_jenis = findViewById(R.id.sort_jenis);
        sort_jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewJenisHewan.this, "Fungsi Belum dibuat", Toast.LENGTH_SHORT).show();
            }
        });
        refresh_data_jenis = findViewById(R.id.refresh_data_jenis);
        refresh_data_jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }// End of initFloatingButton
    public void loadData(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<JenisHewanDAO>> jenisDAOCall = apiService.getAllJenis();

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Fetching data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

        jenisDAOCall.enqueue(new Callback<List<JenisHewanDAO>>() {
            @Override
            public void onResponse(Call<List<JenisHewanDAO>> call,Response<List<JenisHewanDAO>> response) {
                //System.out.println(response.body().get(0).getNama());
                ListJenis.addAll(response.body());
                ListJenisTemp.addAll(response.body());
                adapterJenisHewan.notifyDataSetChanged();
                progress.dismiss();
                Toast.makeText(ViewJenisHewan.this, "Tekan yang Lama untuk Melakukan Aksi", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<JenisHewanDAO>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ViewJenisHewan.this, "Internet Anda Ampas",Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }//end of loaddata()
    @Override
    public void onJenisSelected(JenisHewanDAO jenisHewanDAO) {
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
                ListJenis.clear();
                ListJenis.addAll(ListJenisTemp);
                adapterJenisHewan.getFilter().filter(newText);
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
