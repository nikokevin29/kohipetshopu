package com.p3l.kohipetshopu.Fragment_Owner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.p3l.kohipetshopu.Fragment_CS.Customer_CRUDS.CreateCustomer;
import com.p3l.kohipetshopu.Fragment_CS.Customer_CRUDS.ViewCustomer;
import com.p3l.kohipetshopu.Fragment_CS.Hewan_CRUDS.CreateHewan;
import com.p3l.kohipetshopu.Fragment_CS.Hewan_CRUDS.ViewHewan;
import com.p3l.kohipetshopu.JenisHewan.ViewJenisHewan;
import com.p3l.kohipetshopu.Layanan.ViewLayanan;
import com.p3l.kohipetshopu.Produk.ViewProduk;
import com.p3l.kohipetshopu.R;
import com.p3l.kohipetshopu.Supplier.ViewSupplier;
import com.p3l.kohipetshopu.UkuranHewan.ViewUkuranHewan;

public class KelolaFragment extends Fragment {
    ImageView jenis,ukuran,layanan,produk,supplier;
    ImageView customer,hewan;
    Button create_customer,create_hewan;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kelola, container, false);
        setImageButton(view);
        
        return view;
    }
    public void setImageButton(View view){
        jenis = view.findViewById(R.id.kelola_jenishewan);
        jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ViewJenisHewan.class);
                startActivity(i);
            }
        });

        ukuran = view.findViewById(R.id.kelola_ukuranhewan);
        ukuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ViewUkuranHewan.class);
                startActivity(i);
            }
        });
        layanan = view.findViewById(R.id.kelola_layanan);
        layanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ViewLayanan.class);
                startActivity(i);
            }
        });
        produk = view.findViewById(R.id.kelola_produk);
        produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ViewProduk.class);
                startActivity(i);
            }
        });
        supplier = view.findViewById(R.id.kelola_supplier);
        supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ViewSupplier.class);
                startActivity(i);
            }
        });
        hewan = view.findViewById(R.id.kelola_hewan);
        hewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  =  new Intent(getActivity(), ViewHewan.class);
                startActivity(i);
            }
        });
        customer = view.findViewById(R.id.kelola_customer);
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ViewCustomer.class);
                startActivity(i);
            }
        });
        create_customer = view.findViewById(R.id.create_customer_owner);
        create_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CreateCustomer.class);
                startActivity(i);
            }
        });
        create_hewan = view.findViewById(R.id.create_hewan_owner);
        create_hewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CreateHewan.class);
                startActivity(i);
            }
        });
    }

}
