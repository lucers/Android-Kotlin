package com.lucers.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lucers.widget.adapter.BaseRecyclerViewAdapter;
import com.lucers.widget.adapter.GridAdapter;
import com.lucers.widget.adapter.RecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * WidgetActivity
 *
 * @author lucerstu
 */
public class WidgetActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnLoadMoreListener {

    private RecyclerView rvTop;
    private RecyclerView rvLeft;
    private SmartRefreshLayout refreshLayout;
    private GridView gridView;

    private GridAdapter gridAdapter;
    private RecyclerAdapter listAdapter;
    private RecyclerAdapter recyclerAdapter;

    private List<String> titles = new ArrayList<>();
    private List<String> classifies = new ArrayList<>();
    private List<String> data = new ArrayList<>();

    private boolean loading = false;
    private boolean noMore = false;

    private int itemMaxSize = 200;
    private int topPosition, leftPosition;

    private int pagePosition = 1;

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
        /*
         * 顶部rv配置
         */
        rvTop = findViewById(R.id.rv_top);
        rvTop.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerAdapter = new RecyclerAdapter();
        recyclerAdapter.setItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(@NotNull View view, int position) {
                topPosition = position;
                getClassifies();
            }
        });
        rvTop.setAdapter(recyclerAdapter);

        /*
         * 左边rv配置
         */
        rvLeft = findViewById(R.id.rv_left);
        rvLeft.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new RecyclerAdapter();
        listAdapter.setItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(@NotNull View view, int position) {
                leftPosition = position;
                getData();
            }
        });
        rvLeft.setAdapter(listAdapter);

        /*
         * 刷新控件配置
         */
        refreshLayout = findViewById(R.id.refresh_layout);
        // 去掉下拉刷新
        refreshLayout.setEnableRefresh(false);
        // 设置加载更多的监听
        refreshLayout.setOnLoadMoreListener(this);
        /*
         * 右边gv配置
         */
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
        getTitles();
    }

    private void getTitles() {
        for (int i = 1; i <= 10; i++) {
            titles.add("标题:" + i);
        }
        recyclerAdapter.setList(titles);
        getClassifies();
    }

    private void getClassifies() {
        classifies.clear();
        int itemCount = new Random().nextInt(10);
        itemCount = itemCount == 0 ? 2 : itemCount;
        for (int i = 1; i <= itemCount; i++) {
            classifies.add("分类:" + i);
        }
        listAdapter.setList(classifies);

        getData();
    }

    /**
     * 模拟获取数据
     */
    private void getData() {
        noMore = false;
        pagePosition = 1;
        data.clear();
        gridView.setSelection(0);
        gridView.requestFocus();
        int itemCount = new Random().nextInt(50);
        itemCount = itemCount == 0 ? 10 : itemCount;
        for (int i = gridAdapter.getCount() + 1; i <= itemCount; i++) {
            data.add("数据:" + i);
        }
        gridAdapter.notifyDataSetChanged();
    }

    /**
     * 模拟数据加载
     */
    private void loadMore() {
        LogUtils.d("当前页数:" + pagePosition);
        // 下一页
        pagePosition++;
        LogUtils.d("开始加载第" + pagePosition + "页");
        // 修改为加载中
        loading = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 模拟网络请求中
                    Thread.sleep(3000L);
                    // 修改为加载结束
                    loading = false;
                    // 模拟没有获取到更多数据了
                    if (data.size() >= itemMaxSize) {
                        // 修改为没有加载到数据
                        noMore = true;
                        // 没有获取到更多数据或者获取数据失败了，页面要回退
                        pagePosition--;
                        LogUtils.d("加载失败，当前页数:" + pagePosition + "页");
                        loadOver();
                        return;
                    }
                    // 模拟获取到数据了，将数据加入显示列表
                    loadMoreData();
                    loadOver();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void loadMoreData() {
        LogUtils.d("加载第" + pagePosition + "页数据成功");
        int itemCount = new Random().nextInt(50);
        itemCount = itemCount == 0 ? 10 : itemCount;
        int count = gridAdapter.getCount() + 1;
        for (int i = count; i <= count + itemCount; i++) {
            data.add("数据:" + i);
            if (i == itemMaxSize) {
                break;
            }
        }
    }

    /**
     * 加载结束处理
     */
    private void loadOver() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 结束动画
                refreshLayout.finishLoadMore();
                // 如果获取的数据没有了，显示提示
                if (noMore) {
                    Toast.makeText(getApplication(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 刷新数据列表
                gridAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ToastUtils.showShort("当前选中:" + (position + 1) + "位置");
        if (needLoadMore(position)) {
            // 显示加载更多的动画，加载更多数据
            refreshLayout.autoLoadMoreAnimationOnly();
            loadMore();
        }
    }

    /**
     * 根据位置，加载状态，数据状态判断是否需要加载数据
     *
     * @param position 位置
     * @return 是否加载数据
     */
    private boolean needLoadMore(int position) {
        // 当前数据总数减去选择的位置如果小于gridView的每行个数，代表已经选到了最后一行中的item
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
