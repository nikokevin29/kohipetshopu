package com.p3l.kohipetshopu.Fragment_CS.Customer_CRUDS;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class AdapterCustomer extends RecyclerView.Adapter<AdapterCustomer.MyViewHolder> implements Filterable {

    private Context context;
    private List<CustomerDAO> resultFiltered;
    private CustomerAdapterListener listener;
    public AdapterCustomer(Context context, List<CustomerDAO> result, CustomerAdapterListener listener){
        this.context = context;
        this.resultFiltered = result;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_customer,parent,false);
        final MyViewHolder holder = new MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCustomer.MyViewHolder holder, int position) {
        final CustomerDAO data = resultFiltered.get(position);
        holder.nama.setText(data.getNama());
        holder.notelp.setText(data.getNotelp());
        holder.alamat.setText(data.getAlamat());
        holder.tgllahir.setText(data.getTgllahir());

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
                List<CustomerDAO> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(resultFiltered);
                } else {
                    String fillPattern = constraint.toString().toLowerCase().trim();
                    for (CustomerDAO row : resultFiltered) {
                        if (row.getNama().toLowerCase().contains(fillPattern) || row.getNotelp().contains(fillPattern)) {
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
        private TextView nama,notelp,alamat,tgllahir;
        private CardView parent;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNamaCustomer);
            notelp = itemView.findViewById(R.id.tvNotelpCustomer);
            alamat = itemView.findViewById(R.id.tvAlamatCustomer);
            tgllahir = itemView.findViewById(R.id.tvTgllahirCustomer);
            parent =  itemView.findViewById(R.id.ParentCustomer);
        }
    }
    private void startIntent(CustomerDAO hasil){
        Intent edit = new Intent(context, EditCustomer.class);
        edit.putExtra("idcustomer",hasil.getIdcustomer());
        edit.putExtra("nama",hasil.getNama());
        edit.putExtra("notelp",hasil.getNotelp());
        edit.putExtra("alamat",hasil.getAlamat());
        edit.putExtra("tgllahir",hasil.getTgllahir());
        context.startActivity(edit);
    }
    @SuppressLint("PrivateResource")
    private void showDialog(final CustomerDAO hasil, int position){
        // set pesan dari dialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog
                .setTitle("Aksi apa yang akan anda lakukan?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Edit",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int idcustomer) {
                        // update
                        startIntent(hasil);
                    }
                })
                .setNegativeButton("Hapus",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int idcustomer) {
                        //delete
                        delete(hasil.getIdcustomer());
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

    private void delete(String id){//Delete Customer
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> callDAO = apiService.deleteCustomer(id);
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
    public interface CustomerAdapterListener {
        void onCustomerSelected(CustomerDAO customerDAO);
    }
}
