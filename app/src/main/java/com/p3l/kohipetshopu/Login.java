package com.p3l.kohipetshopu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        ProgressDialog progress = new ProgressDialog(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponseBody> loginRequest = apiInterface.loginRequest(etUsername.getEditText().getText().toString(),etPassword.getEditText().getText().toString());

                progress.setMessage("Memproses data . . . ");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setCancelable(false);
                progress.show();

                loginRequest.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            progress.dismiss();
                                    Toast.makeText(Login.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                                    Intent i =  new Intent(Login.this,MainView.class);
                                    startActivity(i);
                                    finish();
                        }else{
                            progress.dismiss();
                            Toast.makeText(Login.this, "NANANA", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        Toast.makeText(Login.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                });
//                if(etUsername.getEditText().getText().toString().equalsIgnoreCase("a" ) &&
//                        etPassword.getEditText().getText().toString().equalsIgnoreCase("a" ) ){
//                    Intent i =  new Intent(Login.this,MainView.class);
//                    startActivity(i);
//                    finish();
//                }else if(etUsername.getEditText().getText().toString().equalsIgnoreCase("cs" )&&
//                        etPassword.getEditText().getText().toString().equalsIgnoreCase("cs" )){
//                    Intent i =  new Intent(Login.this,MainViewCS.class);
//                    startActivity(i);
//                    finish();
//                }else{
//                    Toast.makeText(Login.this, "Tidak Valid", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }


}
