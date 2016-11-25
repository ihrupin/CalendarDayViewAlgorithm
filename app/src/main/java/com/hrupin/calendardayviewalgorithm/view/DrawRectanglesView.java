package com.hrupin.calendardayviewalgorithm.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.hrupin.calendardayviewalgorithm.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Igor Khrupin www.hrupin.com on 11/22/16.
 */
public class DrawRectanglesView extends View {

    private List<Event> events = new CopyOnWriteArrayList<>();

    public DrawRectanglesView(Context context) {
        this(context, null);
    }

    public DrawRectanglesView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawRectanglesView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);

        if(events != null) {
            if (!events.isEmpty()) {
                for (Event er : events) {
                    Rect r = er.getRect();
                    Paint paint = new Paint();
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                    paint.setColor(er.getColor());

                    canvas.drawRect(r.left, r.top, r.right, r.bottom, paint);
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(20);
                    canvas.drawText(er.getName(), r.centerX(), r.centerY(), paint);
                }
            }
        }
    }

    public void drawEvents(Context context, List<Event> events) {
        if(events != null && !events.isEmpty()) {

            Collections.sort(events, new TimeComparator());

            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int screenWidth = dm.widthPixels;

            List<Cluster> clusters = createClusters(createCliques(events));

            for (Cluster c : clusters) {
                for (Event event : c.getEvents()) {
                    int width = screenWidth / c.getMaxCliqueSize();
                    int height = event.getEndTimeInMinutes() + 1 - event.getStartTimeInMinutes();
                    int top = event.getStartTimeInMinutes();
                    int left = c.getNextPosition() * width;

                    Rect r = new Rect(left, top, left + width, top + height);
                    event.setRect(r);
                }
            }
            this.events = events;
            invalidate();
        }
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
