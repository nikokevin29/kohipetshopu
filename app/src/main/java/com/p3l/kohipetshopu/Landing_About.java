package com.p3l.kohipetshopu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Landing_About extends AppCompatActivity {
    Button btn_resume_login,btn_pricelist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_about);
        btn_resume_login = findViewById(R.id.btn_login_landing);
        btn_pricelist = findViewById(R.id.pricelist_landing);

        btn_resume_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(Landing_About.this, Login.class);
                startActivity(i);
            }
        });
        btn_pricelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(Landing_About.this,PriceList.class);
                startActivity(i);
            }
        });
    }
}
