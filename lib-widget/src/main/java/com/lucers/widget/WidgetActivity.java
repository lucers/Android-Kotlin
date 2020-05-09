package com.lucers.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lucers.widget.adapter.GridAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * TestActivity
 *
 * @author lucerstu
 */
public class WidgetActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private SmartRefreshLayout refreshLayout;
    private GridView gridView;

    private GridAdapter gridAdapter;

    private List<String> data = new ArrayList<>();

    private boolean loading = false;
    private boolean noMore = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        initView();
        initData();
    }

    private void initView() {
        refreshLayout = findViewById(R.id.refresh_layout);
//        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);

        gridView = findViewById(R.id.gridView);

        gridAdapter = new GridAdapter(data);

        gridView.setAdapter(gridAdapter);
        gridView.setOnItemSelectedListener(this);
    }

    private void initData() {
        data.addAll(getData());
        gridAdapter.notifyDataSetChanged();
    }

    @NotNull
    @Contract(pure = true)
    private List<String> getData() {
        List<String> data = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            data.add("位置:" + i);
        }
        return data;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (needLoadMore(position)) {
            loadMore();
        }
    }

    private void loadMore() {
        refreshLayout.autoLoadMore();
        loading = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000L);
                    if (data.size() > 200) {
                        noMore = true;
                        loading = false;
                        loadOver();
                        return;
                    }
                    data.addAll(getData());
                    loading = false;
                    loadOver();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void loadOver() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (noMore) {
                    Toast.makeText(getApplication(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                }
                refreshLayout.finishLoadMore();
                gridAdapter.notifyDataSetChanged();
            }
        });
    }

    @Contract(pure = true)
    private boolean needLoadMore(int position) {
        return gridAdapter.getCount() - position < gridView.getNumColumns() * 2 && !loading && !noMore;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
