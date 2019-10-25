package com.example.customanim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void imgClose(View view) {
        CustomTV customTV = new CustomTV();
        view.startAnimation(customTV);
    }

    public void btnAnim(View view) {
        CustomAnim customAnim = new CustomAnim();
        customAnim.setRotateY(30);
        view.startAnimation(customAnim);
    }
}
