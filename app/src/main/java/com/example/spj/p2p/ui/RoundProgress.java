package com.example.spj.p2p.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.spj.p2p.R;
import com.example.spj.p2p.utils.UIUtils;

/**
 * Created by spj on 2016/9/20.
 */
public class RoundProgress extends View {

    //这几个属性通过布局文件中相应的属性的声明，进行初始化
    private int roundColor;//圆环的颜色
    private int roundProgressColor;//圆弧的颜色
    private int textColor;//字体的颜色
    private float roundWidth;//圆环的宽度
    private float textSize;//字体的大小
    private int max;//最大进度
    private int progress;//当前进度

    private int width;//圆环外切正方形的宽度
    private Paint paint;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public RoundProgress(Context context) {
        this(context, null);
    }

    public RoundProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化操作
        paint = new Paint();
        paint.setAntiAlias(true);


        //获取布局文件中自定义的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);
        roundColor = typedArray.getColor(R.styleable.RoundProgress_roundColor, Color.RED);
        textColor = typedArray.getColor(R.styleable.RoundProgress_textColor, Color.GREEN);
        roundProgressColor = typedArray.getColor(R.styleable.RoundProgress_roundProgressColor, Color.RED);
        roundWidth = typedArray.getDimension(R.styleable.RoundProgress_roundWidth, UIUtils.dp2px(10));
        textSize = typedArray.getDimension(R.styleable.RoundProgress_textSize, UIUtils.dp2px(20));

        max = typedArray.getInteger(R.styleable.RoundProgress_max, 100);
        progress = typedArray.getInteger(R.styleable.RoundProgress_progress, 60);

        //需要进行回收
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = this.getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //1.绘制圆环
        //圆心的横纵坐标
        int cx = width / 2;
        int cy = width / 2;

        //半径
        int radius = (int) (width / 2 - roundWidth / 2);
        paint.setStrokeWidth(roundWidth);//设置圆环的宽度
        paint.setColor(roundColor);//画笔的颜色
        paint.setStyle(Paint.Style.STROKE);//设置圆环样式

        canvas.drawCircle(cx, cy, radius, paint);

        //2.绘制圆弧,左上顶点，右下顶点

        RectF rectF = new RectF(roundWidth / 2, roundWidth / 2, width - roundWidth / 2, width - roundWidth / 2);
        paint.setColor(roundProgressColor);//设置圆弧的颜色
        canvas.drawArc(rectF, 0, progress * 360 / max, false, paint);//false表示没有那两条线

        //绘制文本
        String text = progress * 100 / max + "%";
        //文本所占的矩形的长宽
        Rect rect = new Rect();
        paint.setTextSize(textSize);
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        //先设置字体的大小，再调用以下的方法
        paint.getTextBounds(text, 0, text.length(), rect);
        //绘制字体是左下点，
        int textLeft = width / 2 - rect.width() / 2;
        int textBottom = width / 2 + rect.height() / 2;
        canvas.drawText(text, textLeft, textBottom, paint);
    }
}
