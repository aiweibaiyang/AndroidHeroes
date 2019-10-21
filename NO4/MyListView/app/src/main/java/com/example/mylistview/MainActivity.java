package com.example.mylistview;

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

    public void btnViewHolder(View view) {
        startActivity(new Intent(this, NotifyTest.class));
    }

    public void btnFlexible(View view) {
        startActivity(new Intent(this, FlexibleListViewTest.class));
    }

    public void btnScrollHideListView(View view) {
        startActivity(new Intent(this, ScrollHideListView.class));
    }

    public void btnChatItem(View view) {
        startActivity(new Intent(this, ChatItemListViewTest.class));
    }

    public void btnFocus(View view) {
        startActivity(new Intent(this, FocusListViewTest.class));
    }
}
