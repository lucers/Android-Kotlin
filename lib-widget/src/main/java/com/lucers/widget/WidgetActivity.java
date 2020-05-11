package com.lucers.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.lucers.widget.adapter.GridAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * WidgetActivity
 *
 * @author lucerstu
 */
public class WidgetActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnLoadMoreListener {

    private SmartRefreshLayout refreshLayout;
    private GridView gridView;

    private GridAdapter gridAdapter;

    private List<String> data = new ArrayList<>();

    private boolean loading = false;
    private boolean noMore = false;

    private int itemMaxSize = 200;

    private int pagePosition = 1;
    private int pageSize = 50;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        initView();
        initData();
    }

    /**
     * 初始化控件配置
     */
    private void initView() {
        refreshLayout = findViewById(R.id.refresh_layout);
        // 去掉下拉刷新
        refreshLayout.setEnableRefresh(false);
        // 设置加载更多的监听
        refreshLayout.setOnLoadMoreListener(this);
        //是否上拉Footer的时候向上平移列表或者内容
        refreshLayout.setEnableFooterTranslationContent(true);

        gridView = findViewById(R.id.gridView);

        gridAdapter = new GridAdapter(data);

        gridView.setAdapter(gridAdapter);
        // 设置TV上选择监听
        gridView.setOnItemSelectedListener(this);
    }

    /**
     * 加载初始化显示数据
     */
    private void initData() {
        data.addAll(getData());
        gridAdapter.notifyDataSetChanged();
    }

    /**
     * 模拟获取数据
     *
     * @return 返回一段数据
     */
    @NotNull
    private List<String> getData() {
        List<String> data = new ArrayList<>();
        for (int i = (pagePosition - 1) * pageSize + 1; i <= pagePosition * pageSize; i++) {
            data.add("位置:" + i);
        }
        return data;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (needLoadMore(position)) {
            // 触发加载更多
            refreshLayout.autoLoadMore();
        }
    }

    /**
     * 模拟数据加载
     */
    private void loadMore() {
        LogUtils.d("loadMore");
        // 下一页
        pagePosition++;
        // 修改为加载中
        loading = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 模拟网络请求中
                    Thread.sleep(3000L);
                    // 模拟没有获取到更多数据了
                    if (data.size() >= itemMaxSize) {
                        // 修改为没有加载到数据
                        noMore = true;
                        // 修改为加载结束
                        loading = false;
                        // 没有获取到更多数据或者获取数据失败了，页面要回退
                        pagePosition--;
                        loadOver();
                        return;
                    }
                    // 模拟获取到数据了，将数据加入显示列表
                    data.addAll(getData());
                    // 修改为加载结束
                    loading = false;
                    loadOver();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 加载结束处理
     */
    private void loadOver() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 如果获取的数据没有了，显示提示
                if (noMore) {
                    Toast.makeText(getApplication(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                }
                // 结束动画，并刷新数据列表
                refreshLayout.finishLoadMore();
                gridAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 根据位置，加载状态，数据状态判断是否需要加载数据
     *
     * @param position 位置
     * @return 是否加载数据
     */
    @Contract(pure = true)
    private boolean needLoadMore(int position) {
        // 当前数据总数减去选择的位置如果小于gridView的每行个数的两倍，代表已经选到了最后两行中的item
        return gridAdapter.getCount() - position <= gridView.getNumColumns() && !loading && !noMore;
    }

    /**
     * 没有数据被选中的监听
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * 加载更多的监听
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        loadMore();
    }
}
