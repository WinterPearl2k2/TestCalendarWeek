package com.example.testcalendarweek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private RecyclerView calendarRecyclerView, itemSubjectRecyclerView;

    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CalendarUtils.selectDate = LocalDate.now();
        }
        setWeekView();
        swipeWeek();
        pauseSwipe();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void pauseSwipe() {
        calendarRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void swipeWeek() {
        itemSubjectRecyclerView.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x1 = motionEvent.getX();
                    y1 = motionEvent.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    x2 = motionEvent.getX();
                    y2 = motionEvent.getY();
                    float deltaX = x2 - x1;
                    float deltaY = y2 - y1;
                    Animation animation = null;
                    if(deltaX > 0 && (deltaY > 0 && deltaY < 300)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.luot_trai);
                            CalendarUtils.selectDate = CalendarUtils.selectDate.minusDays(1);
                        }
                    } else if(deltaX < 0 && (deltaY > 0 && deltaY < 300)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.luot_phai);
                            CalendarUtils.selectDate = CalendarUtils.selectDate.plusDays(  1);
                        }
                    }
//                    calendarRecyclerView.setAnimation(animation);
                    itemSubjectRecyclerView.setAnimation(animation);
                    setWeekView();
                    break;
            }
            return false;
        });
    }

    private void setWeekView() {
        ArrayList<LocalDate> daysInWeek = CalendarUtils.daysInWeekArray(CalendarUtils.selectDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInWeek, this, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEvenAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEvenAdapter();
    }

    private void setEvenAdapter() {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        itemSubjectRecyclerView.setLayoutManager(linearLayoutManager);
        itemSubjectRecyclerView.setAdapter(eventAdapter);
    }

    ImageButton hihi;
    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendar_recycler);
        itemSubjectRecyclerView = findViewById(R.id.item_class_recycler);
        hihi = findViewById(R.id.btn_hihi);
    }

    @Override
    public void onItemCLick(int position, LocalDate date) {
        CalendarUtils.selectDate = date;
        setWeekView();
    }

    public void Test(View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalTime time = LocalTime.now();
            Event event = new Event("Dong", time, CalendarUtils.selectDate);
            Event event1 = new Event("Hai", time, CalendarUtils.selectDate);
            Event event2 = new Event("Long", time, CalendarUtils.selectDate);
            Event event3 = new Event("Vuong", time, CalendarUtils.selectDate);
            Event.eventsList.add(event);
            Event.eventsList.add(event1);
            Event.eventsList.add(event2);
            Event.eventsList.add(event3);
        }
        setEvenAdapter();
    }
}