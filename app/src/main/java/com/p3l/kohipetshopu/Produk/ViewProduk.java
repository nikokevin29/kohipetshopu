package com.p3l.kohipetshopu.Produk;

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
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.RemoteMessage;
import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.PriceList;
import com.p3l.kohipetshopu.R;
import com.pusher.pushnotifications.PushNotificationReceivedListener;
import com.pusher.pushnotifications.PushNotifications;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProduk extends AppCompatActivity implements AdapterProduk.ProdukAdapterListener,PopupMenu.OnMenuItemClickListener {

    private List<ProdukDAO> ListProduk, ListProdukTemp;
    AdapterProduk adapterProduk;
    private RecyclerView recyclerProduk;
    private FloatingActionButton add_produk,sort_produk,refresh_data_produk;
    boolean state = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_produk);
        recyclerProduk = findViewById(R.id.recycler_view_produk);

        ListProduk = new ArrayList<>();
        ListProdukTemp = new ArrayList();
        adapterProduk = new AdapterProduk(this, ListProduk,this);
        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        recyclerProduk.setLayoutManager(mLayoutmanager);
        recyclerProduk.setItemAnimator(new DefaultItemAnimator());
        recyclerProduk.setAdapter(adapterProduk);

        initFloatingButton();
        loadData();

    }//End Of On Create
    public void popupmenu(View v){
        PopupMenu pop = new PopupMenu(this,v);
        pop.setOnMenuItemClickListener(this);
        pop.inflate(R.menu.popup_menu);
        pop.show();
    }
    public boolean onMenuItemClick(MenuItem item){
        switch (item.getItemId()){
            case R.id.harga://Sort By Harga
                if(state){
                    Collections.sort(ListProduk, new Comparator<ProdukDAO>() {
                        @Override
                        public int compare(ProdukDAO o1, ProdukDAO o2) {
                            return Integer.parseInt(o1.getHarga()) - Integer.parseInt(o2.getHarga());
                        }
                    });
                    state=false;
                }else{
                    Collections.sort(ListProduk, new Comparator<ProdukDAO>() {
                        @Override
                        public int compare(ProdukDAO o1, ProdukDAO o2) {

                            return Integer.parseInt(o2.getHarga()) - Integer.parseInt(o1.getHarga());
                        }
                    });
                    state=true;
                }
                adapterProduk.notifyDataSetChanged();
                Toast.makeText(ViewProduk.this, "Sortir Harga", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.stok://Sort By Stok
                if(state){
                    Collections.sort(ListProduk, new Comparator<ProdukDAO>() {
                        @Override
                        public int compare(ProdukDAO o1, ProdukDAO o2) {
                            return Integer.parseInt(o1.getStok()) - Integer.parseInt(o2.getStok());
                        }
                    });
                    state=false;
                }else{
                    Collections.sort(ListProduk, new Comparator<ProdukDAO>() {
                        @Override
                        public int compare(ProdukDAO o1, ProdukDAO o2) {
                            return Integer.parseInt(o2.getStok()) - Integer.parseInt(o1.getStok());
                        }
                    });
                    state=true;
                }
                adapterProduk.notifyDataSetChanged();
                Toast.makeText(ViewProduk.this, "Sortir Stok", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.abjad://Sort By Alfabet
                if(state){
                    Collections.sort(ListProduk, new Comparator<ProdukDAO>() {
                        @Override
                        public int compare(ProdukDAO o1, ProdukDAO o2) {
                            return o1.getNama().compareToIgnoreCase(o2.getNama());
                        }
                    });
                    state=false;
                }else{
                    Collections.sort(ListProduk, new Comparator<ProdukDAO>() {
                        @Override
                        public int compare(ProdukDAO o1, ProdukDAO o2) {
                            return o2.getNama().compareToIgnoreCase(o1.getNama());
                        }
                    });
                    state=true;
                }
                adapterProduk.notifyDataSetChanged();
                Toast.makeText(ViewProduk.this, "Sortir Alfabet", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
    public void initFloatingButton(){
        add_produk = findViewById(R.id.add_produk);
        add_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(ViewProduk.this, AddProduk.class);
                i.putExtra("from","AddProduk");
                startActivity(i);
            }
        });
        sort_produk = findViewById(R.id.sort_produk);
        sort_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupmenu(v);
            }
        });
        refresh_data_produk = findViewById(R.id.refresh_data_produk);
        refresh_data_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                ListProduk.clear();
                ListProdukTemp.clear();
            }
        });
    }//End of  initFloatingButton()

    public void loadData(){
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
                Toast.makeText(ViewProduk.this, "Tekan yang Lama untuk Melakukan Aksi", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<ProdukDAO>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ViewProduk.this, "Internet Anda Ampas",Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }// end of  loadData()

    @Override
    public void onProdukSelected(ProdukDAO produkDAO) {
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
    protected void onResume() {
        super.onResume();
        PushNotifications.setOnMessageReceivedListenerForVisibleActivity(this, new PushNotificationReceivedListener() {
            @Override
            public void onMessageReceived(RemoteMessage remoteMessage) {
                // do something magical 🔮
                Toast.makeText(ViewProduk.this, "Notif Masuk Harusnya", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
