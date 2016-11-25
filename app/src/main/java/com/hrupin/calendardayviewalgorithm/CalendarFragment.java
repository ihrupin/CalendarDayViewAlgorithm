package com.hrupin.calendardayviewalgorithm;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hrupin.calendardayviewalgorithm.view.DrawRectanglesView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor Khrupin www.hrupin.com on 11/25/16.
 */

public class CalendarFragment extends Fragment {
    private static List<Event> events = new ArrayList<>();

    static {
        events.add(new Event(1, "A", 9f, 10f, Color.BLUE));
        events.add(new Event(2, "B", 11f, 12f, Color.YELLOW));
        events.add(new Event(3, "C", 11.5f, 12.5f, Color.RED));
        events.add(new Event(4, "D", 13f, 14.5f, Color.GREEN));
        events.add(new Event(5, "E", 13f, 13.5f, Color.CYAN));
        events.add(new Event(6, "F", 14f, 14.5f, Color.MAGENTA));
        events.add(new Event(7, "G", 15f, 16f, Color.GRAY));
        events.add(new Event(8, "H", 15f, 16.5f, Color.DKGRAY));
        events.add(new Event(9, "I", 15.5f, 17.0f, Color.BLACK));
        events.add(new Event(10, "J", 16.5f, 17.5f, Color.LTGRAY));
    }

    private DrawRectanglesView drawRectView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        drawRectView = new DrawRectanglesView(getContext());
        return drawRectView;
    }

    @Override
    public void onResume() {
        super.onResume();
        drawRectView.drawEvents(getContext(), events);
    }
}
