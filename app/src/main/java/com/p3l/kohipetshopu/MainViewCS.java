package com.p3l.kohipetshopu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.p3l.kohipetshopu.Fragment_CS.AkunCSFragment;
import com.p3l.kohipetshopu.Fragment_CS.TransaksiFragment;

public class MainViewCS extends AppCompatActivity {
    private boolean doubleTapParam;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_cs);

        bottomNavigationView = findViewById(R.id.navigation_view_cs);
        loadFragment(new TransaksiFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.menu_transaksi_penjualan:
                        fragment = new TransaksiFragment();
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
    @Override
    public void onBackPressed() { //2x Tap Untuk Keluar
        if (doubleTapParam) {
            super.onBackPressed();
            return ;
        }
        this.doubleTapParam = true;
        Toast.makeText(this, "Tap sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleTapParam = false;
            }
        }, 2000);
    }
}
