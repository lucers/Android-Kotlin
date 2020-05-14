package com.lucers.widget;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * WidgetActivity
 *
 * @author lucerstu
 */
public class WidgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        initView();
        initData();
    }

    private void initView() {
    }

    private void initData() {

    }
}
