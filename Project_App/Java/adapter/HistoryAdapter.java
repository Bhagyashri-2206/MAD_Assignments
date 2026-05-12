package com.example.aquaritual.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aquaritual.R;
import com.example.aquaritual.model.DayHistory;

import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final List<DayHistory> list;

    public HistoryAdapter(List<DayHistory> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DayHistory item = list.get(position);

        holder.txtDate.setText(item.date != null ? item.date : "--");

        int totalMl = item.totalMl;

        float liters = totalMl / 1000f;

        holder.txtTotal.setText(
                String.format(Locale.getDefault(), "%d ml (%.2f L)", totalMl, liters)
        );
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtDate, txtTotal;

        ViewHolder(View v) {
            super(v);
            txtDate = v.findViewById(R.id.txtDate);
            txtTotal = v.findViewById(R.id.txtTotal);
        }
    }
}