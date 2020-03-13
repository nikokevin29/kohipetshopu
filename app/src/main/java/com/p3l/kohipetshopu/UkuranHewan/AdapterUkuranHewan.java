package com.p3l.kohipetshopu.UkuranHewan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterUkuranHewan extends RecyclerView.Adapter<AdapterUkuranHewan.MyViewHolder> implements Filterable {
    private Context context;
    private List<UkuranHewanDAO> resultFiltered;
    private UkuranAdapterListener listener;

    public AdapterUkuranHewan(Context context, List<UkuranHewanDAO> result,UkuranAdapterListener listener){
        this.context = context;
        this.resultFiltered = result;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_ukuran_hewan,parent,false);
        final MyViewHolder holder = new MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUkuranHewan.MyViewHolder holder, int position) {
        final UkuranHewanDAO jenis = resultFiltered.get(position);
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
                List<UkuranHewanDAO> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    //resultFiltered = result;
                    filteredList.addAll(resultFiltered);
                } else {
                    String fillPattern = constraint.toString().toLowerCase().trim();
                    //List<UkuranHewanDAO> filteredList = new ArrayList<>();
                    for (UkuranHewanDAO row : resultFiltered) {
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
        private TextView nama, created_at, updated_at, deleted_at, aksi, aktor;
        private CardView parent;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNamaUkuranHewan);
            created_at =  itemView.findViewById(R.id.tvCreated_at);
            updated_at = itemView.findViewById(R.id.tvUpdated_at);
            deleted_at = itemView.findViewById(R.id.tvDeleted_at);
            aksi = itemView.findViewById(R.id.tvAksi);
            aktor = itemView.findViewById(R.id.tvAktor);
            parent =  itemView.findViewById(R.id.ParentUkuranHewan);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onUkuranSelected(resultFiltered.get(getAdapterPosition()));
                }
            });
        }
    }
    private void startIntent(UkuranHewanDAO hasil){
        Intent edit = new Intent(context, EditUkuran.class);
        edit.putExtra("idukuran",hasil.getIdukuran());
        edit.putExtra("nama",hasil.getNama());
        context.startActivity(edit);
    }
    private void showDialog(final UkuranHewanDAO hasil){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title dialog
        alertDialogBuilder.setTitle("Aksi apa yang akan anda lakukan?");

        // set pesan dari dialog
        alertDialogBuilder
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Edit",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int idukuran) {
                        // update
                        startIntent(hasil);
                    }
                })
                .setNegativeButton("Hapus",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int idukuran) {
                        //delete
                        deleteUkuran(hasil.getIdukuran());
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

    private void deleteUkuran(String idukuran){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> ukuranDAOCall = apiService.deleteUkuran(idukuran);

        ukuranDAOCall.enqueue(new Callback<Void>() {
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
    public interface UkuranAdapterListener {
        void onUkuranSelected(UkuranHewanDAO ukuranHewanDAO);
    }

}
