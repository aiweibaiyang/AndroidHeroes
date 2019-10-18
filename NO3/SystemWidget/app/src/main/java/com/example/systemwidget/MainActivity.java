package com.example.systemwidget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIntent = new Intent(this,MyViewText.class);
    }

    public void btnMyTextView(View view){
        mIntent.putExtra("flag", 1);
        startActivity(mIntent);
    }

    public void btnShineTextView(View view){
        mIntent.putExtra("flag", 2);
        startActivity(mIntent);
    }

    public void btnCircleProgress(View view) {
        mIntent.putExtra("flag", 3);
        startActivity(mIntent);
    }

    public void btnVolumeView(View view) {
        mIntent.putExtra("flag", 4);
        startActivity(mIntent);
    }

    public void btnMyScrollView(View view) {
        mIntent.putExtra("flag", 5);
        startActivity(mIntent);
    }

    public void btnTopBar(View view) {
        startActivity(new Intent(this, TopBarTest.class));
    }
}

