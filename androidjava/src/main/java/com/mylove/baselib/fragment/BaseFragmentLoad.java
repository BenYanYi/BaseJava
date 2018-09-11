package com.mylove.baselib.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylove.baselib.R;
import com.mylove.baselib.adapter.CommonRecyclerAdapter;
import com.mylove.baselib.utils.StringUtil;
import com.mylove.baselib.viewholder.RecyclerHolder;
import com.mylove.baselib.widget.RecyclerViewAnimation;
import com.mylove.loglib.JLog;
import com.mylove.okhttp.onOkHttpListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanyi
 */

public abstract class BaseFragmentLoad<T> extends BaseFragment implements onOkHttpListener {
    public RecyclerView commRecycler;
    public SwipeRefreshLayout commSwipe;
    public LinearLayout footLinear;
    public LinearLayout footLoading;
    public TextView footPrompt;
    public CommonRecyclerAdapter<T> mAdapter;
    public List<T> oList = new ArrayList<>();
    public Handler handler = new Handler();
    /**
     * 下拉刷新的标识
     */
    public int REFRESH = 0;
    /**
     * 上拉加载更多的标识
     */
    public int LOAD = 1;
    /**
     * 判断是否没有数据可加载
     */
    private boolean boo = true;
    /**
     * 每页加载的条数
     */
    public int pageSize = 10;
    public int page = 1;
    public int sumSize = 0;
    public int what = REFRESH;

    public boolean NoData = false;

    private int animationID = 0;

    @Override
    protected void init() {
        commRecycler = mView.findViewById(R.id.recycler);
        commSwipe = mView.findViewById(R.id.swipe_refresh);
        commSwipe.setColorSchemeColors(getResources().getColor(R.color.comm_blue),
                getResources().getColor(R.color.comm_green), getResources().getColor(R.color.comm_red));
        setAdapter();
        setHeader();
        setFooter();
        refreshData();
    }

    public void setAdapter() {
        if (setLayoutManager() == null) {
            throw new NullPointerException("RecyclerView样式不能为空");
        }
        if (setItemLayout() == 0) {
            throw new NullPointerException("adapter需要设置item布局");
        }
        oList.clear();
        commRecycler.setLayoutManager(setLayoutManager());
        mAdapter = new CommonRecyclerAdapter<T>(mContext, oList, setItemLayout()) {
            @Override
            public void convert(RecyclerHolder mHolder, T item, int position, boolean isScrolling) {
                adapterConvert(mHolder, item, position, isScrolling);
            }
        };
        commRecycler.setAdapter(mAdapter);
        notifyData(oList);
    }

    protected abstract RecyclerView.LayoutManager setLayoutManager();

    /**
     * 设置适配器
     */
    protected abstract int setItemLayout();

    /**
     * item设置参数
     *
     * @param mHolder
     * @param item
     * @param position
     * @param isScrolling
     */
    protected abstract void adapterConvert(RecyclerHolder mHolder, T item, int position, boolean isScrolling);

    /**
     * 设置头部
     */
    public void setHeader() {
    }

    protected void setFooter() {
        View footer = LayoutInflater.from(mContext).inflate(R.layout.item_foot, commRecycler, false);
        footLinear = footer.findViewById(R.id.foot_linear);
        footLoading = footer.findViewById(R.id.foot_loading);
        footPrompt = footer.findViewById(R.id.foot_prompt);
        mAdapter.addFooterView(footer);
    }

    /**
     * 刷新以及加载更多
     */
    private void refreshData() {
        if (!ifHidden()) {
            dataInit();
        }
        //下拉刷新
        commSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dataInit();
                    }
                });
            }
        });
        commRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem = 0;
            //在屏幕上可见的item数量
            private int visibleItemCount = 0;
            //已经加载出来的Item的数量
            private int totalItemCount = 0;
            //在屏幕可见的Item中的第一个
            private int firstVisibleItem = 0;
