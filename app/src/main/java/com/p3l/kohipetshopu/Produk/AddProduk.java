package com.p3l.kohipetshopu.Produk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
                int cekstok = Integer.parseInt(stok.getText().toString());
                int cekstokmin = Integer.parseInt(stokmin.getText().toString());
                if(namaProduk.getText().length() == 0 || Harga.getText().length() == 0 ||  stok.getText().length() == 0  ||  stokmin.getText().length() == 0 ){
                    Toast.makeText(AddProduk.this, "Masih Kosong", Toast.LENGTH_SHORT).show();
                }
                else if(cekstokmin > cekstok ){
                    Toast.makeText(AddProduk.this, "Stok Minimum Tidak Boleh lebih kecil dari jumlah stok", Toast.LENGTH_SHORT).show();
                }
                else{
                    progress.setMessage("Memproses data . . . ");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setCancelable(false);
                    progress.show();
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                    File file = new File(getRealPathFromURI(imageUri));

                    Toast.makeText(AddProduk.this, file.getName(), Toast.LENGTH_SHORT).show();
                    HashMap<String, RequestBody> map = new HashMap<>();
                    map.put("nama", createPartFromString(namaProduk.getText().toString()));
                    map.put("harga", createPartFromString(Harga.getText().toString()));
                    map.put("stok", createPartFromString(stok.getText().toString()));
                    map.put("stokminimum", createPartFromString(stokmin.getText().toString()));
                     RequestBody GAMBAR = RequestBody.create(MediaType.parse("image/jpeg"), file);
                    MultipartBody.Part fileGambar = MultipartBody.Part.createFormData("gambar", file.getName(), RequestBody.create(MediaType.parse("image/jpeg"), file));

                    Call<ResponseBody> calls = apiService.createProduk(fileGambar,map);
                    calls.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            progress.dismiss();
                            if (response.isSuccessful()) {
                                try {
                                    Toast.makeText(AddProduk.this, response.body().string(), Toast.LENGTH_SHORT).show();
                                }
                                catch (Exception e)
                                {
                                    Toast.makeText(AddProduk.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                                Intent i = new Intent(AddProduk.this, ViewProduk.class);
                                progress.dismiss();
                                startActivity(i);
                                Toast.makeText(AddProduk.this, response.message(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                            {

                                Toast.makeText(AddProduk.this, response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            progress.dismiss();
                            Toast.makeText(AddProduk.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
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
    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }
    public void openGaler(){
        if(checkPermissionForReadExtertalStorage())
        {
            Intent galer = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galer, galleryCode);
        }
        else
        {
            try {
                requestPermissionForReadExtertalStorage();
                Toast.makeText(this, "a", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

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
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ContextCompat.checkSelfPermission(AddProduk.this, Manifest.permission.READ_EXTERNAL_STORAGE);
            int result2 = ContextCompat.checkSelfPermission(AddProduk.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            Toast.makeText(this, String.format("%d %d",result,result2), Toast.LENGTH_SHORT).show();
            return result == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }
    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) AddProduk.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            throw e;
        }
    }

}
