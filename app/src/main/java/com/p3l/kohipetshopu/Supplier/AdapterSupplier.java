package com.p3l.kohipetshopu.Supplier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterSupplier extends RecyclerView.Adapter<AdapterSupplier.MyViewHolder> implements Filterable {

    private Context context;
    private List<SupplierDAO> resultFiltered;
    private SupplierAdapterListener listener;

    public AdapterSupplier(Context context, List<SupplierDAO> result,SupplierAdapterListener listener){
        this.context = context;
        this.resultFiltered = result;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_supplier,parent,false);
        final MyViewHolder holder = new MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSupplier.MyViewHolder holder, int position) {
        final SupplierDAO supplier = resultFiltered.get(position);
        holder.nama.setText(supplier.getNama());
        holder.alamat.setText(supplier.getAlamat());
        holder.notelp.setText(supplier.getNotelp());
        holder.created_at.setText(supplier.getCreated_at());
        holder.updated_at.setText(supplier.getUpdated_at());
        holder.deleted_at.setText(supplier.getDeleted_at());
        holder.aksi.setText(supplier.getAksi());
        holder.aktor.setText(supplier.getAktor());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();

                data.putString("nama", supplier.getNama());
                data.putString("alamat", supplier.getAlamat());
                data.putString("notelp", supplier.getNotelp());
                data.putString("created_at", supplier.getCreated_at());
                data.putString("updated_at", supplier.getUpdated_at());
                data.putString("deleted_at", supplier.getDeleted_at());
                data.putString("aksi", supplier.getAksi());
                data.putString("aktor", supplier.getAktor());

            }
        });
        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDialog(supplier,position);
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
                List<SupplierDAO> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    //resultFiltered = result;
                    filteredList.addAll(resultFiltered);
                } else {
                    String fillPattern = constraint.toString().toLowerCase().trim();
                    //List<SupplierDAO> filteredList = new ArrayList<>();
                    for (SupplierDAO row : resultFiltered) {
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
        private TextView nama,alamat,notelp, created_at, updated_at, deleted_at, aksi, aktor;
        private CardView parent;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNamaSupplier);
            alamat =  itemView.findViewById(R.id.tvAlamatSupplier);
            notelp =  itemView.findViewById(R.id.tvNotelpSupplier);
            created_at =  itemView.findViewById(R.id.tvCreated_at);
            updated_at = itemView.findViewById(R.id.tvUpdated_at);
            deleted_at = itemView.findViewById(R.id.tvDeleted_at);
            aksi = itemView.findViewById(R.id.tvAksi);
            aktor = itemView.findViewById(R.id.tvAktor);
            parent =  itemView.findViewById(R.id.ParentSupplier);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSupplierSelected(resultFiltered.get(getAdapterPosition()));
                }
            });
        }
    }
    private void startIntent(SupplierDAO hasil){
        Intent edit = new Intent(context, EditSupplier.class);
        edit.putExtra("idsupplier",hasil.getIdsupplier());
        edit.putExtra("nama",hasil.getNama());
        edit.putExtra("notelp",hasil.getNotelp());
        edit.putExtra("alamat",hasil.getAlamat());

        context.startActivity(edit);
    }
    private void showDialog(final SupplierDAO hasil, int position){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title dialog
        alertDialogBuilder.setTitle("Aksi apa yang akan anda lakukan?");

        // set pesan dari dialog
        alertDialogBuilder
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Edit",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int idsupplier) {
                        // update
                        startIntent(hasil);
                    }
                })
                .setNegativeButton("Hapus",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int idsupplier) {
                        //delete
                        deleteSupplier(hasil.getIdsupplier());
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

    private void deleteSupplier(String idsupplier){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> supplierDAOCall = apiService.deleteSupplier(idsupplier);

        supplierDAOCall.enqueue(new Callback<Void>() {
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
    public interface SupplierAdapterListener {
        void onSupplierSelected(SupplierDAO supplierDAO);
    }
}
