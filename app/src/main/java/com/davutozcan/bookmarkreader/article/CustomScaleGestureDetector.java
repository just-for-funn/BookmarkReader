package com.davutozcan.bookmarkreader.article;

import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.annimon.stream.function.Consumer;

public class CustomScaleGestureDetector
{
    ScaleGestureDetector scaleGestureDetector;
    Consumer<Float> scaleListener;
    public CustomScaleGestureDetector(View v)
    {
        scaleGestureDetector = new ScaleGestureDetector(v.getContext() , new ScaleGestureDetector.SimpleOnScaleGestureListener()
        {
            @Override
            public boolean onScale(ScaleGestureDetector detector)
            {
               if(scaleListener != null)
                   scaleListener.accept(detector.getScaleFactor());
               return true;
            }
        });
        v.setOnTouchListener((v1, event) ->
        {
            scaleGestureDetector.onTouchEvent(event);
            return event.getPointerCount() > 1;
        });
    }

    public static CustomScaleGestureDetector bindScaleGesture(View v)
    {
        return new CustomScaleGestureDetector(v);
    }

    public  CustomScaleGestureDetector onScale(Consumer<Float> onScale)
    {
        this.scaleListener = onScale;
        return this;
    }
}
