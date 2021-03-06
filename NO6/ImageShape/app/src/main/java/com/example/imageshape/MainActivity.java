package com.example.imageshape;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnImageMatrix(View view) {
        startActivity(new Intent(this, ImageMatrixTest.class));
    }

    public void btnFlag(View view) {
        startActivity(new Intent(this, FlagBitmapMeshTest.class));
    }

    public void btnPorterDuffXfermode(View view) {
        startActivity(new Intent(this, XfermodeViewTest.class));
    }

    public void btnReflect(View view) {
        startActivity(new Intent(this, ReflectViewTest.class));
    }

//    public void btnRoundRect(View view) {
//        startActivity(new Intent(this, RoundRectTest.class));
//    }
}
