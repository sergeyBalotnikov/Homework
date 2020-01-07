package ru.mail.sergey_balotnikov.homework2_2.task2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

import ru.mail.sergey_balotnikov.homework2_2.MainActivity;
import ru.mail.sergey_balotnikov.homework2_2.R;

public class CustomView extends View {

    private int width;
    private int height;
    private int [] arrayColors;
    private float centerX;
    private float touchX;
    private float touchY;
    private float centerY;
    private int activeColor;
    private CustomCallBack callBack = null;
    private String [] arrayColorsName;
    private String activeColorName;

    public void setCallBack(CustomCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        touchX=event.getX();
        touchY=event.getY();
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            int touchRadius = (int)Math.sqrt(Math.pow(Math.abs(centerX-event.getX()),2)
                    +Math.pow(Math.abs(centerY-event.getY()),2));
            if(touchRadius<=getMeasuredHeight()/2){
                if(centerY<event.getY()){
                    if(centerX>event.getX()){
                        activeColor=arrayColors[3];
                        activeColorName=arrayColorsName[3];
                    } else {
                        activeColor=arrayColors[2];
                        activeColorName=arrayColorsName[2];
                    }
                } else if(centerX>event.getX()){
                    activeColor=arrayColors[0];
                    activeColorName=arrayColorsName[0];
                } else {
                    activeColor=arrayColors[1];
                    activeColorName=arrayColorsName[1];
                }
            } else {
                return true;
            }
            if(touchRadius<=getMeasuredHeight()/6){
                setArrayColors();
                invalidate();
                return true;
            }
        int [] XYColor = {(int)touchX, (int)touchY, activeColor};
            callBack.makeToast(XYColor);
            callBack.writeFile(XYColor, activeColorName);
        }

        return true;
    }

    protected void setArrayColors(){
        arrayColors = new int[]{Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW};
        arrayColorsName = new String[]{"Green", "Red", "Blue", "Yellow"};
        for(int i = 0; i<arrayColors.length; i++){
            int tempCopy = arrayColors[i];
            String tempName = arrayColorsName[i];
            int changeOn = (int)Math.round(Math.random()*3);
            arrayColors[i]=arrayColors[changeOn];
            arrayColorsName[i]=arrayColorsName[changeOn];
            arrayColors[changeOn]=tempCopy;
            arrayColorsName[changeOn]=tempName;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desiredWidth = 400;
        int desiredHeight = 400;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }
        if(width>height){
            width=height;
        } else {
            height = width;
        }
        centerX = width/2;
        centerY = height/2;
        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        RectF rect = new RectF();

        rect.set(0,0,getMeasuredWidth(),getMeasuredHeight());
        paint.setColor(arrayColors[0]);
        canvas.drawArc(rect,180f, 90f, true, paint);
        paint.setColor(arrayColors[1]);
        canvas.drawArc(rect,270f, 90f, true, paint);
        paint.setColor(arrayColors[2]);
        canvas.drawArc(rect,0f, 90f, true, paint);
        paint.setColor(arrayColors[3]);
        canvas.drawArc(rect,90f, 90f, true, paint);
        paint.setColor(getResources().getColor(R.color.colorCustomViewCenter, null));
        canvas.drawCircle(getMeasuredHeight()/2, getMeasuredWidth()/2, getMeasuredHeight()/6, paint);

    }



    public CustomView(Context context) {
        super(context);
        setArrayColors();
        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setArrayColors();
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setArrayColors();
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
    }


}
