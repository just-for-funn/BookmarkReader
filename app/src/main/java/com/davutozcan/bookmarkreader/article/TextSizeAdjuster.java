package com.davutozcan.bookmarkreader.article;

import android.util.TypedValue;
import android.widget.TextView;

class TextSizeAdjuster
{
    final float minScale = 0.5f;
    final float maxScale = 2.0f;
    final float minAllowedSize;
    final float maxAllowedSize;
    ITextView textView;

    TextSizeAdjuster(ITextView iTextView)
    {
        this.textView = iTextView;
        minAllowedSize = iTextView.getTextSize() * minScale;
        maxAllowedSize = iTextView.getTextSize() * maxScale;
    }

    void onScale(float scale)
    {
        float newSize = textView.getTextSize() * scale;
        if(newSize < minAllowedSize)
            newSize = minAllowedSize;
        if(newSize > maxAllowedSize)
            newSize = maxAllowedSize;
        textView.setTextSize(newSize);
    }

    static interface ITextView
    {
        float getTextSize();
        void setTextSize(float size);

        static ITextView from(TextView textView)
        {
            return new ITextView() {
                @Override
                public float getTextSize() {
                    return textView.getTextSize();
                }

                @Override
                public void setTextSize(float size) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX , size);
                }
            };
        }
    }
}
