package com.mylove.baselib.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.mylove.baselib.R;
import com.mylove.baselib.adapter.CommonRecyclerAdapter;

import java.util.List;

/**
 * @author myLove
 * RecyclerView动画
 */
public class RecyclerViewAnimation {

    private int animationID;

    private static RecyclerViewAnimation instance;

    private RecyclerViewAnimation(int animationID) {
        this.animationID = animationID;
    }

    public static RecyclerViewAnimation getInstance(int animationID) {
        instance = new RecyclerViewAnimation(animationID);
        return instance;
    }

    public static RecyclerViewAnimation getInstance() {
        instance = new RecyclerViewAnimation(0);
        return instance;
    }

    /**
     * @param recyclerView 当前recyclerView
     * @param list         集合
     * @param <T>          数据类型
     */
    public <T> void runLayoutAnimation(RecyclerView recyclerView, List<T> list) {
        Context context = recyclerView.getContext();
        LayoutAnimationController controller;
        if (animationID == 0) {
            controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        } else {
            controller = AnimationUtils.loadLayoutAnimation(context, animationID);
        }
        recyclerView.setLayoutAnimation(controller);
        if (recyclerView.getAdapter().getClass().isInstance(CommonRecyclerAdapter.class)) {
            ((CommonRecyclerAdapter<T>) recyclerView.getAdapter()).notifyDataSetChanged(list);
        } else {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        recyclerView.scheduleLayoutAnimation();
    }
}
