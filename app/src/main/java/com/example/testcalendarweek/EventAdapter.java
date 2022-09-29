package com.example.testcalendarweek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EvenViewHodler>{
    private Context context;
    private ArrayList<Event> events;

    public EventAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public EvenViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject_time, parent, false);
        return new EvenViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EvenViewHodler holder, int position) {
        Event event = events.get(position);
        holder.txtTitle.setText(event.getName());
    }

    @Override
    public int getItemCount() {
        return events == null ? 0 : events.size();
    }

    public class EvenViewHodler extends RecyclerView.ViewHolder {
        TextView txtTitle;
        public EvenViewHodler(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
        }
    }
}
