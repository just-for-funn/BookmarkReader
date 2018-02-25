package com.codezilla.bookmarkreader.views.fab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codezilla.bookmarkreader.R;

/**
 * Created by davut on 25.02.2018.
 */

public class FabMenuItem extends LinearLayout {
    private CharSequence itemText;
    private Drawable itemSrc;

    public FabMenuItem(Context context) {
        super(context);
        init(null);
    }

    public FabMenuItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FabMenuItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public FabMenuItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflate(getContext() , R.layout.fab_menu_item , this);
        if(attrs != null)
            bindAttributes(attrs);
    }

    private void bindAttributes(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.fabMenu, 0, 0);
        this.itemText = a.getString(R.styleable.fabMenu_fab_item_text);
        this.itemSrc = a.getDrawable(R.styleable.fabMenu_fab_item_src);
        a.recycle();
        TextView textView = findViewById(R.id.fab_item_text);
        textView.setText(itemText);
        FloatingActionButton fab = findViewById(R.id.fab_item_icon);
        fab.setImageDrawable(itemSrc);
        invalidate();
    }

}
