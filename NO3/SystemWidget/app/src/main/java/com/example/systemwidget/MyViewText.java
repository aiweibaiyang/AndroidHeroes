package com.example.systemwidget;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by 25400 on 2019/10/16.
 */

public class MyViewText extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int flag = getIntent().getIntExtra("flag", -1);
        switch (flag){
            case 1:
                setContentView(R.layout.my_textview);
                break;
        }
    }
}
