package com.p3l.kohipetshopu.Produk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.p3l.kohipetshopu.API.ApiClient.BASE_URL;

public class AdapterProduk extends RecyclerView.Adapter<AdapterProduk.MyViewHolder> implements Filterable {

    private Context context;
    private List<ProdukDAO> resultFiltered;
    private ProdukAdapterListener listener;

    public AdapterProduk(Context context, List<ProdukDAO> result, AdapterProduk.ProdukAdapterListener listener){
        this.context = context;
        this.resultFiltered = result;
        this.listener = listener;

    }

    @NonNull
    @Override
    public AdapterProduk.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_produk,parent,false);
        final AdapterProduk.MyViewHolder holder = new AdapterProduk.MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProduk.MyViewHolder holder, int position) {
        final ProdukDAO produk = resultFiltered.get(position);


        holder.nama.setText(produk.getNama());
        holder.harga.setText(produk.getHarga());
        holder.stok.setText(produk.getStok());
        holder.stokminimum.setText(produk.getStokminimum());

        System.out.println("PANTSU"+produk.getGambar());
        Picasso.get().load(produk.URLproduk()).fit().into(holder.gambar); //produk.getGambar()

        holder.created_at.setText(produk.getCreated_at());
        holder.updated_at.setText(produk.getUpdated_at());
        holder.deleted_at.setText(produk.getDeleted_at());
        holder.aktor.setText(produk.getAktor());
        holder.aksi.setText(produk.getAksi());
        holder.idsupplier.setText(produk.getIdsupplier());

        System.out.println("GAMBAR: "+produk.getGambar());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();

                data.putString("nama", produk.getNama());
                data.putString("harga", produk.getHarga());
                data.putString("stok", produk.getStok());
                data.putString("stokminimum", produk.getStokminimum());
                data.putString("gambar", produk.getGambar());
                data.putString("created_at", produk.getCreated_at());
                data.putString("updated_at", produk.getUpdated_at());
                data.putString("deleted_at", produk.getDeleted_at());
                data.putString("aktor", produk.getAktor());
                data.putString("aksi", produk.getAksi());
                data.putString("idsupplier", produk.getIdsupplier());

            }
        });
        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDialog(produk,position);
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return resultFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<ProdukDAO> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    //resultFiltered = result;
                    filteredList.addAll(resultFiltered);
                } else {
                    String fillPattern = constraint.toString().toLowerCase().trim();
                    //List<ProdukDAO> filteredList = new ArrayList<>();
                    for (ProdukDAO row : resultFiltered) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNama().toLowerCase().contains(fillPattern)) {
                            filteredList.add(row);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                resultFiltered.clear();
                resultFiltered.addAll((List) filterResults.values);

                notifyDataSetChanged();

            }
        };
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nama,harga,stok,stokminimum, created_at, updated_at, deleted_at, aksi, aktor,idsupplier;
        private CardView parent;
        private ImageView gambar;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNamaProduk);
            harga = itemView.findViewById(R.id.tvHargaProduk);
            stok = itemView.findViewById(R.id.tvStokProduk);
            stokminimum = itemView.findViewById(R.id.tvStokMinimumProduk);
            created_at =  itemView.findViewById(R.id.tvCreated_at);
            updated_at = itemView.findViewById(R.id.tvUpdated_at);
            deleted_at = itemView.findViewById(R.id.tvDeleted_at);
            aksi = itemView.findViewById(R.id.tvAksi);
            aktor = itemView.findViewById(R.id.tvAktor);
            idsupplier = itemView.findViewById(R.id.tvSupplierproduk);
            gambar = (ImageView) itemView.findViewById(R.id.gambar_produk);
            parent =  itemView.findViewById(R.id.ParentProduk);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onProdukSelected(resultFiltered.get(getAdapterPosition()));
                }
            });
        }
    }
    private void startIntent(ProdukDAO hasil){
        Intent edit = new Intent(context, EditProduk.class);
        edit.putExtra("idproduk",hasil.getIdproduk());
        edit.putExtra("nama",hasil.getNama());
        edit.putExtra("harga",hasil.getHarga());
        edit.putExtra("stok",hasil.getStok());
        edit.putExtra("stokminimum",hasil.getStokminimum());
        edit.putExtra("gambar",hasil.getGambar());
        context.startActivity(edit);
    }
    private void showDialog(final ProdukDAO hasil,int position){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title dialog
        alertDialogBuilder.setTitle("Aksi apa yang akan anda lakukan?");

        // set pesan dari dialog
        alertDialogBuilder
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Edit",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int idproduk) {
                        // update
                        startIntent(hasil);
                    }
                })
                .setNegativeButton("Hapus",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int idproduk) {
                        //delete
                        deleteProduk(hasil.getIdproduk());
                        notifyItemRemoved(position);
                        resultFiltered.remove(position);
                    }
                })
                .setNeutralButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void deleteProduk(String idproduk){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> produkDAOCall = apiService.deleteProduk(idproduk);

        produkDAOCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(context, "Success Deleting", Toast.LENGTH_SHORT).show();
                System.out.println("Sukses Delete");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Failed Deleteing", Toast.LENGTH_SHORT).show();
                System.out.println("TRACE ERROR "+t.getMessage());
            }
        });
    }
    public interface ProdukAdapterListener {
        void onProdukSelected(ProdukDAO produkDAO);
    }
}
