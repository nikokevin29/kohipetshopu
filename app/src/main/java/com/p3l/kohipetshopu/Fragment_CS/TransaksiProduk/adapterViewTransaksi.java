package com.p3l.kohipetshopu.Fragment_CS.TransaksiProduk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.Produk.ProdukDAO;
import com.p3l.kohipetshopu.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.p3l.kohipetshopu.Fragment_CS.TransaksiProduk.adapterPickProduk.tempProduk;

public class adapterViewTransaksi extends RecyclerView.Adapter<adapterViewTransaksi.MyViewHolder> {
    private Context context;
    private List<DetilPenjualanDAO> result;
    public String jumlah = "";
    public adapterViewTransaksi(Context context, List<DetilPenjualanDAO> result){
        this.context = context;
        this.result = result;
    }
    @NonNull
    @Override
    public adapterViewTransaksi.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_view_transaksi,parent,false);
        final adapterViewTransaksi.MyViewHolder holder = new adapterViewTransaksi.MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        apiService.getProdukbyId(result.get(position).idproduk).enqueue(new Callback<ProdukDAO>() {
            @Override
            public void onResponse(Call<ProdukDAO> call, Response<ProdukDAO> response) {

                holder.nama.setText(response.body().getNama());
                holder.harga.setText(response.body().getHarga());
                holder.jumlah.setText(result.get(position).jumlah);
                Picasso.get().load(response.body().URLproduk()).fit().into(holder.gambar);
            }
            @Override
            public void onFailure(Call<ProdukDAO> call, Throwable t) { }
        });
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Kelola Jumlah Produk");
                builder.setMessage("Lakukan Aksi Edit / Delete:");
                builder.setIcon(R.mipmap.ic_launcher);
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setHint("Edit Jumlah");
                builder.setView(input);

                jumlah = input.getText().toString().trim();
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //edit jumlah disini
                        if(jumlah.equals("")){
                            Toast.makeText(context, "Masih Kosong", Toast.LENGTH_SHORT).show();
                        }else {
                            //tempProduk.set(position,jumlah);
                        }
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { dialog.cancel(); }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tempProduk.remove(position);
                    }
                });
                builder.create().show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return result.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nama,harga,jumlah;
        private CardView parent;
        private ImageView gambar;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNamaProdukTransaksi);
            harga = itemView.findViewById(R.id.tvHargaTransaksi);
            jumlah = itemView.findViewById(R.id.tvJumlahTransaksi);
            gambar = itemView.findViewById(R.id.gambar_transaksi);
            parent =  itemView.findViewById(R.id.ParentTransaksi);
        }
    }

}
