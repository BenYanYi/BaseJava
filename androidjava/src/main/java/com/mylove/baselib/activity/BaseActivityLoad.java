package com.mylove.baselib.activity;

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

public abstract class BaseActivityLoad<T> extends BaseActivity implements onOkHttpListener {
    public RecyclerView commRecycler;
    public SwipeRefreshLayout commSwipe;
    public LinearLayout footLinear;
    public LinearLayout footLoading;
    public TextView footPrompt;
    public CommonRecyclerAdapter<T> mAdapter;
    public List<T> oList = new ArrayList<>();
    public Handler handler = new Handler();
    /**
     * Pull down refresh identifier
     */
    public int REFRESH = 0;
    /**
     * Pull up to load more tags
     */
    public int LOAD = 1;
    /**
     * Determine if there is data to load
     */
    private boolean boo = true;
    /**
     * Number of pages loaded per page
     */
    public int pageSize = 10;
    public int page = 1;
    public int sumSize = 0;
    public int what = REFRESH;

    public boolean NoData = false;

    private int animationID = 0;

    @Override
    protected void init() {
        commRecycler = findViewById(R.id.recycler);
        commSwipe = findViewById(R.id.swipe_refresh);
        commSwipe.setColorSchemeColors(getResources().getColor(R.color.comm_blue),
                getResources().getColor(R.color.comm_green), getResources().getColor(R.color.comm_red));
        setAdapter();
        setHeader();
        setFooter();
        refreshData();
    }

    /**
     * set adapter in recyclerView
     */
    public void setAdapter() {
        if (setLayoutManager() == null) {
            throw new NullPointerException("layoutManager cannot be null");
        }
        if (setItemLayout() == 0) {
            throw new NullPointerException("Adapter needs to set the item layout");
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
//        if (isListAnimation()) {
//            RecyclerViewAnimation.runLayoutAnimation(commRecycler, oList);
//        } else {
//            mAdapter.notifyDataSetChanged(oList);
//        }
    }

    protected abstract RecyclerView.LayoutManager setLayoutManager();

    /**
     * adapter set the item layout
     */
    protected abstract int setItemLayout();

    /**
     * setting item
     */
    protected abstract void adapterConvert(RecyclerHolder mHolder, T item, int position, boolean isScrolling);

    /**
     * setting header
     */
    public void setHeader() {
    }

    /**
     * setting footer
     */
    protected void setFooter() {
        View footer = LayoutInflater.from(mContext).inflate(R.layout.item_foot, commRecycler, false);
        footLinear = footer.findViewById(R.id.foot_linear);
        footLoading = footer.findViewById(R.id.foot_loading);
        footPrompt = footer.findViewById(R.id.foot_prompt);
        mAdapter.addFooterView(footer);
    }

    /**
     * Refresh and load more
     */
    private void refreshData() {
        dataInit();
        //Pull down to refresh
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
            //The number of items visible on the screen
            private int visibleItemCount = 0;
            //The number of items that have been loaded
            private int totalItemCount = 0;
            //The first item in the item visible on the screen
            private int firstVisibleItem = 0;
//            //Is pulling data up
//            private boolean loading = true;

            private int viewSize = 0;

            /**
             * Called in scrolling
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
             * Called after scrolling stops
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //The last visible item
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (boo && lastVisibleItem + viewSize == oList.size()) {
                    //Show loading progress bar
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
     * Method of requesting data
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
     * notify UI
     */
    public void notifyUi() {
        dataInit();
    }

    /**
     * clean adapter
     */
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
     * State change
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
     * data processing
     *
     * @param message Network data
     */
    protected abstract <K> void disposeResultData(K message);

    /**
     * is show recyclerView animation
     *
     * @return
     */
    public boolean isListAnimation() {
        return true;
    }

    /**
     * setting animation ID
     *
     * @param animationID
     */
    public void setAnimationID(int animationID) {
        this.animationID = animationID;
    }

    public int getAnimationID() {
        return animationID;
    }
}
