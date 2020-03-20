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

import com.p3l.kohipetshopu.JenisHewan.ViewJenisHewan;
import com.p3l.kohipetshopu.Layanan.ViewLayanan;
import com.p3l.kohipetshopu.Produk.ViewProduk;
import com.p3l.kohipetshopu.R;
import com.p3l.kohipetshopu.Supplier.ViewSupplier;
import com.p3l.kohipetshopu.UkuranHewan.ViewUkuranHewan;

public class KelolaFragment extends Fragment {
    Button jenis;
    Button ukuran;
    Button layanan;
    Button produk;
    Button supplier;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kelola, container, false);
        setButton(view);
        
        return view;
    }

    public void setButton(View view){

        jenis = (Button) view.findViewById(R.id.kelola_jenishewan);
        jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ViewJenisHewan.class);
                startActivity(i);
            }
        });

        ukuran = (Button) view.findViewById(R.id.kelola_ukuranhewan);
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
    }
}
