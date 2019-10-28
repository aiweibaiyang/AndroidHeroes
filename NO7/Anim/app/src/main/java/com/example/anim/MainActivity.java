package com.example.anim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnProperty(View view) {
        startActivity(new Intent(this, PropertyTest.class));
    }

    public void btnTimer(View view) {
        startActivity(new Intent(this, TimerTest.class));
    }

    public void btnDrop(View view) {
        startActivity(new Intent(this, DropTest.class));
    }
}
