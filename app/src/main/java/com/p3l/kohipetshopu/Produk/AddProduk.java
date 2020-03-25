package com.p3l.kohipetshopu.Produk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.R;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.p3l.kohipetshopu.API.ApiClient.BASE_URL;

public class AddProduk extends AppCompatActivity {

    EditText namaProduk,Harga,stok,stokmin;
    Button btn_Submit_add_produk,btngambar;
    ImageView preview_produk;
    Spinner spinnerSupplier;

    final int galleryCode = 100;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produk);
        namaProduk = (EditText) findViewById(R.id.etNamaProduk);
        Harga = findViewById(R.id.etHargaProduk);
        stok = findViewById(R.id.etStokProduk);
        stokmin = findViewById(R.id.etStokMinimumProduk);
        preview_produk = (ImageView) findViewById(R.id.preview_produk);
        btngambar = findViewById(R.id.btn_add_gambar);
        btn_Submit_add_produk = (Button) findViewById(R.id.btn_Submit_add_produk);

        ProgressDialog progress = new ProgressDialog(this);
        btn_Submit_add_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(namaProduk.getText().length() == 0 || Harga.getText().length() == 0 ||  stok.getText().length() == 0  ||  stokmin.getText().length() == 0 ){
                    Toast.makeText(AddProduk.this, "Masih Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<List<ProdukDAO>> produkDAOCall = apiService.getAllProduk();

                    progress.setMessage("Memproses data . . . ");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setCancelable(false);
                    progress.show();

                    produkDAOCall.enqueue(new Callback<List<ProdukDAO>>() {
                        @Override
                        public void onResponse(Call<List<ProdukDAO>> call, Response<List<ProdukDAO>> response) {
                            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                            Call<ProdukDAO> ProdukDAO = apiService.createProduk(
                                    namaProduk.getText().toString(),
                                    Harga.getText().toString(),
                                    stok.getText().toString(),
                                    stokmin.getText().toString(),
                                    imageUri.toString());
                            ProdukDAO.enqueue(new Callback<ProdukDAO>() {
                                @Override
                                public void onResponse(Call<ProdukDAO> call, Response<ProdukDAO> response) {
                                    Toast.makeText(AddProduk.this, "Sukses Tambah", Toast.LENGTH_SHORT).show();
                                    progress.dismiss();
                                }
                                @Override
                                public void onFailure(Call<ProdukDAO> call, Throwable t) {
                                    Toast.makeText(AddProduk.this, "Sukses Tambah.", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(AddProduk.this, ViewProduk.class);
                                    i.putExtra("from","produk");
                                    System.out.println("PESAN ERROR"+t.getMessage());
                                    progress.dismiss();
                                    startActivity(i);
                                    finish();
                                }
                            });
                        }
                        @Override
                        public void onFailure(Call<List<ProdukDAO>> call, Throwable t) {
                            System.out.println("gagal");
                            Toast.makeText(AddProduk.this, "Gagal dari produkDAOCall", Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }
                    });
                }

            }
        });
        btngambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGaler();
            }
        });
    }
    public void openGaler(){
        Intent galer = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galer, galleryCode);
    }
    public void uploadGambar(String url_produk){

        ProdukDAO produk = new ProdukDAO();
        url_produk = BASE_URL+"/uploads/produk/"+produk.getGambar();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == galleryCode){
            imageUri = data.getData();
            try {
                Bitmap bitmapImg = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                preview_produk.setImageBitmap(bitmapImg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
