package com.p3l.kohipetshopu.Fragment_Owner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.p3l.kohipetshopu.R;

public class AkunFragment extends Fragment {
    TextView nama,username;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_akun, container, false);

        nama = (TextView) view.findViewById(R.id.tvNamaOwner);
        username = (TextView) view.findViewById(R.id.tvUsernameOwner);




        return view;
    }
}
