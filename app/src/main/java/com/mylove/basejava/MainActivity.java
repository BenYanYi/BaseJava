package com.mylove.basejava;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.mylove.baselib.activity.BaseActivity;
import com.mylove.baselib.adapter.CommonRecyclerAdapter;
import com.mylove.baselib.viewholder.RecyclerHolder;
import com.mylove.viewbind.BindView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author myLove
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    private CommonRecyclerAdapter<String> mAdapter;
    private List<String> oList = new ArrayList<>();
    private CommonRecyclerAdapter<Integer> adapter;
    private List<Integer> mList = new ArrayList<>();

    @Override
    protected int setLayoutID() {
        return R.layout.main_activity;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new CommonRecyclerAdapter<String>(mContext, oList, R.layout.item_layout) {
            @Override
            public void convert(RecyclerHolder mHolder, String item, int position, boolean isScrolling) {

            }
        };
        recyclerView.setAdapter(mAdapter);
        View mView = LayoutInflater.from(mContext).inflate(R.layout.head_layout, null);
        RecyclerView mRecycler = mView.findViewById(R.id.mrecycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new CommonRecyclerAdapter<Integer>(mContext, mList, R.layout.item_head) {
            @Override
            public void convert(RecyclerHolder mHolder, Integer item, int position, boolean isScrolling) {
                mHolder.setImageResource(R.id.img, item);
            }
        };
        mRecycler.setAdapter(adapter);
        mAdapter.addHeaderView(mView);
        for (int i = 0; i < 10; i++) {
            oList.add("哈哈哈" + i);
        }
        for (int i = 0; i < 5; i++) {
            mList.add(R.mipmap.ic_launcher);
        }
        adapter.notifyDataSetChanged(mList);
        mAdapter.notifyDataSetChanged(oList);
    }
}
