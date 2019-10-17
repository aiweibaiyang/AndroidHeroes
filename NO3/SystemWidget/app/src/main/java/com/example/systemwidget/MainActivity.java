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
}

