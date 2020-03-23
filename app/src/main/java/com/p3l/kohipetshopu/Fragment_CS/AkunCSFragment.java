package com.p3l.kohipetshopu.Fragment_CS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.p3l.kohipetshopu.R;

public class AkunCSFragment extends Fragment {
    TextView tvnama,tvusername,tvalamat,tvnotelp,tvtgllahir;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_akun_cs, container, false);

        tvnama = (TextView) view.findViewById(R.id.tvNamaCS);
        tvusername = (TextView) view.findViewById(R.id.tvUsernameCS);
        tvalamat = (TextView) view.findViewById(R.id.IsiAlamat);
        tvnotelp = (TextView) view.findViewById(R.id.isiNoHpCS);
        tvtgllahir = (TextView) view.findViewById(R.id.isiTglLahir);

        SharedPreferences mSettings = getActivity().getSharedPreferences("LoginCS", Context.MODE_PRIVATE);
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
