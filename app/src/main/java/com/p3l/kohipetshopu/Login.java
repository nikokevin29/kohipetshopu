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
                if(etUsername.getEditText().getText().toString().equalsIgnoreCase("admin")){
                    Intent i =  new Intent(Login.this,MainView.class);
                    startActivity(i);
                    finish();
                }else if(etUsername.getEditText().getText().toString().equalsIgnoreCase("cs")){
                    Intent i =  new Intent(Login.this,MainViewCS.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(Login.this, "見つかりませんでした", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(MainActivity.this, "Something Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateUsername() {
        String usernameInput = etUsername.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            etUsername.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            etUsername.setError("Username too long, Max is 15");
            return false;
        } else {
            etUsername.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = etPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            etPassword.setError("Field can't be empty");
            return false;
        } else {
            etPassword.setError(null);
            return true;
        }
    }
    public void confirmInput(View v) {
        if (!validateUsername() | !validatePassword()) {
            return;
        }
        String input = "Username: " + etUsername.getEditText().getText().toString();
        input += "\n";
        input += "Password: " + etPassword.getEditText().getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }
}
