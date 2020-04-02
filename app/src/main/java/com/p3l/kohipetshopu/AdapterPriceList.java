package com.p3l.kohipetshopu;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.p3l.kohipetshopu.Produk.ProdukDAO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class AdapterPriceList extends RecyclerView.Adapter<AdapterPriceList.MyViewHolder> implements Filterable {

    private Context context;
    private List<ProdukDAO> resultFiltered;
    private ProdukPriceAdapterListener listener;

    public AdapterPriceList(Context context, List<ProdukDAO> result, AdapterPriceList.ProdukPriceAdapterListener listener){
        this.context = context;
        this.resultFiltered = result;
        this.listener = listener;

    }

    @NonNull
    @Override
    public AdapterPriceList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_price_list,parent,false);
        final AdapterPriceList.MyViewHolder holder = new AdapterPriceList.MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPriceList.MyViewHolder holder, int position) {
        final ProdukDAO produk = resultFiltered.get(position);


        holder.nama.setText(produk.getNama());
        holder.harga.setText(produk.getHarga());
        holder.stok.setText(produk.getStok());
        holder.stokminimum.setText(produk.getStokminimum());

        Picasso.get().load(produk.URLproduk()).fit().into(holder.gambar); //produk.getGambar()

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
                data.putString("aktor", produk.getAktor());
                data.putString("aksi", produk.getAksi());// klik adapternya 3 detik buat kluarin box
                data.putString("idsupplier", produk.getIdsupplier());
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


    public interface ProdukPriceAdapterListener {
        void onProdukSelected(ProdukDAO produkDAO);
    }
}
