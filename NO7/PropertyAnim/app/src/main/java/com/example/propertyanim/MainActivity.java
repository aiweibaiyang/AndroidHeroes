package com.example.propertyanim;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;

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

    public void btnPropertyValuesHolder(View view){
        PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat("translationX", 300f);
        PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0, 1f);
        PropertyValuesHolder pvh3 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvh1, pvh2, pvh3).setDuration(1000).start();
    }

    public void btnAnimatorSet(View view){
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationX", 300f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0, 1f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.playTogether(animator1, animator2, animator3);
        set.start();
    }

//    public void btnScaleX(View view){
//        Animator anim = AnimatorInflater.loadAnimator(this, R.animator.scale);
//        anim.setTarget(mMv);
//        anim.start();
//    }

}
