package com.woo.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class DemoView extends View {
    private static final String TAG = "DemoView";
    private int mRadius;
    private int mScreenWidth, mScreenHeight;
    private Paint mCirclePaint, mArcPaint, mLinePaint, mTextPaint;
    private RectF mRectF;
    private float mMovStartX, mMovStartY, mMovEndX, mMovEndY;
    private ArrayList<Float> mMovX, mMovY;
    private Canvas mCanvas;

    public DemoView(Context context) {
        super(context);
        init();
    }

    public DemoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        mRadius = Math.min(mScreenHeight, mScreenWidth) / 4;

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG); // 笔刷
        mCirclePaint.setColor(Color.DKGRAY);
        mCirclePaint.setShadowLayer(10, 2, 2, Color.YELLOW);
        Log.d(TAG, "init: w:" + mScreenWidth + " h:" + mScreenHeight);

        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(Color.YELLOW);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(mRadius / 2);
        mRectF = new RectF((mScreenWidth-mRadius)/2, (mScreenHeight-mRadius)/2,
                (mScreenWidth+mRadius)/2, (mScreenHeight+mRadius)/2);

        mLinePaint = new Paint(Paint.DITHER_FLAG);
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(10);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mLinePaint.setAntiAlias(true);

        mMovX = new ArrayList<Float>();
        mMovY = new ArrayList<Float>();
    }

    private void drawSth(Canvas canvas) {
        canvas.drawColor(Color.LTGRAY);
        canvas.drawCircle(mScreenWidth / 2, mScreenHeight / 2, mRadius, mCirclePaint);
        canvas.drawArc(mRectF, 0, 130, false, mArcPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSth(canvas);
        for (int i = 0; i < mMovX.size()-1; i++) {
            Log.d(TAG, "onDraw: canvas.drawLine" + mMovX.size());
            canvas.drawLine(mMovX.get(i), mMovY.get(i), mMovX.get(i+1), mMovY.get(i+1), mLinePaint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: " + event.getAction());
        mMovStartX = event.getX();
        mMovStartY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(getContext(), "ACTION_DOWN", Toast.LENGTH_SHORT).show();
                mMovX.add(event.getX());
                mMovY.add(event.getY());
                    break;
            case MotionEvent.ACTION_UP:
                Toast.makeText(getContext(), "ACTION_UP", Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_MOVE:
                Toast.makeText(getContext(), "ACTION_MOVE", Toast.LENGTH_SHORT).show();
                mMovStartX = event.getX();
                break;
        }
        return super.onTouchEvent(event);
    }
}
