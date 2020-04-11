package com.p3l.kohipetshopu.Fragment_CS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.p3l.kohipetshopu.Fragment_CS.Customer_RUDS.ViewCustomer;
import com.p3l.kohipetshopu.Fragment_CS.Hewan_RUDS.ViewHewan;
import com.p3l.kohipetshopu.R;

public class TransaksiPenjualanFragment extends Fragment {
    private Button kelola_customer,kelola_hewan;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaksi_penjualan, container, false);
        kelola_customer = view.findViewById(R.id.kelola_customer);
        kelola_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ViewCustomer.class);
                startActivity(i);
            }
        });
        kelola_hewan = view.findViewById(R.id.kelola_hewan);
        kelola_hewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(getActivity(), ViewHewan.class);
                startActivity(i);
            }
        });
        return view;
    }
}
