package com.tool.androidapidemo.imageview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class FixRatioImageView extends ImageView {
    public float ratio=1;
    public FixRatioImageView(Context context) {
        super(context);
    }

    public FixRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixRatioImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height =(int)( width * ratio);
        setMeasuredDimension(width, height);
    }
}
