package com.mylove.baselib.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mylove.baselib.listener.CommonListener;

import java.util.Random;

/**
 * @author yanyi
 * 万能的RecyclerView的ViewHolder
 */

public class RecyclerHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;
    private Context context;

    private RecyclerHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        //指定一个初始为8
        views = new SparseArray<>(8);
    }

    /**
     * 取得一个RecyclerHolder对象
     *
     * @param context  上下文
     * @param itemView 子项
     * @return 返回一个RecyclerHolder对象
     */
    public static RecyclerHolder getRecyclerHolder(Context context, View itemView) {
        return new RecyclerHolder(context, itemView);
    }

    public SparseArray<View> getViews() {
        return this.views;
    }

    /**
     * 通过view的id获取对应的控件，如果没有则加入views中
     *
     * @param viewId 控件的id
     * @return 返回一个控件
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置字符串
     */
    public RecyclerHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置字体文字颜色
     */
    public RecyclerHolder setTextColor(int viewId, int colorID) {
        TextView textView = getView(viewId);
        textView.setTextColor(context.getResources().getColor(colorID));
        return this;
    }

    /**
     * 设置控件显示隐藏
     */
    public RecyclerHolder setVisibility(int viewID, int visibility) {
        getView(viewID).setVisibility(visibility);
        return this;
    }

    /**
     * check设置选中事件
     */
    public RecyclerHolder setCheck(int viewID, boolean boo) {
        CheckBox cb = getView(viewID);
        cb.setChecked(boo);
        return this;
    }

    /**
     * 设置图片
     */
    public RecyclerHolder setImageResource(int viewId, int drawableId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
        return this;
    }

    /**
     * 设置图片
     */
    public RecyclerHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置图片
     */
    public RecyclerHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView iv = getView(viewId);
        iv.setImageDrawable(drawable);
        return this;
    }

    /**
     * 设置图片
     */
    public RecyclerHolder setImageByUrl(int viewId, String url) {
        Glide.with(context).load(url).into((ImageView) getView(viewId));
        return this;
    }

    public RecyclerHolder setImage(int viewID, String url, int width, int height) {
        Glide.with(context).load(url).into((ImageView) getView(viewID));
        return this;
    }

    /**
     * 设置本地图片
     */
    public RecyclerHolder setImageByFile(final Context mContext, final int viewId, final String url) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Glide.with(mContext).load(url).into((ImageView) getView(viewId));
            }
        }, new Random().nextInt(100));
        return this;
    }

    /**
     * 设置视频图片
     */
    public RecyclerHolder setVideoImageByFile(Context mContext, int viewId, String url) {
        Glide.with(mContext).load(url).into((ImageView) getView(viewId));
        return this;
    }

    /**
     * 设置点击事件
     */
    public RecyclerHolder setOnClickListener(int viewId, final int position, final CommonListener commonListener) {
        View iv = getView(viewId);
        iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                commonListener.commonListener(view, position);
            }
        });
        return this;
    }
}
