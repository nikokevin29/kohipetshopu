package com.p3l.kohipetshopu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {
    private TextInputLayout etUsername,etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.username_text_input);
        etPassword = findViewById(R.id.password_text_input);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etUsername.getEditText().getText().toString().equalsIgnoreCase("a" ) &&
                        etPassword.getEditText().getText().toString().equalsIgnoreCase("a" ) ){
                    Intent i =  new Intent(Login.this,MainView.class);
                    startActivity(i);
                    finish();
                }else if(etUsername.getEditText().getText().toString().equalsIgnoreCase("cs" )&&
                        etPassword.getEditText().getText().toString().equalsIgnoreCase("cs" )){
                    Intent i =  new Intent(Login.this,MainViewCS.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(Login.this, "Tidak Valid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
