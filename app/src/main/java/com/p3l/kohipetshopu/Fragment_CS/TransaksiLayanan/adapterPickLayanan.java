package com.p3l.kohipetshopu.Fragment_CS.TransaksiLayanan;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.p3l.kohipetshopu.Layanan.LayananDAO;
import com.p3l.kohipetshopu.R;

import java.util.ArrayList;
import java.util.List;

public class adapterPickLayanan extends RecyclerView.Adapter<adapterPickLayanan.MyViewHolder> implements Filterable {

    private ViewPickLayanan context;
    private List<LayananDAO> resultFiltered;
    public static List<DetilPelayananDAO> tempLayanan = new ArrayList<>();
    public String jumlah = "";
    public double subtotal = 0;

    public adapterPickLayanan(ViewPickLayanan context, List<LayananDAO> result){
        this.context = context;
        this.resultFiltered = result;
    }

    @NonNull
    @Override
    public adapterPickLayanan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_pick_produk,parent,false);
        final adapterPickLayanan.MyViewHolder holder = new adapterPickLayanan.MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull adapterPickLayanan.MyViewHolder holder, int position) {
        final LayananDAO item = resultFiltered.get(position);
        holder.nama.setText(item.getNama());
        holder.harga.setText(item.getHarga());


        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Clicked "+produk.getNama(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Transaksi Layanan");
                builder.setMessage("Masukan Jumlah Layanan untuk Peliharaan anda :");
                builder.setIcon(R.drawable.icon_layanan);
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setHint("jumlah");
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jumlah = input.getText().toString().trim();
                        if(jumlah.equals("") || jumlah.equals("0")){
                            Toast.makeText(context, "Masih Kosong", Toast.LENGTH_SHORT).show();
                        }else {
                            subtotal = Double.parseDouble(item.getHarga()) * Double.parseDouble(jumlah);

                            tempLayanan.add(new DetilPelayananDAO("", item.getIdlayanan(), jumlah, String.valueOf(subtotal), "0"));
                            for (int i = 0; i < resultFiltered.size(); i++) {
                                if (item.getIdlayanan().equals(resultFiltered.get(i).getIdlayanan())) {
                                    context.update_subtotalPicker();//update subtotal di View Pick Produk
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
                List<LayananDAO> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(resultFiltered);
                } else {
                    String fillPattern = constraint.toString().toLowerCase().trim();
                    for (LayananDAO row : resultFiltered) {
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
        private TextView nama,harga;
        private CardView parent;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNamaProduk);
            harga = itemView.findViewById(R.id.tvHargaProduk);
            parent =  itemView.findViewById(R.id.ParentProduk);
        }
    }
}
