package com.p3l.kohipetshopu.Fragment_Owner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.p3l.kohipetshopu.JenisHewan.ViewJenisHewan;
import com.p3l.kohipetshopu.R;

public class KelolaFragment extends Fragment {
    Button jenis;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kelola, container, false);

        jenis = (Button) view.findViewById(R.id.kelola_jenishewan);

        jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addRep = new Intent(getActivity(), ViewJenisHewan.class);
                startActivity(addRep);
            }
        });

        return view;
    }
}