//            //是否正在上拉数据
//            private boolean loading = true;
//            //主要用来存储上一个totalItemCount
//            private int previousTotal = 0;

            private int viewSize = 0;

            /**
             * 滚动中调用
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                viewSize = 0;
                boolean footerView = mAdapter.haveFootView();
                boolean headerView = mAdapter.haveHeaderView();
                if (footerView) {
                    viewSize++;
                }
                if (headerView) {
                    viewSize++;
                }
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                if (boo && totalItemCount >= (pageSize * page)
                        && totalItemCount == (oList.size() + viewSize)
                        && (lastVisibleItem + viewSize) == totalItemCount
                        && (footLoading.getVisibility()) == View.VISIBLE) {
                    page++;
                    what = LOAD;
                    loadResultData();
                }
            }

            /**
             * 滚动停止后调用
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                //最后一个可见的item
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (boo && lastVisibleItem + viewSize == oList.size()) {
                    //显示加载中进度条
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            footLinear.setVisibility(View.VISIBLE);
                            footLoading.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
    }

    /**
     * 请求数据的方法
     */
    protected abstract void loadResultData();

    public void notifyData(List<T> list) {
        oList = list;
        if (isListAnimation()) {
            RecyclerViewAnimation.getInstance(animationID).runLayoutAnimation(commRecycler, oList);
        } else {
            mAdapter.notifyDataSetChanged(oList);
        }
    }

    /**
     * 刷新页面
     */
    public void notifyUi() {
        dataInit();
    }

    public void cleanAdapter() {
        oList.clear();
        notifyData(oList);
    }

    private void dataInit() {
        cleanAdapter();
        NoData = false;
        footLinear.setVisibility(View.VISIBLE);
        footLoading.setVisibility(View.VISIBLE);
        footPrompt.setText(getResources().getString(R.string.loading));
        page = 1;
        pageSize = 10;
        what = REFRESH;
        loadResultData();
    }

    /**
     * 状态改变
     */
    private void status() {
        if (StringUtil.isListNotEmpty(oList)) {
            JLog.v(oList);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boo = true;
                footLoading.setVisibility(View.GONE);
                commSwipe.setRefreshing(false);
                if (StringUtil.isListEmpty(oList)) {
                    JLog.v();
                    boo = false;
                    footLinear.setVisibility(View.VISIBLE);
                    footPrompt.setText("暂无数据");
                    footPrompt.setVisibility(View.VISIBLE);
                } else if ((oList.size() == sumSize && NoData) || oList.size() % pageSize != 0) {
                    JLog.v();
                    boo = false;
                    footLinear.setVisibility(View.VISIBLE);
                    footPrompt.setVisibility(View.VISIBLE);
                    footPrompt.setText("已加载全部数据");
                } else {
                    JLog.v();
                    footLinear.setVisibility(View.GONE);
                    footPrompt.setVisibility(View.GONE);
                }
                sumSize = oList.size();
                notifyData(oList);
            }
        }, 500);
    }

    @Override
    public <K> void onSuccess(K message) {
        disposeResultData(message);
        status();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onFailure(Throwable t) {
        JLog.e(t.getMessage());
        oList.clear();
        status();
    }

    /**
     * 数据处理
     *
     * @param message 网络数据
     */
    protected abstract <K> void disposeResultData(K message);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (ifHidden()) {
            if (isVisibleToUser && isResumed()) {
                onResume();
            } else {
                onPause();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && ifHidden()) {
            visibleInit();
            dataInit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 判断是否需要判断可视
     *
     * @return
     */
    public boolean ifHidden() {
        return false;
    }

    @Override
    public boolean isVisibleHidden() {
        return ifHidden();
    }

    /**
     * 是否显示RecyclerView动画
     *
     * @return
     */
    public boolean isListAnimation() {
        return true;
    }

    public void setAnimationID(int animationID) {
        this.animationID = animationID;
    }

    public int getAnimationID() {
        return animationID;
    }
}
