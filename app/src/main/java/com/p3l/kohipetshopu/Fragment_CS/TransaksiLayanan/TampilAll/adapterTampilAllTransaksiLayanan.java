package com.p3l.kohipetshopu.Fragment_CS.TransaksiLayanan.TampilAll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Filter;
import android.widget.Filterable;

import com.p3l.kohipetshopu.API.ApiClient;
import com.p3l.kohipetshopu.API.ApiInterface;
import com.p3l.kohipetshopu.Fragment_CS.TransaksiLayanan.TransaksiPelayananDAO;
import com.p3l.kohipetshopu.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adapterTampilAllTransaksiLayanan extends RecyclerView.Adapter<adapterTampilAllTransaksiLayanan.MyViewHolder> implements Filterable {

    private ViewTampilTransaksiLayanan context;
    private List<TransaksiPelayananDAO> resultFiltered;

    public adapterTampilAllTransaksiLayanan(ViewTampilTransaksiLayanan context, List<TransaksiPelayananDAO> result){
        this.context = context;
        this.resultFiltered = result;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_tampil_all_transaksi_layanan,parent,false);
        final MyViewHolder holder = new MyViewHolder(v);

        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull adapterTampilAllTransaksiLayanan.MyViewHolder holder, int position) {
        final TransaksiPelayananDAO dao = resultFiltered.get(position);
        holder.notrans.setText(dao.getNoLY());
        holder.tglTransaksi.setText(dao.getTanggaltransaksi());
//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        apiService.gethewanbyid(result.get(position).getIdhewan()).enqueue(new Callback<HewanDAO>() {
//            @Override
//            public void onResponse(Call<HewanDAO> call, Response<HewanDAO> response) {
//                holder.hewan.setText(response.body().getNama());
//            }
//            @Override
//            public void onFailure(Call<HewanDAO> call, Throwable t) { }
//        });
//        apiService.getcustomerbyid(result.get(position).getIdcustomer()).enqueue(new Callback<CustomerDAO>() {
//            @Override
//            public void onResponse(Call<CustomerDAO> call, Response<CustomerDAO> response) {
//                holder.cust.setText(response.body().getNama());
//            }
//            @Override
//            public void onFailure(Call<CustomerDAO> call, Throwable t) { }
//        });
        holder.hewan.setText(dao.getIdhewan());
        holder.cust.setText(dao.getIdcustomer());

        if(dao.getTotal().equals("0")){
            holder.status.setText("Belum Lunas");
        }else{
            holder.status.setText("Lunas");
        }
        holder.statusLayanan.setText(dao.getStatus());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Yakin ingin hapus Transaksi?");
                alertDialogBuilder
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delete(dao.getIdtransaksipelayanan());
                                resultFiltered.remove(position);
                                notifyItemRemoved(position);
                            }
                        })
                        .setNeutralButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                alertDialogBuilder.create().show();
            }
        });

    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView notrans, hewan, tglTransaksi, cust, status,statusLayanan;
        private CardView parent;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            notrans = itemView.findViewById(R.id.noTrans);
            hewan =  itemView.findViewById(R.id.tvShowhewan);
            tglTransaksi = itemView.findViewById(R.id.tvTanggalTrans);
            cust = itemView.findViewById(R.id.tvnamacust);
            status = itemView.findViewById(R.id.tvStatus);
            statusLayanan = itemView.findViewById(R.id.tvStatusLayanan);
            parent =  itemView.findViewById(R.id.ParentTransaksiLayananAll);
        }
    }
    @Override
    public int getItemCount() {
        return resultFiltered.size();
    }
    private void delete(String id){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> DAOcall = apiService.deletePelayanan(id);
        DAOcall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(context, "Sukses Hapus", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Gagal Hapus", Toast.LENGTH_SHORT).show();
                System.out.println("TRACE ERROR "+t.getMessage());
            }
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<TransaksiPelayananDAO> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(resultFiltered);
                } else {
                    String fillPattern = constraint.toString().toLowerCase().trim();
                    for (TransaksiPelayananDAO row : resultFiltered) {
                        if (row.getNoLY().toLowerCase().contains(fillPattern)) {
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
}
