package com.example.imageshape;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;

public class ImageMatrixTest extends AppCompatActivity {

    private GridLayout mGridGroup;
    private ImageMatrixView mMyView;
    private Bitmap mBitmap;
    private int mEtWidth = 0;
    private int mEtHeight = 0;
    private float[] mImageMatrix = new float[9];
    private EditText[] mETs = new EditText[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colormatrix);
        mGridGroup = (GridLayout) findViewById(R.id.grid_group);
        mMyView = (ImageMatrixView) findViewById(R.id.view);
        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);

        mGridGroup.post(new Runnable() {
            @Override
            public void run() {
                mEtWidth = mGridGroup.getWidth() / 3;
                mEtHeight = mGridGroup.getHeight() / 3;
                addEts();
                initImageMatrix();
            }
        });
    }

    //向网格中添加数据
    private void addEts(){
        for (int i = 0; i < 9; i ++){
            EditText et = new EditText(ImageMatrixTest.this);
            et.setGravity(Gravity.CENTER);
            mETs[i] = et;
            mGridGroup.addView(et, mEtWidth, mEtHeight);
        }
    }

    //获取网格中的数据
    private void getImageMatrix(){
        for (int i = 0; i < 9; i ++){
            EditText et = mETs[i];
            mImageMatrix[i] = Float.valueOf(et.getText().toString());
        }
    }

    //初始化网格中的数据
    private void initImageMatrix(){
        for (int i = 0; i < 9; i++) {
            if (i % 4 == 0) {
                mETs[i].setText(String.valueOf(1));
            }else {
                mETs[i].setText(String.valueOf(0));
            }
        }
    }

    public void change(View view){
        getImageMatrix();
        Matrix matrix = new Matrix();
        matrix.setValues(mImageMatrix);//将一维数组转为图形变换矩阵

        mMyView.setImageAndMatrix(mBitmap, matrix);//ImageMatrixView中的方法
        mMyView.invalidate();//刷新mMyView
    }

    public void reset(View view){
        initImageMatrix();
        getImageMatrix();
        Matrix matrix = new Matrix();
        matrix.setValues(mImageMatrix);
        mMyView.setImageAndMatrix(mBitmap, matrix);
        mMyView.invalidate();
    }
}
