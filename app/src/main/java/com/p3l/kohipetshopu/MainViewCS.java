package com.p3l.kohipetshopu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.p3l.kohipetshopu.Fragment_CS.AkunCSFragment;
import com.p3l.kohipetshopu.Fragment_CS.TransaksiPenjualanFragment;

public class MainViewCS extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_cs);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_view_cs);
        loadFragment(new TransaksiPenjualanFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.menu_transaksi_penjualan:
                        fragment = new TransaksiPenjualanFragment();
                        break;
                    case R.id.menu_akun_cs:
                        fragment = new AkunCSFragment();
                        break;
                }
                return loadFragment(fragment);
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container_cs, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
