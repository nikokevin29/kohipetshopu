package com.p3l.kohipetshopu.Fragment_CS.Hewan_CRUDS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.DatePickerFragment;
import com.p3l.kohipetshopu.Fragment_CS.Customer_CRUDS.CustomerDAO;
import com.p3l.kohipetshopu.JenisHewan.JenisHewanDAO;
import com.p3l.kohipetshopu.R;
import com.p3l.kohipetshopu.UkuranHewan.UkuranHewanDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditHewan extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView tvEditTglLahir_hewan;
    Button pickDate,sumbit_edit;
    TextInputLayout nama;
    Spinner spinnerJenis,spinnerUkuran,spinnerCustomer;

    String selectedCustomer,selectedUkuran,selectedJenis;
    List<CustomerDAO> ListCustomer;
    List<JenisHewanDAO> ListJenisHewan;
    List<UkuranHewanDAO> ListUkuranHewan;
    ArrayAdapter<String> adapterCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_hewan);

        initCustomer(); initJenis(); initUkuran();
        spinnerCustomer = findViewById(R.id.spinnerPemilik);
        spinnerUkuran = findViewById(R.id.spinnerUkuranHewan);
        spinnerJenis = findViewById(R.id.spinnerJenisHewan);
        spinnerCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCustomer = parent.getItemAtPosition(position).toString();
                for(int count=0;count<ListCustomer.size();count++){
                    if(selectedCustomer.equalsIgnoreCase(ListCustomer.get(count).getNama())){
                        selectedCustomer = ListCustomer.get(count).getIdcustomer();
                        //Toast.makeText(EditHewan.this, "PIN "+selectedCustomer, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        spinnerUkuran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUkuran = parent.getItemAtPosition(position).toString();
                for(int count=0;count<ListUkuranHewan.size();count++){
                    if(selectedUkuran.equalsIgnoreCase(ListUkuranHewan.get(count).getNama())){
                        selectedUkuran = ListUkuranHewan.get(count).getIdukuran();
                        //Toast.makeText(EditHewan.this, "PIN "+selectedUkuran, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        spinnerJenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedJenis = parent.getItemAtPosition(position).toString();
                for(int count=0;count<ListJenisHewan.size();count++){
                    if(selectedJenis.equalsIgnoreCase(ListJenisHewan.get(count).getNama())){
                        selectedJenis = ListJenisHewan.get(count).getIdjenis();
                        //Toast.makeText(EditHewan.this, "PIN "+selectedJenis, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        nama = findViewById(R.id.edit_nama_hewan);
        tvEditTglLahir_hewan = findViewById(R.id.show_tgllahir_hewan);
        setField();
        pickDate = findViewById(R.id.edit_tgllahir_hewan);
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        ProgressDialog progress = new ProgressDialog(this);
        sumbit_edit = findViewById(R.id.btn_submit_edit_hewan);
        sumbit_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nama.getEditText().getText().length() == 0 ){
                    Toast.makeText(EditHewan.this, "Text Field Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences mSettings = getSharedPreferences("LoginCS", Context.MODE_PRIVATE);// SharedPreferences Ambil dari Login
                    String aktor = mSettings.getString("id","1");

                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<HewanDAO> callDAO = apiService.editHewan(
                            getIntent().getStringExtra("idhewan"),
                            nama.getEditText().getText().toString(),
                            tvEditTglLahir_hewan.getText().toString(),
                            selectedJenis,
                            selectedUkuran,
                            selectedCustomer,
                            aktor);
                    progress.setMessage("Memproses data . . . ");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setCancelable(false);
                    progress.show();
                    callDAO.enqueue(new Callback<HewanDAO>() {
                        @Override
                        public void onResponse(Call<HewanDAO> call, Response<HewanDAO> response) {
                            Toast.makeText(EditHewan.this, "Edit Gagal "+response.toString(), Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }
                        @Override
                        public void onFailure(Call<HewanDAO> call, Throwable t) {
                            Toast.makeText(EditHewan.this, "Edit Sukses", Toast.LENGTH_SHORT).show();
                            System.out.println(t.getMessage());
                            Intent i =  new Intent(EditHewan.this,ViewHewan.class);
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
        tvEditTglLahir_hewan.setText(strDate);
    }

    public void setField(){
        nama.getEditText().setText(getIntent().getStringExtra("nama"));
        tvEditTglLahir_hewan.setText(getIntent().getStringExtra("tgllahir"));
    }
    private void initCustomer(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<CustomerDAO>> callDAO = apiService.getAllCustomer();
        callDAO.enqueue(new Callback<List<CustomerDAO>>() {
            @Override
            public void onResponse(Call<List<CustomerDAO>> call, Response<List<CustomerDAO>> response) {
                if(response.isSuccessful()){
                    ListCustomer = response.body();
                    List<String> listSpinner = new ArrayList<>();
                    for(int i=0;i<ListCustomer.size();i++){
                        listSpinner.add(ListCustomer.get(i).getNama());
                    }
                    //masukin hasil ke spinner
                    adapterCustomer = new ArrayAdapter<>(EditHewan.this, android.R.layout.simple_spinner_item,listSpinner);
                    adapterCustomer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCustomer.setAdapter(adapterCustomer);
                }else{
                    Toast.makeText(EditHewan.this, "Gagal get Customer ke Spinner", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<CustomerDAO>> call, Throwable t) {
                Toast.makeText(EditHewan.this, "Koneksi Anda benar-benar Ampas", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initJenis(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<JenisHewanDAO>> callDAO = apiService.getAllJenis();
        callDAO.enqueue(new Callback<List<JenisHewanDAO>>() {
            @Override
            public void onResponse(Call<List<JenisHewanDAO>> call, Response<List<JenisHewanDAO>> response) {
                if(response.isSuccessful()){
                    ListJenisHewan = response.body();
                    List<String> listSpinner = new ArrayList<>();
                    for(int i=0;i<ListJenisHewan.size();i++){
                        listSpinner.add(ListJenisHewan.get(i).getNama());
                    }
                    //masukin hasil ke spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(EditHewan.this, android.R.layout.simple_spinner_item,listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerJenis.setAdapter(adapter);
                }else{
                    Toast.makeText(EditHewan.this, "Gagal get Jenis ke Spinner", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<JenisHewanDAO>> call, Throwable t) {
                Toast.makeText(EditHewan.this, "Koneksi Anda benar-benar Ampas", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initUkuran(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<UkuranHewanDAO>> callDAO = apiService.getAllUkuran();
        callDAO.enqueue(new Callback<List<UkuranHewanDAO>>() {
            @Override
            public void onResponse(Call<List<UkuranHewanDAO>> call, Response<List<UkuranHewanDAO>> response) {
                if(response.isSuccessful()){
                    ListUkuranHewan = response.body();
                    List<String> listSpinner = new ArrayList<>();
                    for(int i=0;i<ListUkuranHewan.size();i++){
                        listSpinner.add(ListUkuranHewan.get(i).getNama());
                    }
                    //masukin hasil ke spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(EditHewan.this, android.R.layout.simple_spinner_item,listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerUkuran.setAdapter(adapter);
                }else{
                    Toast.makeText(EditHewan.this, "Gagal get Jenis ke Spinner", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<UkuranHewanDAO>> call, Throwable t) {
                Toast.makeText(EditHewan.this, "Koneksi Anda benar-benar Ampas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
