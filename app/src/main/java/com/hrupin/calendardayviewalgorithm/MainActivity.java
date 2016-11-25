package com.hrupin.calendardayviewalgorithm;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hrupin.calendardayviewalgorithm.math.DayViewMath;
import com.hrupin.calendardayviewalgorithm.math.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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

    private Button btn;
    private DrawRectanglesView drawRectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(this);

        drawRectView = (DrawRectanglesView)findViewById(R.id.draw_rect_view);
    }

    @Override
    public void onClick(View v) {
        drawRectView.drawRects(DayViewMath.getEventRects(this, events));
    }
}
