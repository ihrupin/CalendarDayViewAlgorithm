package com.hrupin.calendardayviewalgorithm;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hrupin.calendardayviewalgorithm.view.Clique;
import com.hrupin.calendardayviewalgorithm.view.Cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Igor Khrupin www.hrupin.com on 11/25/16.
 */

public class CalendarFragment extends Fragment {
    private static final int MINUTES_IN_A_HOUR = 24 * 60;
    private static List<Event> events = new ArrayList<>();

    static {
        events.add(new Event(1, "Event A", 9f, 10f));
        events.add(new Event(2, "Event B", 11f, 12f));
        events.add(new Event(3, "Event C", 11.5f, 12.5f));
        events.add(new Event(4, "Event D", 13f, 14.5f));
        events.add(new Event(5, "Event E", 13f, 13.5f));
        events.add(new Event(6, "Event F", 14f, 14.5f));
        events.add(new Event(7, "Event G", 15f, 16f));
        events.add(new Event(8, "Event H", 15f, 16.5f));
        events.add(new Event(9, "Event I", 15.5f, 17.0f));
        events.add(new Event(10, "Event J", 16.5f, 17.5f));
    }
    private RelativeLayout rlCalendarRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        rlCalendarRoot = (RelativeLayout)v.findViewById(R.id.rl_calendar_root);
        rlCalendarRoot.post(new Runnable() {
            @Override
            public void run() {
                drawEvents(events);
            }
        });
        return v;
    }

    public void drawEvents(List<Event> events) {
        if(events != null && !events.isEmpty()) {

            Collections.sort(events, new TimeComparator());
            int screenWidth = rlCalendarRoot.getWidth();
            int screenHeight = (int)((23f * getResources().getDimension(R.dimen.hour_divider_height) + (24f * getResources().getDimension(R.dimen.hour_divider_margin_top))));

            List<Cluster> clusters = createClusters(createCliques(events));

            for (Cluster c : clusters) {
                for (Event event : c.getEvents()) {
                    int eventWidth = screenWidth / c.getMaxCliqueSize();
                    int leftMargin = c.getNextPosition() * eventWidth;
                    int eventHeight = minutesToPixels(screenHeight, event.getEndTimeInMinutes() + 1) - minutesToPixels(screenHeight, event.getStartTimeInMinutes());
                    int topMargin = minutesToPixels(screenHeight, event.getStartTimeInMinutes());

                    TextView eventView = new TextView(getContext());
                    eventView.setText(getString(R.string.event_name_format, event.getName()));
                    eventView.setTextColor(Color.BLACK);
                    eventView.setBackgroundResource(R.drawable.event_bg);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(eventWidth, eventHeight);
                    params.setMargins(leftMargin, topMargin, 0, 0);

                    rlCalendarRoot.addView(eventView, params);
                }
            }
        }
    }

    private int minutesToPixels(int screenHeight, int minutes){
        return (screenHeight * minutes) / MINUTES_IN_A_HOUR;
    }

    public static List<Clique> createCliques(List<Event> events) {
        int startTime = events.get(0).getStartTimeInMinutes();
        int endTime = events.get(events.size() - 1).getEndTimeInMinutes();

        List<Clique> cliques = new ArrayList<>();

        for(int i = startTime; i <= endTime; i++){
            Clique c = null;
            for(Event e : events){
                if(e.getStartTimeInMinutes() <= i && e.getEndTimeInMinutes() >= i){
                    if(c == null){
                        c = new Clique();
                    }
                    c.addEvent(e);
                }
            }
            if(c != null){
                if(!cliques.contains(c)) {
                    cliques.add(c);
                }
            }
        }
        return cliques;
    }

    public static List<Cluster> createClusters(List<Clique> cliques) {
        List<Cluster> clusters = new ArrayList<>();
        Cluster cluster = null;
        for(Clique c : cliques){
            if(cluster == null){
                cluster = new Cluster();
                cluster.addClique(c);
            }else {
                if (cluster.getLastClique().intersects(c)) {
                    cluster.addClique(c);
                } else {
                    clusters.add(cluster);
                    cluster = new Cluster();
                    cluster.addClique(c);
                }
            }
        }
        if(cluster != null){
            clusters.add(cluster);
        }
        return clusters;
    }

    public static class TimeComparator implements Comparator {
        public int compare(Object obj1, Object obj2) {
            Event o1 = (Event)obj1;
            Event o2 = (Event)obj2;
            float change1 = o1.getStartTime();
            float change2 = o2.getStartTime();
            if (change1 < change2) {
                return -1;
            }
            if (change1 > change2) {
                return 1;
            }
            float change3 = o1.getEndTime();
            float change4 = o2.getEndTime();
            if (change3 < change4) return -1;
            if (change3 > change4) return 1;
            return 0;
        }
    }
}
