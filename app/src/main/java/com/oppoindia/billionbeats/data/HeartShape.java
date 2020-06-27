package com.oppoindia.billionbeats.data;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.view.View;

public class HeartShape extends View{

    private static int WIDTH = 500;
    private static int HEIGHT = 300;

    private Path path;
    private Paint paint;

    private int top;
    private int left;


    public HeartShape(Context context) {
        super(context);

        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        top=10;
        left=10;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Fill the canvas with background color
        canvas.drawColor(Color.GRAY);
        paint.setShader(null);

        // Defining of  the heart path starts
        path.moveTo(left+WIDTH/2, top+HEIGHT/4); // Starting point
        // Create a cubic Bezier cubic left path
        path.cubicTo(left+WIDTH/5,top,
                left+WIDTH/4,top+4*HEIGHT/5,
                left+WIDTH/2, top+HEIGHT);
        // This is right Bezier cubic path
        path.cubicTo(left+3*WIDTH/4,top+4*HEIGHT/5,
                left+4*WIDTH/5,top,
                left+WIDTH/2, top+HEIGHT/4);

        paint.setColor(Color.RED); // Set with heart color
        //paint.setShader(shader);
        paint.setStyle(Style.FILL); // Fill with heart color
        canvas.drawPath(path, paint); // Actual drawing happens here

        // Draw Blue Boundary
        paint.setShader(null);
        paint.setColor(Color.BLUE); // Change the boundary color
        paint.setStrokeWidth(4);
        paint.setStyle(Style.STROKE);
        canvas.drawPath(path, paint);

    }
}