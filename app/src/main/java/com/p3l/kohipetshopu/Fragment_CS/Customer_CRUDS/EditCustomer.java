package com.p3l.kohipetshopu.Fragment_CS.Customer_CRUDS;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.DatePickerFragment;
import com.p3l.kohipetshopu.R;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCustomer extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView tvEditTglLahir_customer;
    Button pickDate,sumbit_edit;
    TextInputLayout nama,notelp,alamat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_customer);

        nama = findViewById(R.id.edit_nama_customer);
        notelp = findViewById(R.id.edit_notelp_customer);
        alamat = findViewById(R.id.edit_alamat_customer);
        tvEditTglLahir_customer = findViewById(R.id.show_tgllahir_customer);
        setField();
        pickDate = findViewById(R.id.edit_tgllahir_customer);
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        ProgressDialog progress = new ProgressDialog(this);
        sumbit_edit = findViewById(R.id.btn_submit_edit_customer);
        sumbit_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nama.getEditText().getText().length() == 0  || notelp.getEditText().getText().length() == 0 || alamat.getEditText().getText().length() == 0 ){
                    Toast.makeText(EditCustomer.this, "Text Field Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences mSettings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                    String aktor = mSettings.getString("id","2");

                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<CustomerDAO> callDAO = apiService.editCustomer(
                            getIntent().getStringExtra("idcustomer"),
                            nama.getEditText().getText().toString(),
                            notelp.getEditText().getText().toString(),
                            alamat.getEditText().getText().toString(),
                            tvEditTglLahir_customer.getText().toString(),
                            aktor);
                    progress.setMessage("Memproses data . . . ");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setCancelable(false);
                    progress.show();
                    callDAO.enqueue(new Callback<CustomerDAO>() {
                        @Override
                        public void onResponse(Call<CustomerDAO> call, Response<CustomerDAO> response) {
                            Toast.makeText(EditCustomer.this, "Edit Gagal "+response.toString(), Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }
                        @Override
                        public void onFailure(Call<CustomerDAO> call, Throwable t) {
                            Toast.makeText(EditCustomer.this, "Edit Sukses", Toast.LENGTH_SHORT).show();
                            System.out.println(t.getMessage());
                            Intent i =  new Intent(EditCustomer.this,ViewCustomer.class);
                            startActivity(i);
                            finish();
                            progress.dismiss();
                        }
                    });
                }
            }
        });

    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(c.getTime());
        tvEditTglLahir_customer.setText(strDate);
    }

    public void setField(){
        nama.getEditText().setText(getIntent().getStringExtra("nama"));
        notelp.getEditText().setText(getIntent().getStringExtra("notelp"));
        alamat.getEditText().setText(getIntent().getStringExtra("alamat"));
        tvEditTglLahir_customer.setText(getIntent().getStringExtra("tgllahir"));
        Toast.makeText(this, tvEditTglLahir_customer.getText().toString(), Toast.LENGTH_SHORT).show();
    }

}
