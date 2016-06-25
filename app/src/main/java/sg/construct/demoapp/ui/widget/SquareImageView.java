package sg.construct.demoapp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Copyright (c) 2015, Posiba. All rights reserved.
 *
 * @author Hien Ngo
 * @since 5/22/15
 */
public class SquareImageView extends ImageView {
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(getResources().getDisplayMetrics().widthPixels, widthSize);
        } else {
            width = getResources().getDisplayMetrics().widthPixels;
        }
        setMeasuredDimension(width, width);
        int finalWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        super.onMeasure(finalWidthMeasureSpec, finalWidthMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        if (result) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    this.animate().scaleX(0.9f).scaleY(0.9f).start();
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    this.animate().scaleX(1f).scaleY(1f).start();
                    break;
            }
        }
        return result;
    }
}
