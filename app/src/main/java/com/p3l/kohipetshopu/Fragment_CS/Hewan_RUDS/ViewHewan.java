package com.p3l.kohipetshopu.Fragment_CS.Hewan_RUDS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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

public class ViewHewan extends AppCompatActivity implements AdapterHewan.HewanAdapterListener {

    private List<HewanDAO> ListHewan, ListHewanTemp;
    AdapterHewan adapterHewan;
    private RecyclerView recycle;
    FloatingActionButton refresh_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_hewan);
        recycle = findViewById(R.id.recycler_view_hewan);
        ListHewan = new ArrayList<>();
        ListHewanTemp = new ArrayList<>();
        adapterHewan = new AdapterHewan(this, ListHewan,this);
        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        recycle.setLayoutManager(mLayoutmanager);
        recycle.setItemAnimator(new DefaultItemAnimator());
        recycle.setAdapter(adapterHewan);
        initFloatingButton();
        loadData();
    }//end of onCreate

    public void initFloatingButton(){
        refresh_data = findViewById(R.id.refresh_data);
        refresh_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }// End of initFloatingButton

    public void loadData(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<HewanDAO>> callDAO = apiService.getAllHewan();
        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Fetching data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();
        callDAO.enqueue(new Callback<List<HewanDAO>>() {
            @Override
            public void onResponse(Call<List<HewanDAO>> call, Response<List<HewanDAO>> response) {
                ListHewan.addAll(response.body());
                ListHewanTemp.addAll(response.body());
                adapterHewan.notifyDataSetChanged();
                progress.dismiss();
                Toast.makeText(ViewHewan.this, "Tekan yang Lama untuk Melakukan Aksi", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<HewanDAO>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ViewHewan.this, "Internet Anda Ampas",Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }//end of loaddata()

    @Override
    public void onHewanSelected(HewanDAO hewanDAO) {
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
                ListHewan.clear();
                ListHewan.addAll(ListHewanTemp);
                adapterHewan.getFilter().filter(newText);
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
