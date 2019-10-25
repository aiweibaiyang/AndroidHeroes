package com.example.propertyanim;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnObjectAnimator(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 300);
        animator.setDuration(300);
        animator.start();
    }

}
