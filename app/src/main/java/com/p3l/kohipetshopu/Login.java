package com.p3l.kohipetshopu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
    public static PegawaiDAO pegawaiDAO;
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

                loginRequest.enqueue(new Callback<PegawaiDAO>() {
                    @Override
                    public void onResponse(Call<PegawaiDAO> call, Response<PegawaiDAO> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            progress.dismiss();

                            String role = response.body().getRole().trim();
                            String nama = response.body().getNama();
                            System.out.println("Role : "+response.body().getRole()+
                                    "Nama : "+response.body().getNama()+
                                    "Username : "+response.body().getUsername());

                            if("Owner".equals(role)){
                                Toast.makeText(Login.this, "Halo "+role+" "+nama, Toast.LENGTH_SHORT).show();
                                Intent i =  new Intent(Login.this,MainView.class);
                                startActivity(i);
                                finish();
                            }else if("CS".equals(role)){
                                Toast.makeText(Login.this, "Halo "+role+" "+nama, Toast.LENGTH_SHORT).show();
                                Intent i =  new Intent(Login.this,MainViewCS.class);
                                startActivity(i);
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
                        Toast.makeText(Login.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                });
            }
        });
    }


}
