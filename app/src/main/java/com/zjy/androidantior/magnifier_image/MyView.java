package com.zjy.androidantior.magnifier_image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.MotionEvent;
import android.view.View;

/**
 * 绘制放大镜效果图像
 */
public class MyView extends View {
    private Bitmap bitmap;  //背景图像
    private ShapeDrawable drawable;
    private final int RADIUS = 57; //放大镜的半径
    private final int FACTOR = 2; //放大镜的倍数
    private Matrix matrix = new Matrix();
    private Bitmap bitmap_magnifier; //放大镜的位图
    private int m_left = 0; //放大镜的左边距
    private int m_top = 0;//放大镜的顶边距

    public MyView(Context context) {
        super(context);

        Bitmap bitmap_s = BitmapFactory.decodeResource(getResources(), R.mipmap.source);
        bitmap = bitmap_s;//获取要显示的背景图
        //创建bitmapshader对象
        BitmapShader shader = new BitmapShader(Bitmap.createScaledBitmap(bitmap_s, bitmap_s.getWidth() * FACTOR, bitmap_s.getHeight() * FACTOR, true), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //圆形的drawable
        drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setShader(shader);
        //设置圆的外切矩形
        drawable.setBounds(0,0,RADIUS*2,RADIUS*2);
        //获取放大镜的图像
        bitmap_magnifier = BitmapFactory.decodeResource(getResources(),R.mipmap.magnifier);
        //计算放大镜的默认左右边距
        m_left = RADIUS - bitmap_magnifier.getWidth()/2;
        m_top = RADIUS -bitmap_magnifier.getHeight()/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景图
        canvas.drawBitmap(bitmap,0,0,null);
        //绘制放大镜图像
        canvas.drawBitmap(bitmap_magnifier,m_left,m_top,null);
        //绘制放大后的图像
        drawable.draw(canvas);
    }

    /**
     * 重写此方法，实现当用户触摸屏幕时，放大触摸点附近的图像
     * @param event
     * @return
     */
    public boolean onTouchEvent(MotionEvent event) {
        //获取当前触摸点的x，y坐标
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        //平移到绘制shader的起始位置
        matrix.setTranslate(RADIUS-x*FACTOR,RADIUS-y*FACTOR);
        drawable.getPaint().getShader().setLocalMatrix(matrix);
        //设置圆的外切矩形
        drawable.setBounds(x-RADIUS,y-RADIUS,x+RADIUS,y+RADIUS);
        m_left = x-bitmap_magnifier.getWidth()/2;
        m_top= y-bitmap_magnifier.getHeight()/2;
        //重绘画布
        invalidate();
        return true;
    }
}
