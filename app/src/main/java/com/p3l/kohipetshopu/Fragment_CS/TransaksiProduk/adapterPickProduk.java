package com.p3l.kohipetshopu.Fragment_CS.TransaksiProduk;

import androidx.annotation.NonNull;
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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.p3l.kohipetshopu.Fragment_Owner.Pemesanan.DetilPemesananDAO;
import com.p3l.kohipetshopu.Produk.ProdukDAO;
import com.p3l.kohipetshopu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class adapterPickProduk extends RecyclerView.Adapter<adapterPickProduk.MyViewHolder> implements Filterable {

    private ViewPickProduk context;
    private List<ProdukDAO> resultFiltered;
    public static List<DetilPenjualanDAO> tempProduk = new ArrayList<>();
    public String jumlah = "";
    public double subtotal = 0;

    public adapterPickProduk(ViewPickProduk context, List<ProdukDAO> result){
        this.context = context;
        this.resultFiltered = result;
    }

    @NonNull
    @Override
    public adapterPickProduk.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_pick_produk,parent,false);
        final adapterPickProduk.MyViewHolder holder = new adapterPickProduk.MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull adapterPickProduk.MyViewHolder holder, int position) {
        final ProdukDAO produk = resultFiltered.get(position);
        holder.nama.setText(produk.getNama());
        holder.harga.setText(produk.getHarga());
        holder.stok.setText(produk.getStok());

        Picasso.get().load(produk.URLproduk()).fit().into(holder.gambar); //produk.getGambar()

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Clicked "+produk.getNama(), Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Transaksi Produk");
                builder.setMessage("Masukan Jumlah Produk yang ingin dibeli :");
                builder.setIcon(R.mipmap.ic_launcher);
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setHint("jumlah");
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jumlah = input.getText().toString().trim();
                        if(jumlah.equals("")){
                            Toast.makeText(context, "Masih Kosong", Toast.LENGTH_SHORT).show();
                        }else {
                            subtotal = subtotal + Double.parseDouble(produk.getHarga()) * Double.parseDouble(jumlah);
                            System.out.println("jumlah" + jumlah);
                            System.out.println("subtotal" + subtotal);
                            ViewPickProduk.tempProduk.add(new DetilPenjualanDAO("", produk.getIdproduk(), jumlah, String.valueOf(subtotal), "1"));
                            context.update_subtotal();
                            for (int i = 0; i < resultFiltered.size(); i++) {
                                if (produk.getIdproduk().equals(resultFiltered.get(i).getIdproduk())) {
                                    DetilPenjualanDAO trans = new DetilPenjualanDAO("", produk.getIdproduk(), jumlah, String.valueOf(subtotal), "1");
                                    tempProduk.add(trans);
                                }
                            }
                        }

                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
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
        private TextView nama,harga,stok;
        private CardView parent;
        private ImageView gambar;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNamaProduk);
            harga = itemView.findViewById(R.id.tvHargaProduk);
            stok = itemView.findViewById(R.id.tvStok);
            gambar = itemView.findViewById(R.id.gambar_produk);
            parent =  itemView.findViewById(R.id.ParentProduk);
        }
    }
}
