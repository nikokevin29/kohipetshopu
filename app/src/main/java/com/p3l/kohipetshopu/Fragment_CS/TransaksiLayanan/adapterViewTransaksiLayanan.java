package com.p3l.kohipetshopu.Fragment_CS.TransaksiLayanan;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.Layanan.LayananDAO;
import com.p3l.kohipetshopu.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.p3l.kohipetshopu.Fragment_CS.TransaksiLayanan.adapterPickLayanan.tempLayanan;


public class adapterViewTransaksiLayanan extends RecyclerView.Adapter<adapterViewTransaksiLayanan.MyViewHolder> {

    private ViewTransaksiLayanan context;
    private List<DetilPelayananDAO> result;
    public String jumlah = "";
    public double doub;
    public adapterViewTransaksiLayanan(ViewTransaksiLayanan context, List<DetilPelayananDAO> result){
        this.context = context;
        this.result = result;
    }
    @NonNull
    @Override
    public adapterViewTransaksiLayanan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_view_transaksi,parent,false);
        final adapterViewTransaksiLayanan.MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        apiService.getLayananbyId(result.get(position).idlayanan).enqueue(new Callback<LayananDAO>() {
            @Override
            public void onResponse(Call<LayananDAO> call, Response<LayananDAO> response) {
                holder.nama.setText(response.body().getNama());
                holder.harga.setText(response.body().getHarga());
                holder.jumlah.setText(result.get(position).jumlah);
                doub = Double.parseDouble(response.body().getHarga());//Memasukan Variabel Harga dalam adapter ke variabel tampung
            }
            @Override
            public void onFailure(Call<LayananDAO> call, Throwable t) { }
        });
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Kelola Jumlah Layanan");
                builder.setMessage("Lakukan Aksi Edit / Delete:");
                builder.setIcon(R.drawable.icon_layanan);
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setHint("Edit Jumlah");
                builder.setView(input);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jumlah = input.getText().toString().trim();//get input edit
                        if(jumlah.equals("") || jumlah.equals("0")){
                            Toast.makeText(context, "Masih Kosong", Toast.LENGTH_SHORT).show();
                        }else {
                            tempLayanan.get(position).setJumlah(jumlah);//set update jumlah barang di adapter ViewTransaksi
                            tempLayanan.get(position).setSubtotal(Double.toString(doub * Double.parseDouble(jumlah))); //set subtotal
                            context.update_updated();//notify set data changed
                            context.subtotalFromRecycleTransaksi();//update subtotal sebelah update

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
                        tempLayanan.remove(position);
                        context.delete_updated(position,tempLayanan);//update posisi habis delete Arraylistnya
                        context.subtotalFromRecycleTransaksi();//update subtotal setelah delete
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
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nama,harga,jumlah;
        private CardView parent;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNamaProdukTransaksi);
            harga = itemView.findViewById(R.id.tvHargaTransaksi);
            jumlah = itemView.findViewById(R.id.tvJumlahTransaksi);
            parent =  itemView.findViewById(R.id.ParentTransaksi);
        }
    }
}
