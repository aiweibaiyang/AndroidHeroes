package com.example.imageshape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 25400 on 2019/10/24.
 */

public class FlagBitmapMeshView extends View {

    private final int WIDTH = 200;
    private final int HEIGHT = 200;//由于后面y从0开始计数，因此HEIGHT比线的数量少1
    private int COUNT = (WIDTH + 1) * (HEIGHT + 1);//点的数量=横线个数*竖线数量
    private float[] verts = new float[COUNT * 2];
    private float[] orig = new float[COUNT * 2];
    private Bitmap bitmap;
    private float A;
    private float k = 1;
    private int index = 0;

    public FlagBitmapMeshView(Context context) {
        super(context);
        initView(context);
    }

    public FlagBitmapMeshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FlagBitmapMeshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        setFocusable(true);//将控件设置成可获取焦点状态
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test);
        float bitmapWidth = bitmap.getWidth();//获取图片的宽
        float bitmapHeight = bitmap.getHeight();
        for (int y = 0; y <=HEIGHT; y++){
            float fy = bitmapHeight * y / HEIGHT;//每个y点的坐标
            for (int x = 0; x <= WIDTH; x++){
                float fx = bitmapWidth * x / WIDTH;
                orig[index * 2 + 0] = verts[index * 2 + 0] = fx;
                orig[index * 2 + 1] = verts[index * 2 + 1] = fy + 100;
                index += 1;
            }
        }
        A = 50;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        flagWave();
        k += 0.1F;
        canvas.drawBitmapMesh(bitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
        invalidate();
    }

    private void flagWave(){
        for (int j = 0; j <= HEIGHT; j++){
            for (int i = 0; i<= WIDTH; i++){
                verts[(j * (WIDTH + 1) + i) * 2 + 0] += 0;
                float offsetY = (float) Math.sin((float) i / WIDTH * 2 * Math.PI + Math.PI * k);
                verts[(j * (WIDTH + 1) + i) * 2 + 1] =
                        orig[(j * (WIDTH + 1) + i) * 2 + 1] + offsetY * A;
            }
        }
    }
}
