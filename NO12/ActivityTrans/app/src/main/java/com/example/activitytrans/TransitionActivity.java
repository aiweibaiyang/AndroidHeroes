package com.example.activitytrans;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.util.Pair;
import android.view.View;

public class TransitionActivity extends Activity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
    }

    public void explode(View view){
        intent = new Intent(this, Transitions.class);
        intent.putExtra("flag", 0);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void slide(View view){
        intent = new Intent(this, Transitions.class);
        intent.putExtra("flag", 1);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void fade(View view){
        intent = new Intent(this, Transitions.class);
        intent.putExtra("flag", 2);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void share(View view){
        View fab = findViewById(R.id.fab_button);
        intent = new Intent(this, Transitions.class);
        intent.putExtra("flag", 3);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(view,"share"),Pair.create(fab,"fab")).toBundle());
    }
}
