package me.nereo.multi_image_selector.view;

import android.content.Context;
import android.graphics.Matrix;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ScaleGestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 支持手势的ImageView
 * Created by Nereo on 2015/4/10.
 */
public class GestureImageView extends ImageView{

    private static final String TAG = "GestureImageView";

    public GestureImageView(Context context) {
        super(context);
        init(context);
    }

    public GestureImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GestureImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private ScaleGestureDetector mScaleGesture;
    private Matrix mImageMatrix;
    private GestureDetectorCompat mGestureDetector;

    // 最大缩放比例
    private static final float MAX_SCALE_FACTOR = 3.0f;
    // 最小缩放比例
    private static final float MIN_SCALE_FACTOR = 0.3f;
    // 系统常量，系统认为手指是否移动的最小距离
    private int mTouchSlop;

    private float mCurrentFactor = 1.0f;

    private float mFirstPointerX, mFirstPointerY;
    private float mSecondPointerX, mSecondPointerY;

    private int mCenterX, mCenterY;

    /**
     * 初始化
     * @param context
     */
    private void init(final Context context){

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        setScaleType(ScaleType.MATRIX);

        mImageMatrix = new Matrix();

        mScaleGesture = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener(){
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float factor = detector.getScaleFactor();
                mImageMatrix.postScale(factor, factor, mCenterX, mCenterY);
                setImageMatrix(mImageMatrix);
                return true;
            }
        });

        mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                mImageMatrix.postScale(1.f, 1.f, mCenterX, mCenterY);
                setImageMatrix(mImageMatrix);
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                //mImageMatrix.setTranslate(0, 0);
                //setImageMatrix(mImageMatrix);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        });

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if( w != oldw || h != oldh){
            int cx = (w - getDrawable().getIntrinsicWidth()) / 2 ;
            int cy = (h - getDrawable().getIntrinsicHeight()) / 2;
            mImageMatrix.setTranslate(cx, cy);
            setImageMatrix(mImageMatrix);

            mCenterX = w / 2;
            mCenterY = h / 2;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean retValue = mScaleGesture.onTouchEvent(event);

        retValue = mGestureDetector.onTouchEvent(event) || retValue;

        return retValue || super.onTouchEvent(event);
    }

}
