package com.p3l.kohipetshopu.Fragment_CS.Hewan_RUDS;

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
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.JenisHewan.JenisHewanDAO;
import com.p3l.kohipetshopu.R;
import com.p3l.kohipetshopu.UkuranHewan.UkuranHewanDAO;

import java.util.ArrayList;
import java.util.List;
import android.widget.Filter;
import android.widget.Filterable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterHewan extends RecyclerView.Adapter<AdapterHewan.MyViewHolder> implements Filterable {

    private Context context;
    private List<HewanDAO> resultFiltered;
    HewanAdapterListener listener;
    public AdapterHewan(Context context, List<HewanDAO> result, HewanAdapterListener listener){
        this.context = context;
        this.resultFiltered = result;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_hewan,parent,false);
        final MyViewHolder holder = new MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHewan.MyViewHolder holder, int position) {
        final HewanDAO data = resultFiltered.get(position);
        holder.nama.setText(data.getNama());
        holder.tgllahir.setText(data.getTgllahir());
        holder.jenis.setText(data.getIdjenis());
        holder.ukuran.setText(data.getIdukuran());
        holder.pemilik.setText(data.getIdcustomer());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Kamu Menekan "+data.getNama(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDialog(data,position);
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
                List<HewanDAO> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(resultFiltered);
                } else {
                    String fillPattern = constraint.toString().toLowerCase().trim();
                    for (HewanDAO row : resultFiltered) {
                        if (row.getNama().toLowerCase().contains(fillPattern)){
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
        private TextView nama,tgllahir,jenis,ukuran,pemilik;
        private CardView parent;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNamaHewan);
            tgllahir = itemView.findViewById(R.id.tvTgllahirHewan);

            jenis = itemView.findViewById(R.id.tvJenisHewan);
            ukuran = itemView.findViewById(R.id.tvUkuranHewan);
            pemilik = itemView.findViewById(R.id.tvNamaPemilik);

            parent =  itemView.findViewById(R.id.ParentHewan);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onHewanSelected(resultFiltered.get(getAdapterPosition()));
                }
            });
        }
    }
    private void startIntent(HewanDAO hasil){
        Intent edit = new Intent(context, EditHewan.class);//nama,tgllahir,jenis,ukuran,pemilik
        edit.putExtra("idhewan",hasil.getIdhewan());
        edit.putExtra("nama",hasil.getNama());
        edit.putExtra("tgllahir",hasil.getTgllahir());
        edit.putExtra("idjenis",hasil.getIdjenis());
        edit.putExtra("idukuran",hasil.getIdukuran());
        edit.putExtra("idcustomer",hasil.getIdcustomer());
        System.out.println("BNN "+hasil.getIdcustomer());
        context.startActivity(edit);
        Toast.makeText(context, hasil.getIdcustomer(), Toast.LENGTH_SHORT).show();
    }

    private void showDialog(final HewanDAO hasil, int position){
        // set pesan dari dialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog
                .setTitle("Aksi apa yang akan anda lakukan?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Edit",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // update
                        startIntent(hasil);
                    }
                })
                .setNegativeButton("Hapus",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //delete
                        delete(hasil.getIdhewan());
                        notifyItemRemoved(position);
                        resultFiltered.remove(position);
                    }
                })
                .setNeutralButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //tutup dialog
                        dialog.cancel();
                    }
                }).show();
    }

    private void delete(String id){//Delete Hewan
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> callDAO = apiService.deleteHewan(id);
        callDAO.enqueue(new Callback<Void>() {
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
    public interface HewanAdapterListener {
        void onHewanSelected(HewanDAO hewanDAO);
    }

}
