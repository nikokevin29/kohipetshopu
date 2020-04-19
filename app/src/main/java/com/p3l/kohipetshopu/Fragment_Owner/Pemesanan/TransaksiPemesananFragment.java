package com.p3l.kohipetshopu.Fragment_Owner.Pemesanan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.Fragment_CS.TransaksiProduk.ViewPickProduk;
import com.p3l.kohipetshopu.R;
import com.p3l.kohipetshopu.Supplier.SupplierDAO;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiPemesananFragment extends Fragment {
    Spinner spinnerSupplier;
    private Button tambahPesananBarang;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaksi_pemesanan, container, false);

        spinnerSupplier = view.findViewById(R.id.SpinSupplier);
        tambahPesananBarang =  view.findViewById(R.id.tambah_pesanan_barang);

        initSupplier();

        spinnerSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//test toast kalo selected
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getActivity(), "Anda Memilih Supplier "+selected, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        tambahPesananBarang.setOnClickListener(new View.OnClickListener() {// Masuk ke RecycleView Pemilihan Barang untuk Pemesanan
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ViewPickProduk.class);
                startActivity(i);
            }
        });

        return view;
    }

    private void initSupplier(){
        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Fetching Supplier ke Spinner");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<SupplierDAO>> supplierDAOCall = apiService.getAllSupplier();
        supplierDAOCall.enqueue(new Callback<List<SupplierDAO>>() {
            @Override
            public void onResponse(Call<List<SupplierDAO>> call, Response<List<SupplierDAO>> response) {
                if(response.isSuccessful()){
                    progress.dismiss();
                    List<SupplierDAO> ListSupplier = response.body();
                    List<String> listSpinner = new ArrayList<>();
                    for(int i=0;i<ListSupplier.size();i++){
                        listSpinner.add(ListSupplier.get(i).getNama());
                    }
                    //masukin hasil ke spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSupplier.setAdapter(adapter);
                }else{
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Gagal get Supplier ke Spinner", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<SupplierDAO>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(getActivity(), "Koneksi Anda benar-benar Ampas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
