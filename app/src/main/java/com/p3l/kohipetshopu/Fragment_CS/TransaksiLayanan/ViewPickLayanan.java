package com.p3l.kohipetshopu.Fragment_CS.TransaksiLayanan;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.Layanan.LayananDAO;
import com.p3l.kohipetshopu.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPickLayanan extends AppCompatActivity {

    private List<LayananDAO> ListLayanan, ListLayananTemp;
    adapterPickLayanan adapterPickLayanan;
    private RecyclerView recycler;
    TextView subtotal_display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pick_produk);
        recycler = findViewById(R.id.recycler_view_pick_produk);
        subtotal_display = findViewById(R.id.subtotaldisplay);
        update_subtotalPicker();
        ListLayanan = new ArrayList<>();
        ListLayananTemp = new ArrayList();
        adapterPickLayanan = new adapterPickLayanan(this, ListLayanan);
        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(mLayoutmanager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(adapterPickLayanan);
        loadData();
    }//End Of On Create

    public void update_subtotalPicker(){
        double tempsubtotal = 0;
        for(int i = 0; i< com.p3l.kohipetshopu.Fragment_CS.TransaksiLayanan.adapterPickLayanan.tempLayanan.size(); i++){
            tempsubtotal = tempsubtotal + Double.parseDouble(com.p3l.kohipetshopu.Fragment_CS.TransaksiLayanan.adapterPickLayanan.tempLayanan.get(i).subtotal);
        }
        subtotal_display.setText(String.valueOf(tempsubtotal));
        System.out.println("update_subtotalPicker : "+tempsubtotal);
    }

    public void loadData() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<LayananDAO>> DAOCall = apiService.getAllLayanan();

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Fetching data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

        DAOCall.enqueue(new Callback<List<LayananDAO>>() {
            @Override
            public void onResponse(Call<List<LayananDAO>> call, Response<List<LayananDAO>> response) {
                for(int i=0;i<response.body().size();i++){
                    ListLayanan.add(response.body().get(i));
                    ListLayananTemp.add(response.body().get(i));
                    adapterPickLayanan.notifyDataSetChanged();
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<List<LayananDAO>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ViewPickLayanan.this, "Ulangi lagi, koneksi bermasalah", Toast.LENGTH_SHORT).show();
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
                ListLayanan.clear();
                ListLayanan.addAll(ListLayananTemp);
                adapterPickLayanan.getFilter().filter(newText);
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
