package com.example.imagepath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 25400 on 2019/10/25.
 */

public class PathEffectView extends View {

    private Paint mPaint;
    private Path mPath;
    private PathEffect[] mEffects;

    public PathEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.DKGRAY);
        mPath = new Path();
        mPath.moveTo(0, 0);
        for (int i = 0; i <= 30; i ++){
            mPath.lineTo(i * 35, (float) (Math.random() * 100));
        }
        mEffects = new PathEffect[6];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mEffects[0] = null;
        mEffects[1] = new CornerPathEffect(30);//拐角处圆滑
        mEffects[2] = new DiscretePathEffect(3.0F, 5.0F);//线段上有许多杂点
        mEffects[3] = new DashPathEffect(new float[]{20, 10, 5, 10}, 0);//虚线
        Path path = new Path();
        path.addRect(0, 0, 8, 8, Path.Direction.CCW);
        mEffects[4] = new PathDashPathEffect(path, 12, 0, PathDashPathEffect.Style.ROTATE);//方形点的虚线
        mEffects[5] = new ComposePathEffect(mEffects[3], mEffects[1]);//组合效果
        for (int i = 0; i < mEffects.length; i++){
            mPaint.setPathEffect(mEffects[i]);
            canvas.drawPath(mPath, mPaint);
            canvas.translate(0, 200);
        }
    }
}
