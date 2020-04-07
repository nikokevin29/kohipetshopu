package com.p3l.kohipetshopu.Fragment_Owner.Pemesanan;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.p3l.kohipetshopu.Produk.ProdukDAO;
import com.p3l.kohipetshopu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class adapterPickProdukPemesanan extends RecyclerView.Adapter<adapterPickProdukPemesanan.MyViewHolder> implements Filterable {

    private Context context;
    private List<ProdukDAO> resultFiltered;
    private PickProdukPemesananAdapterListener listener;

    public adapterPickProdukPemesanan(Context context, List<ProdukDAO> result, adapterPickProdukPemesanan.PickProdukPemesananAdapterListener listener){
        this.context = context;
        this.resultFiltered = result;
        this.listener = listener;
    }

    @NonNull
    @Override
    public adapterPickProdukPemesanan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_pick_produk_pemesanan,parent,false);
        final adapterPickProdukPemesanan.MyViewHolder holder = new adapterPickProdukPemesanan.MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull adapterPickProdukPemesanan.MyViewHolder holder, int position) {
        final ProdukDAO produk = resultFiltered.get(position);
        holder.nama.setText(produk.getNama());
        holder.harga.setText(produk.getHarga());
        holder.stok.setText(produk.getStok());

        Picasso.get().load(produk.URLproduk()).fit().into(holder.gambar); //produk.getGambar()

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();

                data.putString("nama", produk.getNama());
                data.putString("harga", produk.getHarga());
                data.putString("stok", produk.getStok());
                data.putString("stokminimum", produk.getStokminimum());
                data.putString("gambar", produk.getGambar());
                data.putString("aktor", produk.getAktor());
                data.putString("idsupplier", produk.getIdsupplier());
                data.putString("aksi", produk.getAksi());// klik adapternya 3 detik buat kluarin box
                Toast.makeText(v.getContext(), "Clicked "+produk.getNama(), Toast.LENGTH_SHORT).show();

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
                    filteredList.addAll(resultFiltered);
                } else {
                    String fillPattern = constraint.toString().toLowerCase().trim();
                    for (ProdukDAO row : resultFiltered) {
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
        private TextView nama,harga,stok,stokminimum, aksi, aktor,idsupplier;
        private CardView parent;
        private ImageView gambar;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNamaProduk);
            harga = itemView.findViewById(R.id.tvHargaProduk);
            stok = itemView.findViewById(R.id.tvStokProduk);
            stokminimum = itemView.findViewById(R.id.tvStokMinimumProduk);
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


    public interface PickProdukPemesananAdapterListener {
        void onProdukSelected(ProdukDAO produkDAO);
    }
}
