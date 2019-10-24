package com.example.imageshape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ReflectViewTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ReflectView(this));
    }
}
