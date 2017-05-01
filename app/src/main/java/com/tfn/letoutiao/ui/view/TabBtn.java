package com.tfn.letoutiao.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.tfn.letoutiao.R;

/**
 * Created by tf on 2017/4/28.
 */

public class TabBtn extends View{

    //透明度
    private float mAlpha = 1.0f;

    //自定义属性
    private Bitmap mIconBitmap;
    private String mText;
    private int mColor = 0xFF45C01A;
    private int mTextSize = (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics());

    //内存中绘制属性
    private Bitmap mBitmap;
    private Paint mPaint;
    private Canvas mCanvas;

    //绘制ICON的范围
    private Rect mIconRect;
    private Rect mTextBound;
    private Paint mTextPaint;

    public TabBtn(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public TabBtn(Context context){
        this(context, null);
    }


    /**
     * 获取自定义属性的值
     * @param context
     * @param attrs
     * @param defStyle
     */
    public TabBtn(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabBtn);

        int n = a.getIndexCount();

        for(int i = 0; i < n; i++){
            int attr = a.getIndex(i);
            switch(attr){
                case R.styleable.TabBtn_icon:
                    BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(attr);
                    mIconBitmap = drawable.getBitmap();
                    break;
                case R.styleable.TabBtn_text:
                    mText = a.getString(attr);
                    break;
                case R.styleable.TabBtn_color:
                    mColor = a.getColor(attr,  0xFF45C01A);
                    break;
                case R.styleable.TabBtn_textSize:
                    mTextSize = (int) a.getDimension(attr, TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;
            }
        }

        a.recycle();

        mTextPaint = new Paint();
        mTextBound = new Rect();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0xff555555);

        //得到mText的绘制范围
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int iconWidth = Math.min(getMeasuredWidth()-getPaddingLeft()
                -getPaddingRight(), getMeasuredHeight()-getPaddingTop()-getPaddingBottom()
                -mTextBound.height());

        int left = getMeasuredWidth()/2 - iconWidth/2;
        int top  = (getMeasuredHeight() - mTextBound.height())/2 - iconWidth/2;

        mIconRect = new Rect(left, top, left+iconWidth, top+iconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas){

        //绘制原图
        canvas .drawBitmap(mIconBitmap, null, mIconRect, null);

        int alpha = (int) Math.ceil(255*mAlpha);

        //内存中准备bitmap， 纯色， 图标，xformode， 图标
        setupTargetBitmap(alpha);
        drawSourceText(canvas, alpha);
        drawTargetText(canvas, alpha);
        canvas.drawBitmap(mBitmap, 0, 0, null);


    }

    //绘制变色的文本
    private void drawTargetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mColor);

        int x = getMeasuredWidth()/2 - mTextBound.width()/2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint);

    }


    /**
     * 绘制原文本
     * @param canvas
     * @param alpha
     */
    private void drawSourceText(Canvas canvas, int alpha) {

        mTextPaint.setColor(0xff555555);
        mTextPaint.setAlpha(255-alpha);
        int x = getMeasuredWidth()/2 - mTextBound.width()/2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y ,mTextPaint);

    }

    //在内存中绘制可辨色的icon
    private void setupTargetBitmap(int alpha) {
        mBitmap = Bitmap.createBitmap(
                getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);
        mCanvas.drawRect(mIconRect, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
    }

    //允许外界设置alpha
    public void setIconAlpha(float alpha){
        this.mAlpha = alpha;
        invalidateView();
    }


    //重绘
    private void invalidateView() {
        if (Looper.myLooper() == Looper.getMainLooper() ) {//判断当前线程是否是UI线程(主线程)
            invalidate();// invalidate()方法  说明：invalidate()是用来刷新View的，必须是在UI线程中进行工作   。请求重绘View树，并且只绘制那些“需要重绘的”视图，即谁(是View的话，只绘制该View ；是ViewGroup，则绘制整个 ViewGroup)请求invalidate()方法，就绘制该视图

        } else {//当前是子线程的时候
            postInvalidate();//使用postInvalidate()刷新界面   使用postInvalidate则比较简单，不需要handler，直接在线程中调用postInvalidate即可。
        }

    }

    private static final String INSTANCE_STATE = "intstance_state";
    private static final String STATES_ALPHA = "states_alpha";


    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putFloat(STATES_ALPHA, mAlpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        if(state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            mAlpha = bundle.getFloat(STATES_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
        }else{
            super.onRestoreInstanceState(state);
        }
    }
}
