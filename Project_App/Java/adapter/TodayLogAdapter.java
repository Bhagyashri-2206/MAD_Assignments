package com.example.aquaritual.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aquaritual.R;
import com.example.aquaritual.model.WaterLog;

import java.util.List;
import java.util.Locale;

public class TodayLogAdapter extends RecyclerView.Adapter<TodayLogAdapter.ViewHolder> {

    private final List<WaterLog> list;

    public TodayLogAdapter(List<WaterLog> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_today_log, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        WaterLog log = list.get(position);


        String time = (log.time != null && !log.time.isEmpty()) ? log.time : "--";
        holder.txtTime.setText(time);


        int amount = log.amount;

        holder.txtAmount.setText(
                String.format(Locale.getDefault(), "%d ml", amount)
        );
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTime, txtAmount;

        ViewHolder(View v) {
            super(v);
            txtTime = v.findViewById(R.id.txtTime);
            txtAmount = v.findViewById(R.id.txtAmount);
        }
    }
}