package com.p3l.kohipetshopu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.Fragment_Owner.AkunFragment;
import com.p3l.kohipetshopu.Fragment_Owner.KelolaFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

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
                Call<PegawaiDAO> loginRequest = apiInterface.loginRequest(
                        etUsername.getEditText().getText().toString(),
                        etPassword.getEditText().getText().toString());

                progress.setMessage("Memproses data . . . ");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setCancelable(false);
                progress.show();

                SharedPreferences mSettings = getApplication().getSharedPreferences("Login", Context.MODE_PRIVATE);
                SharedPreferences mCS = getApplication().getSharedPreferences("LoginCS", Context.MODE_PRIVATE);
                loginRequest.enqueue(new Callback<PegawaiDAO>() {
                    @Override
                    public void onResponse(Call<PegawaiDAO> call, Response<PegawaiDAO> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            progress.dismiss();
                            String id = response.body().getIdpegawai().trim();
                            String role = response.body().getRole().trim();
                            String nama = response.body().getNama();
                            String username = response.body().getUsername();
                            String alamat = response.body().getAlamat();
                            String tgllahir = response.body().getTgllahir();
                            String noTelp = response.body().getNoTelp();
                            System.out.println("Role : "+response.body().getRole()+
                                    "Nama : "+response.body().getNama()+
                                    "Username : "+response.body().getUsername()+
                                    "alamat : "+response.body().getAlamat()+
                                    "tgllahir : "+response.body().getTgllahir()+
                                    "notelp : "+response.body().getNoTelp()
                            );

                            if("Owner".equals(role)){
                                Toast.makeText(Login.this, "Halo "+role+" "+nama, Toast.LENGTH_SHORT).show();
                                Intent i =  new Intent(Login.this,MainView.class);

                                SharedPreferences.Editor editor = mSettings.edit();
                                editor.putString("id",id);
                                editor.putString("nama",nama);
                                editor.putString("username",username);
                                editor.putString("role",role);
                                editor.putString("alamat",alamat);
                                editor.putString("tgllahir",tgllahir);
                                editor.putString("noTelp",noTelp);
                                editor.apply();

                                startActivity(i);
                                finish();
                            }else if("CS".equals(role)){
                                Toast.makeText(Login.this, "Halo "+role+" "+nama, Toast.LENGTH_SHORT).show();
                                Intent i =  new Intent(Login.this,MainViewCS.class);
                                startActivity(i);

                                SharedPreferences.Editor editor = mCS.edit();
                                editor.putString("id",id);
                                editor.putString("nama",nama);
                                editor.putString("username",username);
                                editor.putString("role",role);
                                editor.putString("alamat",alamat);
                                editor.putString("tgllahir",tgllahir);
                                editor.putString("noTelp",noTelp);
                                editor.apply();

                                finish();
                            }else{
                                Toast.makeText(Login.this, "Anda Bukan CS Maupun Owner", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            progress.dismiss();
                            Toast.makeText(Login.this, "Username atau Password Salah!", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<PegawaiDAO> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        System.out.println("GG"+t.getMessage());
                        Toast.makeText(Login.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                });
            }
        });
    }


}
