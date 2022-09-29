package com.example.testcalendarweek;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalenderViewHolder>{

    private final ArrayList<LocalDate> daysOfWeek;
    private final Context context;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<LocalDate> daysOfWeek, Context context, OnItemListener onItemListener) {
        this.daysOfWeek = daysOfWeek;
        this.context = context;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = parent.getHeight();
        return new CalenderViewHolder(view, daysOfWeek, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalenderViewHolder holder, int position) {
        final LocalDate date = daysOfWeek.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(date == null)
                holder.txtDays.setText("");
            else {
                    holder.txtDays.setText(String.valueOf(date.getDayOfMonth()));
                    if(date.equals(CalendarUtils.selectDate)) {
                        holder.parentView.setBackground(context.getResources().getDrawable(R.drawable.cus_days_of_week_checked));
                    }
            }
            if(date.equals(CalendarUtils.selectDateNow()))
                holder.txtDays.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return daysOfWeek == null ? 0 : daysOfWeek.size();
    }

    public interface OnItemListener {
        void onItemCLick(int position, LocalDate date);
    }

    public class CalenderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtDays;
        private View parentView;
        private final ArrayList<LocalDate> daysOfWeek;
        private final OnItemListener onItemListener;

        public CalenderViewHolder(@NonNull View itemView, ArrayList<LocalDate> daysOfWeek, OnItemListener onItemListener) {
            super(itemView);
            txtDays = itemView.findViewById(R.id.cell_day_text);
            parentView = itemView.findViewById(R.id.parent_view);
            this.daysOfWeek = daysOfWeek;
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemCLick(getAdapterPosition(), daysOfWeek.get(getAdapterPosition()));
        }
    }
}
