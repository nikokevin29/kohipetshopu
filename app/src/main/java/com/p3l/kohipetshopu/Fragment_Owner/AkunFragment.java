package com.p3l.kohipetshopu.Fragment_Owner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.p3l.kohipetshopu.R;

import org.w3c.dom.Text;

public class AkunFragment extends Fragment {
    TextView tvnama,tvusername,tvalamat,tvnotelp,tvtgllahir;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_akun, container, false);

        tvnama = (TextView) view.findViewById(R.id.tvNamaOwner);
        tvusername = (TextView) view.findViewById(R.id.tvUsernameOwner);
        tvalamat = (TextView) view.findViewById(R.id.IsiAlamat);
        tvnotelp = (TextView) view.findViewById(R.id.isiNoHpOwner);
        tvtgllahir = (TextView) view.findViewById(R.id.isiTglLahir);

        SharedPreferences mSettings = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String nama = mSettings.getString("nama","missing");
        String username = mSettings.getString("username","missing");
        String alamat =  mSettings.getString("alamat","missing");
        String notelp = mSettings.getString("noTelp","missing");
        String tgllahir = mSettings.getString("tgllahir","missing");

        tvnama.setText(nama);
        tvusername.setText("@"+username);
        tvalamat.setText(alamat);
        tvnotelp.setText(notelp);
        tvtgllahir.setText(tgllahir);

        return view;
    }
}
