package com.hrupin.calendardayviewalgorithm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.hrupin.calendardayviewalgorithm.math.Event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Igor Khrupin www.hrupin.com on 11/22/16.
 */
public class DrawRectanglesView extends View {

    private List<Event> rects = new CopyOnWriteArrayList<>();

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

        if(rects != null) {
            if (!rects.isEmpty()) {
                for (Event er : rects) {
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

    public void drawRects(List<Event> rects) {
        if(rects != null) {
            this.rects = rects;
            invalidate();
        }
    }
}
