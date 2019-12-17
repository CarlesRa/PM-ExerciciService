package com.carlesramos.pm_exerciciservice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.carlesramos.pm_exerciciservice.R;
import java.util.ArrayList;

public class PrimoAdapter extends RecyclerView.Adapter<PrimoAdapter.PrimoViewHolder> {
    private ArrayList <Integer> numerosPrimos;
    private Context context;

    public PrimoAdapter(ArrayList<Integer> numerosPrimos, Context context) {
        this.numerosPrimos = numerosPrimos;
        this.context = context;
    }

    @NonNull
    @Override
    public PrimoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_num_primo, parent, false);
        PrimoViewHolder primoViewHolder = new PrimoViewHolder(itemView, context);
        return primoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PrimoViewHolder holder, int position) {
        int numPrimo = numerosPrimos.get(position);
        holder.bindPrimos(numPrimo);
    }

    @Override
    public int getItemCount() {
        return numerosPrimos.size();
    }

    public static class PrimoViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNumPrimos;
        public PrimoViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            tvNumPrimos = itemView.findViewById(R.id.tvNumPrimo);
        }

        public void bindPrimos(int numPrimo){
            tvNumPrimos.setText(String.valueOf(numPrimo));
        }

    }
}
