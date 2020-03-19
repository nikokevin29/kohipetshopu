package com.p3l.kohipetshopu.Layanan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterLayanan extends RecyclerView.Adapter<AdapterLayanan.MyViewHolder> implements Filterable {

    private Context context;
    private List<LayananDAO> resultFiltered;
    private LayananAdapterListener listener;

    public AdapterLayanan(Context context, List<LayananDAO> result, AdapterLayanan.LayananAdapterListener listener){
        this.context = context;
        this.resultFiltered = result;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterLayanan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_layanan,parent,false);
        final AdapterLayanan.MyViewHolder holder = new AdapterLayanan.MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLayanan.MyViewHolder holder, int position) {
        final LayananDAO layanan = resultFiltered.get(position);
        holder.nama.setText(layanan.getNama());
        holder.harga.setText(layanan.getHarga());
        holder.created_at.setText(layanan.getCreated_at());
        holder.updated_at.setText(layanan.getUpdated_at());
        holder.deleted_at.setText(layanan.getDeleted_at());
        holder.aktor.setText(layanan.getAktor());
        holder.aksi.setText(layanan.getAksi());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();

                data.putString("nama", layanan.getNama());
                data.putString("harga", layanan.getHarga());
                data.putString("created_at", layanan.getCreated_at());
                data.putString("updated_at", layanan.getUpdated_at());
                data.putString("deleted_at", layanan.getDeleted_at());
                data.putString("aktor", layanan.getAktor());
                data.putString("aksi", layanan.getAksi());

            }
        });
        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDialog(layanan);
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
                List<LayananDAO> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    //resultFiltered = result;
                    filteredList.addAll(resultFiltered);
                } else {
                    String fillPattern = constraint.toString().toLowerCase().trim();
                    //List<LayananDAO> filteredList = new ArrayList<>();
                    for (LayananDAO row : resultFiltered) {
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
        private TextView nama,harga, created_at, updated_at, deleted_at, aksi, aktor;
        private CardView parent;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNamaLayanan);
            harga = itemView.findViewById(R.id.tvHargaLayanan);
            created_at =  itemView.findViewById(R.id.tvCreated_at);
            updated_at = itemView.findViewById(R.id.tvUpdated_at);
            deleted_at = itemView.findViewById(R.id.tvDeleted_at);
            aksi = itemView.findViewById(R.id.tvAksi);
            aktor = itemView.findViewById(R.id.tvAktor);
            parent =  itemView.findViewById(R.id.ParentLayanan);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onLayananSelected(resultFiltered.get(getAdapterPosition()));
                }
            });
        }
    }
    private void startIntent(LayananDAO hasil){
        Intent edit = new Intent(context, EditLayanan.class);
        edit.putExtra("idlayanan",hasil.getIdlayanan());
        edit.putExtra("nama",hasil.getNama());
        edit.putExtra("harga",hasil.getHarga());
        context.startActivity(edit);
    }
    @SuppressLint("PrivateResource")
    private void showDialog(final LayananDAO hasil){
        //memunculkan Dialog Saat Long Press Adapter

        new MaterialAlertDialogBuilder(context, R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog)
            .setTitle("Aksi apa yang akan anda lakukan?")
            .setIcon(R.mipmap.ic_launcher)
            .setCancelable(false)
            .setPositiveButton("Edit",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int idlayanan) {
                    // update
                    startIntent(hasil);
                }
            })
            .setNegativeButton("Hapus",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int idlayanan) {
                    //delete
                    deleteLayanan(hasil.getIdlayanan());
                    notifyItemRemoved(idlayanan);
                }
            })
            .setNeutralButton("Batal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                }
            }).show();
    }

    private void deleteLayanan(String idlayanan){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> layananDAOCall = apiService.deleteLayanan(idlayanan);

        layananDAOCall.enqueue(new Callback<Void>() {
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
    public interface LayananAdapterListener {
        void onLayananSelected(LayananDAO layananDAO);
    }
}
