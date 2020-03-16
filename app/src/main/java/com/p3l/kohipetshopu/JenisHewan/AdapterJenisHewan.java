package com.p3l.kohipetshopu.JenisHewan;

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
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.R;
import com.p3l.kohipetshopu.JenisHewan.AdapterJenisHewan;
import com.p3l.kohipetshopu.JenisHewan.JenisHewanDAO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterJenisHewan extends RecyclerView.Adapter<AdapterJenisHewan.MyViewHolder> implements Filterable {
    private Context context;
    private List<JenisHewanDAO> resultFiltered;
    private JenisAdapterListener listener;
    public AdapterJenisHewan(Context context, List<JenisHewanDAO> result, JenisAdapterListener listener){
        this.context = context;
        this.resultFiltered = result;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_jenis_hewan,parent,false);
        final MyViewHolder holder = new MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterJenisHewan.MyViewHolder holder, int position) {
        final JenisHewanDAO jenis = resultFiltered.get(position);
        holder.nama.setText(jenis.getNama());
        holder.created_at.setText(jenis.getCreated_at());
        holder.updated_at.setText(jenis.getUpdated_at());
        holder.deleted_at.setText(jenis.getDeleted_at());
        holder.aksi.setText(jenis.getAksi());
        holder.aktor.setText(jenis.getAktor());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();

                data.putString("nama", jenis.getNama());
                data.putString("created_at", jenis.getCreated_at());
                data.putString("updated_at", jenis.getUpdated_at());
                data.putString("deleted_at", jenis.getDeleted_at());
                data.putString("aksi", jenis.getAksi());
                data.putString("aktor", jenis.getAktor());

            }
        });
        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDialog(jenis);
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
                List<JenisHewanDAO> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(resultFiltered);
                } else {
                    String fillPattern = constraint.toString().toLowerCase().trim();
                    //List<JenisHewanDAO> filteredList = new ArrayList<>();
                    for (JenisHewanDAO row : resultFiltered) {
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
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                resultFiltered.clear();
                resultFiltered.addAll((List) filterResults.values);

                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nama, created_at, updated_at, deleted_at, aksi, aktor;
        private CardView parent;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNamaJenisHewan);
            created_at =  itemView.findViewById(R.id.tvCreated_at);
            updated_at = itemView.findViewById(R.id.tvUpdated_at);
            deleted_at = itemView.findViewById(R.id.tvDeleted_at);
            aksi = itemView.findViewById(R.id.tvAksi);
            aktor = itemView.findViewById(R.id.tvAktor);
            parent =  itemView.findViewById(R.id.ParentJenisHewan);
        }
    }
    private void startIntent(JenisHewanDAO hasil){
        Intent edit = new Intent(context, EditJenis.class);
        edit.putExtra("idjenis",hasil.getIdjenis());
        edit.putExtra("nama",hasil.getNama());
        context.startActivity(edit);
    }
    private void showDialog(final JenisHewanDAO hasil){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title dialog
        alertDialogBuilder.setTitle("Aksi apa yang akan anda lakukan?");

        // set pesan dari dialog
        alertDialogBuilder
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Edit",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int idjenis) {
                        // update
                        startIntent(hasil);

                    }
                })
                .setNegativeButton("Hapus",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int idjenis) {
                        //delete
                        deleteJenis(hasil.getIdjenis());
                        notifyItemRemoved(idjenis);
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

    private void deleteJenis(String idjenis){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> jenisDAOCall = apiService.deleteJenis(idjenis);


        jenisDAOCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(context, "Success Deleting", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Failed Deleteing", Toast.LENGTH_SHORT).show();
                System.out.println("TRACE ERROR "+t.getMessage());
            }
        });
    }
    public interface JenisAdapterListener {
        void onJenisSelected(JenisHewanDAO ukuranHewanDAO);
    }
}

