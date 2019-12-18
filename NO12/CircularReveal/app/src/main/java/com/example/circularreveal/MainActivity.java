package com.example.circularreveal;

import android.animation.Animator;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final View oval = this.findViewById(R.id.oval);
        oval.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Animator animatior = ViewAnimationUtils.createCircularReveal(
                        oval,
                        oval.getWidth()/2,
                        oval.getHeight()/2,
                        oval.getWidth(),
                        0);
                animatior.setInterpolator(new AccelerateDecelerateInterpolator());
                animatior.setDuration(2000);
                animatior.start();
            }
        });

        final View rect = this.findViewById(R.id.rect);

        rect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Animator animator = ViewAnimationUtils.createCircularReveal(
                        rect,
                        0,
                        0,
                        0,
                        (float)Math.hypot(rect.getWidth(), rect.getHeight()));
                animator.setInterpolator(new AccelerateInterpolator());
                animator.setDuration(2000);
                animator.start();
            }
        });
    }
}
